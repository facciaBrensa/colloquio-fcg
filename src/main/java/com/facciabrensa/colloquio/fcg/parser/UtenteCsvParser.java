package com.facciabrensa.colloquio.fcg.parser;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.exception.FcgBadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class UtenteCsvParser {

    private final int NUMERO_CAMPI_UTENTE_CSV = 4;

    @Value("${file.csv.utente.indice.campo.nome}")
    private int indiceNome;
    @Value("${file.csv.utente.indice.campo.cognome}")
    private int indiceCognome;
    @Value("${file.csv.utente.indice.campo.email}")
    private int indiceEmail;
    @Value("${file.csv.utente.indice.campo.indirizzo}")
    private int indiceIndirizzo;
    @Value("${file.csv.utente.separatore.campi}")
    private String separatoreCsv;

    public List<UtenteDTO> parseUtenti(MultipartFile file) {
        List<UtenteDTO> listaUtenti = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String[] fields = line.split(separatoreCsv);

                if (fields.length != NUMERO_CAMPI_UTENTE_CSV) {
                    throw new FcgBadRequestException("Record non valido alla riga " + lineNumber);
                }

                listaUtenti.add(mapToDto(fields));
            }

        } catch (IOException exception) {
            throw new FcgBadRequestException("File CSV non valido.");
        }

        return listaUtenti;
    }

    private UtenteDTO mapToDto(String[] fields) {
        return new UtenteDTO(
                null,
                fields[indiceNome],
                fields[indiceCognome],
                fields[indiceEmail],
                fields[indiceIndirizzo]
        );
    }
}
