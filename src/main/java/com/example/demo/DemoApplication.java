package com.example.demo;

import com.example.demo.entities.Compte;
import com.example.demo.enums.TypeCompte;
import com.example.demo.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository) {
        return args -> {
            for (int i = 0; i < 5; i++) {
                Compte compte = Compte.builder()
                        .id(UUID.randomUUID().toString())
                        .solde(1000.0 + Math.random() * 90000.0)
                        .dateCreation(new Date())
                        .type(Math.random() > 0.5 ? TypeCompte.COURANT : TypeCompte.EPARGNE)
                        .currency(Math.random() > 0.5 ? "MAD" : "EUR")
                        .build();
                compteRepository.save(compte);
            }
            System.out.println("========== Initialisation des données DAO terminée ==========");
            compteRepository.findAll().forEach(c -> {
                System.out.println(c.getId() + " => Solde: " + c.getSolde() + " " + c.getCurrency() + " | Type: " + c.getType());
            });
        };
    }
}
