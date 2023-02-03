package com.enchere.controllers;


import com.enchere.postgres.models.ClientDao;
import com.enchere.postgres.models.Enchere;
import com.enchere.postgres.models.Offre;
import com.enchere.postgres.models.RechercheAvance;
import com.enchere.utils.Database;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("api/projetEnchere/enchere")
public class EnchereController {

    //    build create enchere REST API
   /* @GetMapping("/recordAcheteur")
    public ClientDao recordAcheteur() throws Exception {
        try {
            return  Enchere.recordAcheteur();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }*/

    //    build create enchere REST API
    @GetMapping("/recordEnchere")
    public Enchere recordEnchere() throws Exception {
        try {
            return Enchere.recordEnchere();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build create enchere REST API
    @PostMapping("/rechercheAvancer")
    public List<Enchere> rechercheAvancer(@RequestBody RechercheAvance rechercheAvance) throws Exception {
        try {
            return rechercheAvance.rechercheAvancer();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/getEnchereParClient/{idClient}")
    public List<Enchere> getEnchereParClient(@PathVariable int idClient) throws Exception {
        try {
            return (List<Enchere>) Enchere.getEnchereParClient(idClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/leursEnchere/{monIdClient}")
    public List<Enchere> leursEnchere(@PathVariable int monIdClient) throws Exception {
        try {
            List<Enchere> encheres = Enchere.leursEnchereEnAttente(monIdClient);
            encheres.addAll(Enchere.leursEnchereEnCours(monIdClient));
            encheres.addAll(Enchere.leursEnchereTermine(monIdClient));
            encheres.sort((o1, o2) -> {
                return (o1.getDateDebut().getTime() + o1.getDuree().getTime()) > (o2.getDateDebut().getTime() + o2.getDuree().getTime()) ? -1 : 0;
            });
            return encheres;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/mesEnchere/{monIdClient}")
    public List<Enchere> mesEnchere(@PathVariable int monIdClient) throws Exception {
        try {
            List<Enchere> encheres = Enchere.mesEnchereEnAttente(monIdClient);
            encheres.addAll(Enchere.mesEnchereEnCours(monIdClient));
            encheres.addAll(Enchere.mesEnchereTermine(monIdClient));
            encheres.sort((o1, o2) -> {
                return (o1.getDateDebut().getTime() + o1.getDuree().getTime()) > (o2.getDateDebut().getTime() + o2.getDuree().getTime()) ? -1 : 0;
            });
            return encheres;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/leursEnchereEnAttente/{monIdClient}")
    public List<Enchere> leursEnchereEnAttente(@PathVariable int monIdClient) throws Exception {
        try {
            return Enchere.leursEnchereEnAttente(monIdClient);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/mesEnchereEnAttente/{monIdClient}")
    public List<Enchere> mesEnchereEnAttente(@PathVariable int monIdClient) throws Exception {
        try {
            return Enchere.mesEnchereEnAttente(monIdClient);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/toutesLesEnchereEnAttente")
    public List<Enchere> toutesLesEnchereEnAttente() throws Exception {
        try {
            return Enchere.enchereEnAttente();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/leursEnchereTermine/{monIdClient}")
    public List<Enchere> leursEnchereTermine(@PathVariable int monIdClient) throws Exception {
        try {
            return Enchere.leursEnchereTermine(monIdClient);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/mesEnchereTermine/{monIdClient}")
    public List<Enchere> mesEnchereTermine(@PathVariable int monIdClient) throws Exception {
        try {
            return Enchere.mesEnchereTermine(monIdClient);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/toutesLesEnchereTermine")
    public List<Enchere> toutesLesEnchereTermine() throws Exception {
        try {
            return Enchere.enchereFini();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/fiche/{idEnchere}")
    public ResponseEntity<Enchere> fiche(@PathVariable int idEnchere) throws Exception {
        try {
            Enchere enchere = Enchere.fiche(idEnchere);
            return ResponseEntity.ok(enchere);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/leursEnchereEnCours/{monIdClient}")
    public List<Enchere> leursEnchereEnCours(@PathVariable int monIdClient) throws Exception {
        try {
            return Enchere.leursEnchereEnCours(monIdClient);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/mesEnchereEnCours/{monIdClient}")
    public List<Enchere> mesEnchereEnCours(@PathVariable int monIdClient) throws Exception {
        try {
            return Enchere.mesEnchereEnCours(monIdClient);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/toutesLesEnchereEnCours")
    public List<Enchere> toutesLesEnchereEnCours() throws Exception {
        try {
            return Enchere.toutesLesEnchereEnCours();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/toutesLesEnchere")
    public List<Enchere> toutesLesEnchere() throws Exception {
        try {
            return Enchere.toutesLesEnchere();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }




    //     build update offre REST API
    @PostMapping("/encherir")
    public OffreResponse encherir(@RequestBody Offre offreSave) throws Exception {
        try {
            return new OffreResponse(offreSave).verifierOffre();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build update enchere REST API
    @PostMapping("/enregistrer")
    public EnchereSaveResponse insertEnchere(@RequestBody Enchere enchereSave) throws Exception {
        try {
            return new EnchereSaveResponse(enchereSave).save();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build update enchere REST API
    @PutMapping("{id}")
    public ResponseEntity<Enchere> updateEnchere(@PathVariable int id, @RequestBody Enchere enchereDetails) throws Exception {
        try {
            enchereDetails.update(Database.getConnection(), String.valueOf(id));
            return ResponseEntity.ok(enchereDetails);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build delete enchere REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Enchere> deleteEnchere(@PathVariable int id) throws SQLException {
        try {
            new Enchere().delete(Database.getConnection(), String.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}


class EnchereSaveResponse {
    private boolean isSuccess;
    private Enchere enchere;
    private String message;

    public EnchereSaveResponse(Enchere enchere) {
        this.enchere = enchere;
    }

    public EnchereSaveResponse save() {
        try {
            this.enchere.save(Database.getConnection());
            this.isSuccess = true;
            this.message = "Insertion enchere reussi!";
        } catch (Exception e) {
            e.printStackTrace();
            this.isSuccess = false;
            this.message = "Insertion echoue!";
        }
        return this;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


class OffreResponse {
    private boolean isSuccess;
    private Offre offre;
    private String message;


    public OffreResponse verifierOffre() throws Exception {
        try {
            int idEnchere = this.offre.getIdEnchere();
            Enchere enchere = Enchere.getEnchereById(idEnchere);
            this.isSuccess = false;
            if (enchere != null) {
                if (!enchere.getFini()) {
                    if (!Objects.equals(enchere.getIdClient(), this.offre.getIdClient())) {
                        Offre derniereOffre = Enchere.getDerniereOffre(idEnchere);
                        if (this.offre.getMontant() > derniereOffre.getMontant()) {
                            this.offre.save(Database.getConnection());
                            this.message = "Votre mise de " + this.offre.getMontant() + "Ar a ete bien enregistre!";
                            this.isSuccess = true;
                        }
                        else {
                            this.message = "La derniere mise est " + derniereOffre.getMontant() + " Ar, veuillez la depasser pour miser!";
                        }
                    }
                    else {
                        this.message = "Vous ne pouvez pas encherir avec votre propre enchere!";
                    }
                }
                else {
                    this.message = "L'enchere avec l'id " + this.offre.getIdEnchere() + " n'est plus disponible!";
                }
            }
            else {
                this.message = "Il n'y a pas d'enchere avec l'id " + this.offre.getIdEnchere();
            }
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public OffreResponse(Offre offre) {
        this.offre = offre;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
