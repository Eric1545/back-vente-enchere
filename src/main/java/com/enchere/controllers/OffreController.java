package com.enchere.controllers;


import com.enchere.postgres.models.Offre;
import com.enchere.utils.Database;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/projetEnchere/offre")
public class OffreController {

    //    build create offre REST API
    @GetMapping
    public List<Offre> findAll() throws Exception {
        try {
            return (List<Offre>) new Offre().list(Database.getConnection());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build get offre by id REST API
    @GetMapping("{id}")
    public List<Offre> getOffreById(@PathVariable int id) throws Exception {
        try {
            return Offre.getOffreParEnchere(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



    //    build get offre by id REST API
    @GetMapping("/getOffreParEnchere/{idEnchere}")
    public List<Offre> getOffreParEnchere(@PathVariable int idEnchere) throws Exception {
        try {
            return (List<Offre>) Offre.getOffreParEnchere(idEnchere);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build update offre REST API
    @PutMapping("{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable int id, @RequestBody Offre offreDetails) throws Exception {
        try {
            offreDetails.update(Database.getConnection(), String.valueOf(id));
            return ResponseEntity.ok(offreDetails);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build delete offre REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Offre> deleteOffre(@PathVariable int id) throws SQLException {
        try {
            new Offre().delete(Database.getConnection(), String.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
