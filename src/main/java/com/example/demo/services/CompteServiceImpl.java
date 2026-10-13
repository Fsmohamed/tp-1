package com.example.demo.services;

import com.example.demo.dtos.CompteRequestDTO;
import com.example.demo.dtos.CompteResponseDTO;
import com.example.demo.entities.Compte;
import com.example.demo.exceptions.CompteNotFoundException;
import com.example.demo.mappers.CompteMapper;
import com.example.demo.repositories.CompteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;

    public CompteServiceImpl(CompteRepository compteRepository, CompteMapper compteMapper) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
    }

    @Override
    public CompteResponseDTO addCompte(CompteRequestDTO compteDTO) {
        Compte compte = compteMapper.fromCompteRequestDTO(compteDTO);
        compte.setId(UUID.randomUUID().toString());
        compte.setDateCreation(new Date());
        Compte savedCompte = compteRepository.save(compte);
        return compteMapper.fromCompte(savedCompte);
    }

    @Override
    public CompteResponseDTO updateCompte(String id, CompteRequestDTO compteDTO) {
        Compte existingCompte = compteRepository.findById(id)
                .orElseThrow(() -> new CompteNotFoundException(String.format("Compte with ID %s not found", id)));

        if (compteDTO.getSolde() != null) {
            existingCompte.setSolde(compteDTO.getSolde());
        }
        if (compteDTO.getCurrency() != null) {
            existingCompte.setCurrency(compteDTO.getCurrency());
        }
        if (compteDTO.getType() != null) {
            existingCompte.setType(compteDTO.getType());
        }

        Compte updatedCompte = compteRepository.save(existingCompte);
        return compteMapper.fromCompte(updatedCompte);
    }

    @Override
    public CompteResponseDTO getCompte(String id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new CompteNotFoundException(String.format("Compte with ID %s not found", id)));
        return compteMapper.fromCompte(compte);
    }

    @Override
    public List<CompteResponseDTO> listComptes() {
        return compteRepository.findAll()
                .stream()
                .map(compteMapper::fromCompte)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompte(String id) {
        if (!compteRepository.existsById(id)) {
            throw new CompteNotFoundException(String.format("Compte with ID %s not found", id));
        }
        compteRepository.deleteById(id);
    }
}
