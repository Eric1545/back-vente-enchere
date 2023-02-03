package com.enchere.utils;

import com.enchere.org.gen.dao.utils.GeneriqueDAO;
import com.enchere.postgres.models.Enchere;

public class Main {
    public static void main(String[] args) throws Exception {
        int idDerniereOffre = 1;
        GeneriqueDAO.executeUpdate(Database.getConnection(), "insert into vendu values (" + idDerniereOffre + ")");
//        System.out.println(Enchere.getIdDerniereOffre(1));
    }
}
