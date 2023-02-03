package com.enchere.controllers;


import com.enchere.postgres.models.Categorie;
import com.enchere.utils.Database;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/projetEnchere/categorie")
public class CategorieController {

    //    build create categorie REST API
    @GetMapping
    public List<Categorie> findAll() throws Exception {
        try {
            return (List<Categorie>) new Categorie().list(Database.getConnection());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build create categorie REST API
    @GetMapping("/classementCategorie")
    public List<Categorie> getClassementCategorie() throws Exception {
        try {
            return Categorie.getClassementCategorie();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build create categorie REST API
    @PostMapping
    public void login(@RequestBody Categorie categorie) throws Exception {
        try {
            categorie.save(Database.getConnection());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build get categorie by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable int id) throws Exception {
        try {
            Categorie categorie = ((List<Categorie>) new Categorie(id).list(Database.getConnection())).get(0);
            return ResponseEntity.ok(categorie);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build update categorie REST API
    @PutMapping("{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable int id, @RequestBody Categorie categorieDetails) throws Exception {
        try {
            categorieDetails.update(Database.getConnection(), String.valueOf(id));
            return ResponseEntity.ok(categorieDetails);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build delete categorie REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Categorie> deleteCategorie(@PathVariable int id) throws SQLException {
        try {
            new Categorie().delete(Database.getConnection(), String.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
