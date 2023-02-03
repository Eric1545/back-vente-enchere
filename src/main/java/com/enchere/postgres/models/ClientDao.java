package com.enchere.postgres.models;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;
import com.enchere.utils.Database;

import java.util.List;

@Table(name = "client")
public class ClientDao extends GeneriqueDAO {
    @Colonne
    private Integer id;
    @Colonne
    private String nom;
    @Colonne
    private String prenom;
    @Colonne
    private String email;
    @Colonne
    private String mdp;

    public ClientDao() {
    }

    public ClientDao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public Integer getIdClient() throws Exception{
        List<ClientDao> client = (List<ClientDao>) this.list(Database.getConnection());
        Integer id;
        if (client.size()!=0) id = client.get(0).getId();
        else id = null;
        return id;
    }


    public ClientDao login() throws Exception {
        return (ClientDao) this.findOne(Database.getConnection());
    }

}
