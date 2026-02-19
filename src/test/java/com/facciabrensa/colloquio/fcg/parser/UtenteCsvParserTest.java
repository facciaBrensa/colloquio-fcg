package com.facciabrensa.colloquio.fcg.parser;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.exception.FcgBadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "file.csv.utente.indice.campo.nome=0",
        "file.csv.utente.indice.campo.cognome=1",
        "file.csv.utente.indice.campo.email=2",
        "file.csv.utente.indice.campo.indirizzo=3",
        "file.csv.utente.separatore.campi=,"
})
@ActiveProfiles("test")
class UtenteCsvParserTest {

    @Autowired
    private UtenteCsvParser utenteCsvParser;

    @Test
    public void parseUtenti() {
        String contenutoCsv = "Mario,Rossi,mariorossi@gmail.com,Via Roma 1 Roma";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "utenti.csv",
                "text/csv",
                contenutoCsv.getBytes()
        );

        List<UtenteDTO> result = utenteCsvParser.parseUtenti(file);

        assertEquals(1, result.size());
        assertEquals("Mario", result.get(0).getNome());
        assertEquals("Rossi", result.get(0).getCognome());
        assertEquals("mariorossi@gmail.com", result.get(0).getEmail());
        assertEquals("Via Roma 1 Roma", result.get(0).getIndirizzo());
    }

    @Test
    public void parseUtentiRecordNonValido() {
        String contenutoCsv = "Mario,Rossi,mariorossi@gmail.com";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "utenti.csv",
                "text/csv",
                contenutoCsv.getBytes()
        );

        assertThrows(FcgBadRequestException.class, () -> utenteCsvParser.parseUtenti(file));
    }

    @Test
    public void parseUtentiFileNonValido() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "utenti.csv",
                "text/csv",
                new byte[0]
        ) {
            @Override
            public java.io.InputStream getInputStream() throws IOException {
                throw new IOException();
            }
        };

        assertThrows(FcgBadRequestException.class, () -> utenteCsvParser.parseUtenti(file));
    }
}