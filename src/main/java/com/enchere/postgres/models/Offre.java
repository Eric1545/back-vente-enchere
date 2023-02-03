package com.enchere.postgres.models;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;
import com.enchere.utils.Database;

import java.sql.Date;
import java.util.List;

@Table
public class Offre extends GeneriqueDAO {
    @Colonne
    private Integer id;
    @Colonne
    private Integer idEnchere;
    @Colonne
    private Integer idClient;
    @Colonne
    private Date date;
    @Colonne
    private Double montant;



    public static List<Offre> getOffreParEnchere(int idEnchere) throws Exception {
        try {
            Offre offre = new Offre();
            offre.setIdEnchere(idEnchere);
            return (List<Offre>) offre.list(Database.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    CONSTRUCTEURS
    public Offre() {
    }

    public Offre(Integer id) {
        this.id = id;
    }

    public Offre(Integer idEnchere, Integer idClient, Double montant) {
        this.idEnchere = idEnchere;
        this.idClient = idClient;
        this.montant = montant;
    }

    public Offre(Integer idEnchere, Integer idClient, Date date, Double montant) {
        this.idEnchere = idEnchere;
        this.idClient = idClient;
        this.date = date;
        this.montant = montant;
    }

    //    GETTERS ET SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(Integer idEnchere) {
        this.idEnchere = idEnchere;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
