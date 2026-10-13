package com.example.demo.entities;

import com.example.demo.enums.TypeCompte;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compte {
    @Id
    private String id;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    private Double solde;

    private String currency;

    @Enumerated(EnumType.STRING)
    private TypeCompte type;
}
