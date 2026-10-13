package com.example.demo.mappers;

import com.example.demo.dtos.CompteRequestDTO;
import com.example.demo.dtos.CompteResponseDTO;
import com.example.demo.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {

    public CompteResponseDTO fromCompte(Compte compte) {
        if (compte == null) {
            return null;
        }
        CompteResponseDTO responseDTO = new CompteResponseDTO();
        BeanUtils.copyProperties(compte, responseDTO);
        return responseDTO;
    }

    public Compte fromCompteRequestDTO(CompteRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        Compte compte = new Compte();
        BeanUtils.copyProperties(requestDTO, compte);
        return compte;
    }
}
