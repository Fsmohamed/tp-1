package com.example.demo.dtos;

import com.example.demo.enums.TypeCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteRequestDTO {
    private Double solde;
    private String currency;
    private TypeCompte type;
}
