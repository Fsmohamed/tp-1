package com.example.demo.services;

import com.example.demo.dtos.CompteRequestDTO;
import com.example.demo.dtos.CompteResponseDTO;

import java.util.List;

public interface CompteService {
    CompteResponseDTO addCompte(CompteRequestDTO compteDTO);
    CompteResponseDTO updateCompte(String id, CompteRequestDTO compteDTO);
    CompteResponseDTO getCompte(String id);
    List<CompteResponseDTO> listComptes();
    void deleteCompte(String id);
}
