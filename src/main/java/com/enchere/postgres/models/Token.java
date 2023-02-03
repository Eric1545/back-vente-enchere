package com.enchere.postgres.models;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;
import com.enchere.utils.Database;
import com.enchere.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

@Table
public class Token extends GeneriqueDAO {
    @Colonne
    private Integer id;
    @Colonne
    private String token;
    @Colonne
    private Timestamp datecreation;
    @Colonne
    private Timestamp dateexpiration;

    public Token() {
    }

    public Token(Integer id, String token, Timestamp datecreation, Timestamp dateexpiration) {
        this.id = id;
        this.token = token;
        this.datecreation = datecreation;
        this.dateexpiration = dateexpiration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Timestamp datecreation) {
        this.datecreation = datecreation;
    }

    public Timestamp getDateexpiration() {
        return dateexpiration;
    }

    public void setDateexpiration(Timestamp dateexpiration) {
        this.dateexpiration = dateexpiration;
    }

    public void modifierToken(Connection connection, String id) throws Exception{
        this.update(connection, id);
    }

    public void insererToken(Connection connection) throws Exception {
        this.save(connection);
    }

    private static int validToken(String token) throws Exception {
        int res = 0;
        String sql = "SELECT COUNT(*) FROM token WHERE token LIKE ? AND dateexpiration > current_timestamp";
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, token);
        ResultSet rs = ps.executeQuery();
        rs.next();
        res = rs.getInt(1);
        return res;
    }

    public static boolean verifierExistanceTokenAndValidation(String username, String password) throws Exception {
        boolean exist = false;
        String token = Utils.creationToken(username, password);
        int existance = validToken(token);
        if (existance != 0) exist = true;
        return exist;
    }
}