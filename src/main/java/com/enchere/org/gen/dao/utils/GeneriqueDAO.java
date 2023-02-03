package com.enchere.org.gen.dao.utils;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class GeneriqueDAO {
    private String whereCondition = "true";
    private String nomTable = "";

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public List<?> list(Connection con, String... request) throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class<?> classe = this.getClass();
            String sql = request.length>0 ? request[0] : generateSql();
            List liste = new ArrayList<>();
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Object instance = classe.getConstructor().newInstance();
                List<Method> setters = getMethodsSetOrGet("set");
                List<Method> getters = getMethodsSetOrGet("get");
                for (int i = 0; i < getters.size(); i++) {
                    String column = ((Method)getters.get(i)).getName().substring(3);
                    String retType = ((Method)getters.get(i)).getReturnType().getSimpleName().substring(0, 1).toUpperCase() + getters.get(i).getReturnType().getSimpleName().substring(1);
                    Method metRes = retType.equalsIgnoreCase("integer") ? ResultSet.class.getDeclaredMethod("getInt", String.class) : ResultSet.class.getDeclaredMethod("get" + retType, String.class);
                    setters.get(i).invoke(instance, metRes.invoke(resultSet, column));
                }
                liste.add(instance);
            }
            statement.close();

            return liste;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            assert statement != null;
            statement.close();

        }
    }

    public static String[][] execute(Connection con,String sql) throws SQLException {
        Statement statement = null;
        try {
            List<String[]> data = new ArrayList<>();
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int nbColonne = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()){
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= nbColonne; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row.toArray(new String[0]));
            }
            return data.toArray(new String[0][]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            assert statement != null;
            statement.close();

        }
    }

    public Object findOne(Connection connection, String... request) throws Exception {
        Object result = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = request.length>0 ? request[0] : generateSql();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                result = initInstance(resultSet);
            }
        } catch (Exception e) {
            connection.close();
            throw e;
        }finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }


        return result;
    }

    private Object initInstance(ResultSet resultSet) throws Exception {
        Object instance = this.getClass().getConstructor().newInstance();
        List<Method> setters = getMethodsSetOrGet("set");
        List<Method> getters = getMethodsSetOrGet("get");

        for (int i = 0; i < getters.size(); i++) {
            String column = getters.get(i).getName().substring(3);
            String retType = getters.get(i).getReturnType().getSimpleName().substring(0, 1).toUpperCase() + getters.get(i).getReturnType().getSimpleName().substring(1);
            setters.get(i).invoke(instance, ResultSet.class.getDeclaredMethod("getObject", String.class).invoke(resultSet, column));
        }
        return instance;
    }

    public static void executeUpdate(Connection con,String sql) throws SQLException {
        Statement statement = null;
        try {
            statement = con.createStatement();
            statement.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            assert statement != null;
            statement.close();

        }
    }

    public void update(Connection con,String id) throws Exception {
        try {
            StringBuilder sql = new StringBuilder("UPDATE " + getTableName() + " set ");
            for (Method methode : getMethodsNotNull()) {
                sql.append(methode.getName().substring(3));
                sql.append("='").append(methode.invoke(this));
                sql.append("' , ");
            }
            if (sql.toString().split(" ")[sql.toString().split(" ").length - 1].equals(",")) {
                sql.delete(sql.lastIndexOf(","), sql.length());
            }
            sql.append("where id = '").append(id).append("'");
            System.out.println(sql);
            con.createStatement().executeUpdate(String.valueOf(sql));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void save(Connection con) throws Exception {
        Statement statement = null;
        try {
            StringBuilder sql = new StringBuilder("insert into " + getTableName().toLowerCase() + "(");
            List<Method> methodes = getMethodsNotNull();
            for (Method methode : methodes) {
                String nomColonne = methode.getName().substring(3).toLowerCase();
                sql.append(nomColonne).append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(") values (");
            for (Method methode : methodes) {
                sql.append("'").append(methode.invoke(this)).append("'").append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(")");
            statement = con.createStatement();

            System.out.println(sql);
            statement.executeUpdate(sql.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            assert statement != null;
            statement.close();
        }
    }

    public void delete(Connection con, String id) throws SQLException {
        Statement statement = null;
        try {
            if (id == null)
                throw new IllegalArgumentException("L'id ne pourra pas etre null!");
            statement = con.createStatement();


            System.out.println("delete from " + getTableName() + " where id='" + id + "'");
            statement.executeUpdate("delete from " + getTableName() + " where id=" + id);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            assert statement != null;
            statement.close();
        }
    }


    public GeneriqueDAO addWhereCondition(String condition){
        this.whereCondition += " and " + condition;
        return this;
    }


    protected final String getTableName(){
        if (this.nomTable.isEmpty()){
            Class<?> classe = this.getClass();
            String tableName = "";
            while (classe != GeneriqueDAO.class ){
                Table table = classe.getAnnotation(Table.class);
                if (table != null){
                    tableName = !table.name().isEmpty() ? table.name() : classe.getSimpleName();
                    break;
                }
                classe = classe.getSuperclass();
            }
            this.nomTable = tableName;
        }
        return nomTable;
    }

    protected final String generateSql() throws Exception {
        try {
            StringBuilder sql = new StringBuilder("select * from " + getTableName() + " where ");
            List<Method> methodes = getMethodsNotNull();
            sql.append(whereCondition).append(" and ");
            for (Method methode : methodes) {
                String attribut = methode.getName().substring(3);
                sql.append(attribut).append(" = ");
                sql.append("'").append(methode.invoke(this)).append("'");
                sql.append(" and ");
            }
            if (sql.toString().split(" ")[sql.toString().split(" ").length - 1].equals("and")) {
                sql.delete(sql.lastIndexOf("and"), sql.length());
            }
            return sql.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected final List<Method> getMethodsNotNull() throws Exception {
        try {
            List<Method> methodes = new ArrayList<>();
            for (Method methode : getMethodsSetOrGet("get")) {
                boolean primitive = methode.getReturnType().isPrimitive();
                if ((primitive && !methode.invoke(this).equals(0)) || (!primitive && methode.invoke(this) != null)) {
                    methodes.add(methode);
                }
            }
            return methodes;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public final List<Method> getMethodsSetOrGet(String start) throws NoSuchMethodException {
        try {
            Class<?> classe = this.getClass();
            List<Method> methodes = new ArrayList<>();
            while (classe != GeneriqueDAO.class) {
                Field[] attributs = classe.getDeclaredFields();
                for (Field attribut : attributs) {
                    if (attribut.getAnnotation(Colonne.class) != null) {
                        String nomMethode = start + attribut.getName().substring(0, 1).toUpperCase() + attribut.getName().substring(1);
                        boolean b = start.equals("set") ? methodes.add(classe.getDeclaredMethod(nomMethode, attribut.getType())) : methodes.add(classe.getDeclaredMethod(nomMethode));
                    }
                }
                classe = classe.getSuperclass();
            }
            return methodes;
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
