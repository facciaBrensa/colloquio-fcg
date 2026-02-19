package com.facciabrensa.colloquio.fcg.controller;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UtenteAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtenteRepository utenteRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void creazioneUtenteMassiva() throws Exception {
        String csvContent = """
                Mario,Rossi,mario@gmail.com,Via Roma
                Luigi,Verdi,luigi@gmail.com,Via Milano
                """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "utenti.csv",
                "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8)
        );

        String responseContent = mockMvc.perform(multipart("/admin/utenti/import")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UtenteDTO[] utenti = objectMapper.readValue(responseContent, UtenteDTO[].class);

        assertEquals(2, utenti.length);
        assertEquals("Mario", utenti[0].getNome());
    }

    @Test
    public void ricercaUtenti() throws Exception {
        utenteRepository.save(new UtenteEntity(
                null,
                "Mario",
                "Rossi",
                "mario@gmail.com",
                "Via Roma"
        ));

        utenteRepository.save(new UtenteEntity(
                null,
                "Luigi",
                "Verdi",
                "luigi@gmail.com",
                "Via Milano"
        ));

        String responseContent = mockMvc.perform(get("/admin/utenti")
                        .param("nome", "Mario")
                        .param("cognome", "Rossi"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UtenteDTO[] utenti = objectMapper.readValue(responseContent, UtenteDTO[].class);

        assertEquals(1, utenti.length);
        assertEquals("Mario", utenti[0].getNome());
    }
}
