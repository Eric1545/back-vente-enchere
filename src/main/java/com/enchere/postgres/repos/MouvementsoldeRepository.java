package com.enchere.postgres.repos;

import com.enchere.postgres.models.MouvementSolde;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementsoldeRepository extends JpaRepository<MouvementSolde, Integer> {
}