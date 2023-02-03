package com.enchere.postgres.models;

import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;
import com.enchere.utils.Database;

import java.util.List;
import java.util.Objects;

@Table
public class Categorie extends GeneriqueDAO {
    @Colonne
    private Integer id;
    @Colonne
    private String categorie;
    private int nbEnchere;

    public static List<Categorie> getClassementCategorie() throws Exception {
        try {
            List<Categorie> categories = (List<Categorie>) new Categorie().list(Database.getConnection());
            String[][] categorieEnchere = GeneriqueDAO.execute(Database.getConnection(), "select idcategorie, count(idcategorie) as nb from enchere group by idcategorie");
            int nbCategorieEnchere = categorieEnchere.length;
            for (int i = 0; i < nbCategorieEnchere; i++) {
                int nbCategorie = categories.size();
                for (Categorie category : categories) {
                    if (Objects.equals(category.getId(), Integer.valueOf(categorieEnchere[i][0]))) {
                        categories.get(i).setNbEnchere(Integer.parseInt(categorieEnchere[i][1]));
                    }
                }
            }
            categories.sort((o1, o2) -> {
                return o1.getNbEnchere() > o2.getNbEnchere() ? -1 : 0;
            });
            return categories;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    CONSTRUCTEUR
    public Categorie() {
    }

    public Categorie(Integer id) {
        this.id = id;
    }

    public Categorie(String categorie) {
        this.categorie = categorie;
    }


//    GETTERS ET SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getNbEnchere() {
        return nbEnchere;
    }

    public void setNbEnchere(int nbEnchere) {
        this.nbEnchere = nbEnchere;
    }
}
