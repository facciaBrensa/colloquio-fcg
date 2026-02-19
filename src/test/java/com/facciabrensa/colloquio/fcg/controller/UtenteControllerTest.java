package com.facciabrensa.colloquio.fcg.controller;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UtenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtenteRepository utenteRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void creazioneUtente() throws Exception {
        long numeroUtentiIniziale = utenteRepository.count();

        UtenteDTO dto = new UtenteDTO(
                null,
                "Mario",
                "Rossi",
                "mariorossi@gmail.com",
                "Via Roma 1 Roma"
        );

        mockMvc.perform(post("/utenti")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        assertEquals(numeroUtentiIniziale + 1, utenteRepository.count());
    }

    @Test
    public void recuperoUtenti() throws Exception {
        String responseContent = mockMvc.perform(get("/utenti"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UtenteDTO[] utenti = objectMapper.readValue(responseContent, UtenteDTO[].class);

        assertEquals(utenteRepository.count(), utenti.length);
    }

    @Test
    public void aggiornamentoUtenteIntegration() throws Exception {
        UtenteEntity utente = utenteRepository.save(new UtenteEntity(
                null,
                "Mario",
                "Rossi",
                "mario@gmail.com",
                "Via Roma"
        ));

        UtenteDTO utenteDto = new UtenteDTO(
                null,
                "Giuseppe",
                "Bianchi",
                "giuseppe@gmail.com",
                "Via Napoli"
        );

        mockMvc.perform(put("/utenti/" + utente.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(utenteDto)))
                .andExpect(status().isOk());

        utente = utenteRepository.findById(utente.getId()).orElseThrow();

        assertEquals("Giuseppe", utente.getNome());
    }

    @Test
    public void eliminazioneUtente() throws Exception {
        UtenteEntity utente = utenteRepository.save(new UtenteEntity(
                null,
                "Mario",
                "Rossi",
                "mario@gmail.com",
                "Via Roma"
        ));

        mockMvc.perform(delete("/utenti/" + utente.getId()))
                .andExpect(status().isOk());

        assertTrue(utenteRepository.findById(utente.getId()).isEmpty());
    }
}