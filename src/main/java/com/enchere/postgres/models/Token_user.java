package com.enchere.postgres.models;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;

import java.sql.Connection;

@Table
public class Token_user extends GeneriqueDAO {
    @Colonne
    private Integer id;
    @Colonne
    private Integer id_token;
    @Colonne
    private Integer id_admin;
    @Colonne
    private Integer id_user;

    public Token_user() {
    }

    public Token_user(Integer id, Integer id_token, Integer id_admin, Integer id_user) {
        this.id = id;
        this.id_token = id_token;
        this.id_admin = id_admin;
        this.id_user = id_user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_token() {
        return id_token;
    }

    public void setId_token(Integer id_token) {
        this.id_token = id_token;
    }

    public Integer getId_admin() {
        return id_admin;
    }

    public void setId_admin(Integer id_admin) {
        this.id_admin = id_admin;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public void insererTokenUser(Connection con) throws Exception{
        this.save(con);
    }

}
