package com.enchere.postgres.models;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.enchere.org.gen.dao.annotations.Colonne;
import com.enchere.org.gen.dao.annotations.Table;
import com.enchere.org.gen.dao.utils.GeneriqueDAO;
import com.enchere.utils.Database;
import com.enchere.utils.Utils;
import org.springframework.data.relational.core.sql.In;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Table
public class Enchere extends GeneriqueDAO {
    @Colonne
    private Integer id;
    @Colonne
    private String produit;
    @Colonne
    private String description;
    @Colonne
    private Double prixMin;
    @Colonne
    private Timestamp dateDebut;
    @Colonne
    private Time duree;
    @Colonne
    private Integer idCategorie;
    @Colonne
    private Integer idClient;
    @Colonne
    private Boolean fini;
    private Boolean dejaMiser;
    private String status;


    public static ClientDao recordAcheteur() throws Exception {
        try {
            List<Vendu> vendus = (List<Vendu>) new Vendu().list(Database.getConnection());
            List<Offre> offres = new ArrayList<>();
            for (Vendu vendu: vendus) {
                offres.add((Offre) new Offre(vendu.getIdOffre()).list(Database.getConnection()).get(0));
            }
            offres.sort((o1, o2) -> {
                return o1.getMontant() > o2.getMontant() ? -1 : 0;
            });
            if (offres.size() != 0) {
                return  (ClientDao) new ClientDao(offres.get(0).getIdClient()).list(Database.getConnection()).get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static Enchere recordEnchere() throws Exception {
        try {
            List<Vendu> vendus = (List<Vendu>) new Vendu().list(Database.getConnection());
            List<Offre> offres = new ArrayList<>();
            for (Vendu vendu: vendus) {
                offres.add((Offre) new Offre(vendu.getIdOffre()).list(Database.getConnection()).get(0));
            }
            offres.sort((o1, o2) -> {
                return o1.getMontant() > o2.getMontant() ? -1 : 0;
            });
            if (offres.size() != 0) {
                return  (Enchere) new Enchere(offres.get(0).getIdEnchere()).list(Database.getConnection()).get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static List<Enchere> getEnchereParClient(int idClient) throws Exception {
        try {
            Enchere enchere = new Enchere();
            enchere.setIdClient(idClient);
            return (List<Enchere>) enchere.list(Database.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



    public static Enchere fiche(int idEnchere) throws Exception {
        try {
            Enchere enchere = (Enchere) new Enchere(idEnchere).list(Database.getConnection()).get(0);
            enchere.setDejaMiser();
            return enchere;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> leursEnchereEnAttente(int monIdClient) throws Exception {
        try {
            List<Enchere> enchereEnAttente = enchereEnAttente();
            int nbEnchereEnAttente = enchereEnAttente.size();
            for (int i = 0; i < nbEnchereEnAttente; i++) {
                if (enchereEnAttente.get(i).getIdClient() == monIdClient) {
                    enchereEnAttente.remove(i);
                    i--;
                    nbEnchereEnAttente--;
                }
            }
            return enchereEnAttente;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> mesEnchereEnAttente(int monIdClient) throws Exception {
        try {
            List<Enchere> enchereEnAttente = enchereEnAttente();
            int nbEnchereEnAttente = enchereEnAttente.size();
            for (int i = 0; i < nbEnchereEnAttente; i++) {
                if (enchereEnAttente.get(i).getIdClient() != monIdClient) {
                    enchereEnAttente.remove(i);
                    i--;
                    nbEnchereEnAttente--;
                }
            }
            return enchereEnAttente;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static List<Enchere> enchereEnAttente() throws Exception {
        try {
            List<Enchere> enchereEnCours = toutesLesEnchereEnCours();
            List<Enchere> encherePasFini = encherePasFini();
            int nbEncherePasFini = encherePasFini.size();
            for (int i = 0; i < nbEncherePasFini; i++) {
                for (Enchere enchereEnCour : enchereEnCours) {
                    if (Objects.equals(enchereEnCour.getId(), encherePasFini.get(i).getId())) {
                        encherePasFini.remove(i);
                        i--;
                        nbEncherePasFini--;
                        break;
                    }
                }
                encherePasFini.get(i).setStatus("En attente");
            }
            return encherePasFini;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> leursEnchereTermine(int monIdClient) throws Exception {
        try {
            List<Enchere> enchereTermine = enchereFini();
            int nbEnchereTermine = enchereTermine.size();
            for (int i = 0; i < nbEnchereTermine; i++) {
                if (enchereTermine.get(i).getIdClient() == monIdClient) {
                    enchereTermine.remove(i);
                    i--;
                    nbEnchereTermine--;
                }
            }
            return enchereTermine;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> mesEnchereTermine(int monIdClient) throws Exception {
        try {
            List<Enchere> enchereTermine = enchereFini();
            int nbEnchereTermine = enchereTermine.size();
            for (int i = 0; i < nbEnchereTermine; i++) {
                if (enchereTermine.get(i).getIdClient() != monIdClient) {
                    enchereTermine.remove(i);
                    i--;
                    nbEnchereTermine--;
                }
            }
            return enchereTermine;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> leursEnchereEnCours(int idClient) throws Exception {
        try {
            List<Enchere> enchereEnCours = toutesLesEnchereEnCours();
            int nbEnchereEnCours = enchereEnCours.size();
            for (int i = 0; i < nbEnchereEnCours; i++) {
                if (enchereEnCours.get(i).getIdClient() == idClient) {
                    enchereEnCours.remove(i);
                    i--;
                    nbEnchereEnCours--;
                }
            }
            return enchereEnCours;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> mesEnchereEnCours(int idClient) throws Exception {
        try {
            List<Enchere> enchereEnCours = toutesLesEnchereEnCours();
            int nbEnchereEnCours = enchereEnCours.size();
            for (int i = 0; i < nbEnchereEnCours; i++) {
                if (enchereEnCours.get(i).getIdClient() != idClient) {
                    enchereEnCours.remove(i);
                    i--;
                    nbEnchereEnCours--;
                }
            }
            return enchereEnCours;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> toutesLesEnchereEnCours() throws Exception {
        try {
            List<Enchere> encheres = encherePasFini();
            int nbEnchere = encheres.size();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa = " + nbEnchere);
            for (int i = 0; i < nbEnchere; i++) {
                if (encheres.get(i).getDateDebut().after(new Timestamp(new Date().getTime()))) {
                    System.out.println("AAAA = " + encheres.get(i).getDateDebut());
                    System.out.println("BBBB = " + new Timestamp(new Date().getTime()));
                    encheres.remove(i);
                    i--;
                    nbEnchere--;
                }
                else {
                    encheres.get(i).setDejaMiser();
                    encheres.get(i).setStatus("En cours");
                }
            }
            return encheres;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> encherePasFini() throws Exception {
        try {
            terminerEnchere();
            return (List<Enchere>) new Enchere(false).list(Database.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> enchereFini() throws Exception {
        try {
            terminerEnchere();
            List<Enchere> encheres = (List<Enchere>) new Enchere(true).list(Database.getConnection());
            for (Enchere enchere: encheres) {
                enchere.setStatus("Termine");
            }
            return encheres;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Enchere> toutesLesEnchere() throws Exception {
        try {
            terminerEnchere();
            return (List<Enchere>) new Enchere().list(Database.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void terminerEnchereById(int idEnchere) throws Exception {
        try {
            String dureeRestant = getDureeRestantById(idEnchere);
            if (dureeRestant.toCharArray()[0] == '-') {
                System.out.println("Update fini enchere");
                new Enchere(true).update(Database.getConnection(), String.valueOf(idEnchere));
                Integer idDerniereOffre = getIdDerniereOffre(idEnchere);
                if (idDerniereOffre!=null) {
                    GeneriqueDAO.executeUpdate(Database.getConnection(), "insert into vendu values (" + idDerniereOffre + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Offre getDerniereOffre(int idEnchere) throws Exception {
        try {
            Offre offre = new Offre();
            offre.setId(getIdDerniereOffre(idEnchere));
            List<Offre> offres = (List<Offre>) offre.list(Database.getConnection());
            if (offres.size() != 0) {
                return offres.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Integer getIdDerniereOffre(int idEnchere) throws Exception {
        try {
            String[][] tab = GeneriqueDAO.execute(Database.getConnection(), "select * from offre where idenchere=" + idEnchere + " order by date desc limit 1");
            if (tab.length!=0) {
                return Integer.parseInt(tab[0][0]);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void terminerEnchere() throws Exception {
        try {
            List<Enchere> encheres = (List<Enchere>) new Enchere(false).list(Database.getConnection());
            for (Enchere enchere: encheres) {
                terminerEnchereById(enchere.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static String getDureeRestantById(int idEnchere) throws SQLException {
        try {
            return GeneriqueDAO.execute(Database.getConnection(), "select ((dateDebut+duree) - now()) as dureeRestant from enchere where id=" + idEnchere + " order by id asc")[0][0];
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static String[] getDureeRestant() throws SQLException {
        try {
            String[][] tabDuree = GeneriqueDAO.execute(Database.getConnection(), "select ((dateDebut+duree) - now()) as dureeRestant from enchere order by id asc");
            int tailleTabDuree = tabDuree.length;
            String[] reponse = new String[tailleTabDuree];
            for (int i = 0; i < tailleTabDuree; i++) {
                reponse[i] = tabDuree[i][0];
            }
            return reponse;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }



//  CONSTRUCTEUR
    public Enchere() {
    }

    public Enchere(Integer id) {
        this.id = id;
    }

    public Enchere(Boolean fini) {
        this.setFini(fini);
    }

    public Enchere(String produit, String description, Double prixMin, Timestamp dateDebut, Time duree, Integer idCategorie, Integer idClient) {
        this.produit = produit;
        this.description = description;
        this.prixMin = prixMin;
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.idCategorie = idCategorie;
        this.idClient = idClient;
    }


//  GETTERS ET SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Double getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(Double prixMin) {
        this.prixMin = prixMin;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Integer getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Boolean getFini() {
        return fini;
    }

    public void setFini(Boolean fini) {
        this.fini = fini;
    }

    public Boolean getDejaMiser() {
        return dejaMiser;
    }

    public void setDejaMiser(Boolean dejaMiser) {
        this.dejaMiser = dejaMiser;
    }

    public void setDejaMiser() throws SQLException {
        try {
            this.setDejaMiser(GeneriqueDAO.execute(Database.getConnection(), "select * from offre where idEnchere=" + this.getId() + "and idClient=" + this.getIdClient()).length != 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static Enchere getEnchereById(int idEnchere) throws Exception {
        try {
            List<Enchere> encheres = null;
            encheres = (List<Enchere>) new Enchere(idEnchere).list(Database.getConnection());
            if (encheres.size()>0) {
                return encheres.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
