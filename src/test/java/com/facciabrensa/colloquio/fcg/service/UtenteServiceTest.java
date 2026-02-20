package com.facciabrensa.colloquio.fcg.service;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.exception.FcgNotFoundException;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UtenteServiceTest {

    @Autowired
    private UtenteService utenteService;

    @MockitoBean
    private UtenteRepository utenteRepository;

    @Test
    public void creazioneUtente() {
        UtenteDTO utenteDto = new UtenteDTO(
                null,
                "Mario",
                "Rossi",
                "mariorossi@gmail.com",
                "Via Roma 1 Roma"
        );

        UtenteEntity utenteEntity = creaUtenteEntityPerTest();

        when(utenteRepository.save(any(UtenteEntity.class))).thenReturn(utenteEntity);

        UtenteDTO result = utenteService.creazioneUtente(utenteDto);

        assertNotNull(result);
        assertEquals(utenteEntity.getNome(), result.getNome());
        verify(utenteRepository).save(any());
    }

    @Test
    public void recuperoUtenti() {

        UtenteEntity entity = creaUtenteEntityPerTest();

        when(utenteRepository.findAll()).thenReturn(List.of(entity));

        List<UtenteDTO> utenti = utenteService.recuperoUtenti();

        assertEquals(1, utenti.size());
        verify(utenteRepository).findAll();
    }

    @Test
    public void aggiornamentoUtente() {
        UtenteEntity utenteEntity = creaUtenteEntityPerTest();

        UtenteDTO dtoAggiornato = new UtenteDTO(
                null,
                "Luigi",
                "Verdi",
                "luigi.verdi@gmail.com",
                "Via Milano 10 Milano"
        );

        when(utenteRepository.findById(1L)).thenReturn(Optional.of(utenteEntity));
        when(utenteRepository.save(any(UtenteEntity.class))).thenReturn(utenteEntity);

        utenteService.aggiornaUtente(1L, dtoAggiornato);

        assertEquals("Luigi", utenteEntity.getNome());
        assertEquals("Verdi", utenteEntity.getCognome());
        assertEquals("luigi.verdi@gmail.com", utenteEntity.getEmail());
        assertEquals("Via Milano 10 Milano", utenteEntity.getIndirizzo());

        verify(utenteRepository).save(utenteEntity);
    }

    @Test
    public void aggiornamentoUtenteNonTrrovato() {
        when(utenteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FcgNotFoundException.class, () -> utenteService.aggiornaUtente(1L, new UtenteDTO()));
    }

    @Test
    public void eliminaUtente() {
        when(utenteRepository.existsById(1L)).thenReturn(true);

        utenteService.eliminaUtente(1L);

        verify(utenteRepository).deleteById(1L);
    }

    @Test
    public void eliminaUtenteNonTrovato() {
        when(utenteRepository.existsById(99L)).thenReturn(false);

        assertThrows(FcgNotFoundException.class, () -> utenteService.eliminaUtente(99L));
    }

    private UtenteEntity creaUtenteEntityPerTest() {
        return new UtenteEntity(
                1L,
                "Mario",
                "Rossi",
                "mariorossi@gmail.com",
                "Via Roma 1 Roma"
        );
    }
}