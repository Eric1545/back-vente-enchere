package com.enchere.postgres.models;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;

@Table
public class Vendu extends GeneriqueDAO {
    @Colonne
    private Integer idOffre;

    public Vendu() {
    }

    public Vendu(Integer idOffre) {
        this.idOffre = idOffre;
    }

    public Integer getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(Integer idOffre) {
        this.idOffre = idOffre;
    }
}
