package com.enchere.postgres.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "mouvementsolde")
public class MouvementSolde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @OneToMany(mappedBy = "mouvementSolde")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "mouvementSolde"})
    private Set<Solde> soldes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Solde> getSoldes() {
        return soldes;
    }

    public void setSoldes(Set<Solde> soldes) {
        this.soldes = soldes;
    }

}
