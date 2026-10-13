package com.example.demo.entities;

import com.example.demo.enums.TypeCompte;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p1", types = Compte.class)
public interface CompteProjection1 {
    String getId();
    Double getSolde();
    TypeCompte getType();
}
