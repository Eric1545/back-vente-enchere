package com.enchere.helper;


import com.enchere.org.gen.dao.utils.GeneriqueDAO;

import java.sql.Connection;
import java.sql.SQLException;

public final class SequenceHelper {

    public static String last_value(String tableName, Connection con) throws SQLException {
        System.out.println("select last_value from "+ tableName +"_id_seq");
        return GeneriqueDAO.execute(con, "select last_value from "+ tableName +"_id_seq")[0][0];
    }
}
