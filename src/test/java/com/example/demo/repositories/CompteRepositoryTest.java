package com.example.demo.repositories;

import com.example.demo.entities.Compte;
import com.example.demo.enums.TypeCompte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CompteRepositoryTest {

    @Autowired
    private CompteRepository compteRepository;

    private Compte compteTest;

    @BeforeEach
    void setUp() {
        compteRepository.deleteAll();
        compteTest = Compte.builder()
                .id(UUID.randomUUID().toString())
                .dateCreation(new Date())
                .solde(15000.0)
                .currency("MAD")
                .type(TypeCompte.COURANT)
                .build();
    }

    @Test
    @DisplayName("Test DAO: Enregistrement et recherche d'un compte par ID")
    void shouldSaveAndFindCompteById() {
        Compte savedCompte = compteRepository.save(compteTest);

        Optional<Compte> foundCompte = compteRepository.findById(savedCompte.getId());

        assertThat(foundCompte).isPresent();
        assertThat(foundCompte.get().getSolde()).isEqualTo(15000.0);
        assertThat(foundCompte.get().getCurrency()).isEqualTo("MAD");
        assertThat(foundCompte.get().getType()).isEqualTo(TypeCompte.COURANT);
    }

    @Test
    @DisplayName("Test DAO: Recherche des comptes par TypeCompte")
    void shouldFindComptesByType() {
        compteRepository.save(compteTest);
        Compte compteEpargne = Compte.builder()
                .id(UUID.randomUUID().toString())
                .dateCreation(new Date())
                .solde(30000.0)
                .currency("EUR")
                .type(TypeCompte.EPARGNE)
                .build();
        compteRepository.save(compteEpargne);

        List<Compte> comptesCourants = compteRepository.findByType(TypeCompte.COURANT);
        List<Compte> comptesEpargne = compteRepository.findByType(TypeCompte.EPARGNE);

        assertThat(comptesCourants).hasSize(1);
        assertThat(comptesEpargne).hasSize(1);
        assertThat(comptesCourants.get(0).getType()).isEqualTo(TypeCompte.COURANT);
        assertThat(comptesEpargne.get(0).getType()).isEqualTo(TypeCompte.EPARGNE);
    }

    @Test
    @DisplayName("Test DAO: Suppression d'un compte")
    void shouldDeleteCompte() {
        Compte savedCompte = compteRepository.save(compteTest);
        compteRepository.deleteById(savedCompte.getId());

        Optional<Compte> deletedCompte = compteRepository.findById(savedCompte.getId());
        assertThat(deletedCompte).isEmpty();
    }
}
