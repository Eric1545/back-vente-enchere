package com.enchere.postgres.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "solde")
public class Solde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idclient", nullable = false)
//    @JsonIgnoreProperties("soldes")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "soldes"})
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmouvementsolde", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "soldes"})
    private MouvementSolde mouvementSolde;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "valider")
    private Boolean valider = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MouvementSolde getMouvementSolde() {
        return mouvementSolde;
    }

    public void setMouvementSolde(MouvementSolde mouvementSolde) {
        this.mouvementSolde = mouvementSolde;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean getValider() {
        return valider;
    }

    public void setValider(Boolean valider) {
        this.valider = valider;
    }

}
