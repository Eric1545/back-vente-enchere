package com.enchere.postgres.repos;

import com.enchere.postgres.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}