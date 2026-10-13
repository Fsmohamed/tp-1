package com.example.demo.web;

import com.example.demo.dtos.CompteRequestDTO;
import com.example.demo.dtos.CompteResponseDTO;
import com.example.demo.enums.TypeCompte;
import com.example.demo.services.CompteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompteService compteService;

    @Test
    @DisplayName("REST API: Obtenir la liste des comptes")
    void shouldGetAllComptes() throws Exception {
        mockMvc.perform(get("/api/comptes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    @DisplayName("REST API: Créer, lire, modifier et supprimer un compte (CRUD complet)")
    void shouldPerformFullCrudOperations() throws Exception {
        // 1. CREATE
        CompteRequestDTO requestDTO = CompteRequestDTO.builder()
                .solde(12000.0)
                .currency("MAD")
                .type(TypeCompte.COURANT)
                .build();

        String responseContent = mockMvc.perform(post("/api/comptes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.solde", is(12000.0)))
                .andExpect(jsonPath("$.currency", is("MAD")))
                .andExpect(jsonPath("$.type", is("COURANT")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CompteResponseDTO createdCompte = objectMapper.readValue(responseContent, CompteResponseDTO.class);
        String compteId = createdCompte.getId();

        // 2. READ BY ID
        mockMvc.perform(get("/api/comptes/{id}", compteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(compteId)))
                .andExpect(jsonPath("$.solde", is(12000.0)));

        // 3. UPDATE
        CompteRequestDTO updateRequest = CompteRequestDTO.builder()
                .solde(18500.0)
                .currency("EUR")
                .type(TypeCompte.EPARGNE)
                .build();

        mockMvc.perform(put("/api/comptes/{id}", compteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(compteId)))
                .andExpect(jsonPath("$.solde", is(18500.0)))
                .andExpect(jsonPath("$.currency", is("EUR")))
                .andExpect(jsonPath("$.type", is("EPARGNE")));

        // 4. DELETE
        mockMvc.perform(delete("/api/comptes/{id}", compteId))
                .andExpect(status().isNoContent());

        // 5. VERIFY DELETION
        mockMvc.perform(get("/api/comptes/{id}", compteId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Spring Data REST: Accéder à /rest/comptes et tester la projection p1")
    void shouldAccessSpringDataRestAndProjection() throws Exception {
        // Test Spring Data REST base collection
        mockMvc.perform(get("/rest/comptes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.comptes", notNullValue()));

        // Test Spring Data REST projection p1
        mockMvc.perform(get("/rest/comptes?projection=p1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.comptes[0].solde", notNullValue()))
                .andExpect(jsonPath("$._embedded.comptes[0].type", notNullValue()));
    }

    @Test
    @DisplayName("Swagger / OpenAPI: Vérifier la disponibilité de /v3/api-docs")
    void shouldAccessOpenApiDocs() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi", notNullValue()))
                .andExpect(jsonPath("$.paths['/api/comptes']", notNullValue()));
    }
}
