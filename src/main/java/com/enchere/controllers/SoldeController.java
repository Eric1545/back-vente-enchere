package com.enchere.controllers;


import com.enchere.postgres.models.Solde;
import com.enchere.postgres.repos.MouvementsoldeRepository;
import com.enchere.postgres.repos.SoldeRepository;
import com.enchere.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projetEnchere/soldes")
@CrossOrigin("*")
public class SoldeController {
    private final SoldeRepository soldeRepos;


    public SoldeController(SoldeRepository soldeRepos){
        this.soldeRepos = soldeRepos;
    }

    @GetMapping("/depot-non-valid")
    public ResponseData<List<Solde>> listDepotNonValider(){
        List<Solde> soldes = soldeRepos.findByValiderAndMouvementSolde_Id(false, 1);
        if (soldes == null){
            return new ResponseData<>(false, null);
        }
        return new ResponseData<>(true, soldes);
    }

    @PostMapping("/")
    public SoldeResponse save(@RequestBody Solde solde){
        if (solde.getMontant() < 0){
            return new SoldeResponse("montant ne peut pas etre inferieur a 0", false);
        }
//        System.out.println(solde.getClient());
        soldeRepos.save(solde);
        return new SoldeResponse("insertion reussi!", true);
    }

    @PostMapping("/update")
    public int update(@RequestBody Solde solde){
        return soldeRepos.updateValiderById(solde.getValider(), solde.getId());
    }

}

class SoldeResponse{
    private String message;
    private boolean isSuccess;

    public SoldeResponse(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
