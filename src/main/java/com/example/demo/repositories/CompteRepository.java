package com.example.demo.repositories;

import com.example.demo.entities.Compte;
import com.example.demo.enums.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "comptes", collectionResourceRel = "comptes")
public interface CompteRepository extends JpaRepository<Compte, String> {

    @RestResource(path = "/byType")
    List<Compte> findByType(@Param("t") TypeCompte type);

    @RestResource(path = "/byCurrency")
    List<Compte> findByCurrency(@Param("c") String currency);
}
