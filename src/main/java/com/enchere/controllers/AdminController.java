package com.enchere.controllers;

import com.enchere.helper.SequenceHelper;
import com.enchere.postgres.models.Admin;
import com.enchere.postgres.models.Token;
import com.enchere.postgres.models.Token_user;
import com.enchere.utils.Database;
import com.enchere.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/projetEnchere/admin")
public class AdminController {


    //    build create admin REST API
    @GetMapping
    public List<Admin> findAll() throws Exception {
        try {
            System.out.println("ok");
            return (List<Admin>) new Admin().list(Database.getConnection());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build create admin REST API
    @PostMapping
    public String login(@RequestBody Admin admin) throws Exception {
        try {
            Connection con = Database.getConnection();
            Integer iduser = admin.getIdAdmin();
            if (iduser != null){
                String token = Utils.creationToken(admin.getEmail(), admin.getMdp());
                System.out.println("Le token = "+token);
                boolean exist = Token.verifierExistanceTokenAndValidation(admin.getEmail(), admin.getMdp());
                if (exist == false){
                    System.out.println("Token non existant ou non valide !");
                    Timestamp dateexp = Utils.getDateExpiration(30);
                    Token tok = new Token();
                    tok.setToken(token);
                    tok.setDateexpiration(dateexp);
                    tok.insererToken(con);
                    int idtoken = Integer.parseInt(SequenceHelper.last_value("token", con));
                    System.out.println("idtoken crée ="+idtoken);
                    Token_user tu = new Token_user();
                    tu.setId_token(idtoken);
                    tu.setId_admin(iduser);
                    tu.insererTokenUser(con);
                    con.close();
                }
                else {
                    System.out.println("Token existant ! Oueh !");
                }
                return token;
            }
            else return "";

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //    build get admin by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) throws Exception {
        Admin admin = null;
        try {
            admin = ((List<Admin>) new Admin(id).list(Database.getConnection())).get(0);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build update admin REST API
    @PutMapping("{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin adminDetails) throws Exception {
        try {
            adminDetails.update(Database.getConnection(), String.valueOf(id));
            return ResponseEntity.ok(adminDetails);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //     build delete admin REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable int id) throws SQLException {
        try {
            new Admin().delete(Database.getConnection(), String.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
