package com.enchere.postgres.models;

import com.enchere.utils.Database;
import com.enchere.utils.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class RechercheAvance {
    private String produit;
    private String description;
    private String prixMinMin;
    private String prixMinMax;
    private String dateDebutMin;
    private String dateDebutMax;
    private String dureeMin;
    private String dureeMax;
    private String idCategorie;
    private String status;

    public List<Enchere> rechercheAvancer() throws Exception {
        try {
            Enchere enchere = new Enchere();
            if (produit != null) {
                enchere.setProduit(produit);
                System.out.println("produit = " + produit);
            }/*
            if (description != null) {
                enchere.setDescription(description);
                System.out.println("description = " + description);
            }*/
            if (idCategorie != null) {
                enchere.setIdCategorie(Integer.parseInt(idCategorie));
                System.out.println("idCategorie = " + idCategorie);
            }
            System.out.println("Ok");
            System.out.println("status = " + status);
            if (status != null) {
                enchere.setFini(Boolean.valueOf(status));
                System.out.println("status = " + status);
            }

            List<Enchere> encheres = (List<Enchere>) enchere.list(Database.getConnection());
            int nbEnchere = encheres.size();
            System.out.println("taille = " + nbEnchere);

            if (nbEnchere!=0 && prixMinMin != null) {
                Double prixMinMinD = Double.valueOf(prixMinMin);
                for (int i = 0; i < nbEnchere; i++) {
                    if (encheres.get(i).getPrixMin() < prixMinMinD) {
                        System.out.println("prixMinMinD");
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            if (nbEnchere!=0 && prixMinMax != null) {
                Double prixMinMaxD = Double.valueOf(prixMinMax);
                for (int i = 0; i < nbEnchere; i++) {
                    if (encheres.get(i).getPrixMin() > prixMinMaxD) {
                        System.out.println("prixMinMaxD");
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            if (nbEnchere!=0 && dateDebutMin != null) {
                Timestamp dateDebutMinD = Utils.toTimestamp(dateDebutMin);
                for (int i = 0; i < nbEnchere; i++) {
                    if (encheres.get(i).getDateDebut().before(dateDebutMinD)) {
                        System.out.println("dateDebutMinD");
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            if (nbEnchere!=0 && dateDebutMax != null) {
                Timestamp dateDebutMaxD = Utils.toTimestamp(dateDebutMax);
                for (int i = 0; i < nbEnchere; i++) {
                    if (encheres.get(i).getDateDebut().after(dateDebutMaxD)) {
                        System.out.println("dateDebut = " + encheres.get(i).getDateDebut());
                        System.out.println("dateDebutMaxD = " + dateDebutMaxD);
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            if (nbEnchere!=0 && dureeMin != null) {
                Time dureeMinD = Utils.toTime(dureeMin);
                for (int i = 0; i < nbEnchere; i++) {
                    if (encheres.get(i).getDuree().before(dureeMinD)) {
                        System.out.println("dureeMinD");
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            if (nbEnchere!=0 && dureeMax != null) {
                Time dureeMaxD = Utils.toTime(dureeMax);
                for (int i = 0; i < nbEnchere; i++) {
                    if (encheres.get(i).getDuree().after(dureeMaxD)) {
                        System.out.println("dureeMaxD");
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            if (nbEnchere!=0 && description != null) {
                for (int i = 0; i < nbEnchere; i++) {
                    if (!encheres.get(i).getDescription().contains(description)) {
                        System.out.println("Description = " + description);
                        encheres.remove(i);
                        i--;
                        nbEnchere--;
                    }
                }
                nbEnchere = encheres.size();
            }

            return encheres;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RechercheAvance() {
    }

    public RechercheAvance(String produit, String description, String prixMinMin, String prixMinMax, String dateDebutMin, String dateDebutMax, String dureeMin, String dureeMax, String idCategorie) {
        this.produit = produit;
        this.description = description;
        this.prixMinMin = prixMinMin;
        this.prixMinMax = prixMinMax;
        this.dateDebutMin = dateDebutMin;
        this.dateDebutMax = dateDebutMax;
        this.dureeMin = dureeMin;
        this.dureeMax = dureeMax;
        this.idCategorie = idCategorie;
    }

    // GETTERS ET SETTERS
    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrixMinMin() {
        return prixMinMin;
    }

    public void setPrixMinMin(String prixMinMin) {
        this.prixMinMin = prixMinMin;
    }

    public String getPrixMinMax() {
        return prixMinMax;
    }

    public void setPrixMinMax(String prixMinMax) {
        this.prixMinMax = prixMinMax;
    }

    public String getDateDebutMin() {
        return dateDebutMin;
    }

    public void setDateDebutMin(String dateDebutMin) {
        this.dateDebutMin = dateDebutMin;
    }

    public String getDateDebutMax() {
        return dateDebutMax;
    }

    public void setDateDebutMax(String dateDebutMax) {
        this.dateDebutMax = dateDebutMax;
    }

    public String getDureeMin() {
        return dureeMin;
    }

    public void setDureeMin(String dureeMin) {
        this.dureeMin = dureeMin;
    }

    public String getDureeMax() {
        return dureeMax;
    }

    public void setDureeMax(String dureeMax) {
        this.dureeMax = dureeMax;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
