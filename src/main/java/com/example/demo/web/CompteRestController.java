package com.example.demo.web;

import com.example.demo.dtos.CompteRequestDTO;
import com.example.demo.dtos.CompteResponseDTO;
import com.example.demo.services.CompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@Tag(name = "Gestion des Comptes", description = "Endpoints REST pour la gestion du cycle de vie des comptes bancaires")
public class CompteRestController {

    private final CompteService compteService;

    public CompteRestController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les comptes", description = "Retourne la liste de l'ensemble des comptes bancaires")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public ResponseEntity<List<CompteResponseDTO>> getAllComptes() {
        return ResponseEntity.ok(compteService.listComptes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un compte par son ID", description = "Retourne les détails d'un compte pour un identifiant donné")
    @ApiResponse(responseCode = "200", description = "Compte trouvé")
    @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    public ResponseEntity<CompteResponseDTO> getCompteById(@PathVariable String id) {
        return ResponseEntity.ok(compteService.getCompte(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau compte", description = "Ajoute un nouveau compte bancaire dans la base de données")
    @ApiResponse(responseCode = "201", description = "Compte créé avec succès")
    public ResponseEntity<CompteResponseDTO> saveCompte(@RequestBody CompteRequestDTO compteDTO) {
        CompteResponseDTO createdCompte = compteService.addCompte(compteDTO);
        return new ResponseEntity<>(createdCompte, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un compte existant", description = "Met à jour les informations d'un compte désigné par son ID")
    @ApiResponse(responseCode = "200", description = "Compte mis à jour")
    @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    public ResponseEntity<CompteResponseDTO> updateCompte(@PathVariable String id, @RequestBody CompteRequestDTO compteDTO) {
        return ResponseEntity.ok(compteService.updateCompte(id, compteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un compte", description = "Supprime un compte bancaire par son ID")
    @ApiResponse(responseCode = "204", description = "Compte supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    public ResponseEntity<Void> deleteCompte(@PathVariable String id) {
        compteService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }
}
