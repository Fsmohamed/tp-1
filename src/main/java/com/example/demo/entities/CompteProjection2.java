package com.example.demo.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p2", types = Compte.class)
public interface CompteProjection2 {
    String getId();
    Double getSolde();
    String getCurrency();
}
