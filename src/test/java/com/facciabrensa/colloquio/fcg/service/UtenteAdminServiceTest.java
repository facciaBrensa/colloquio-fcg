package com.facciabrensa.colloquio.fcg.service;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.exception.FcgBadRequestException;
import com.facciabrensa.colloquio.fcg.parser.UtenteCsvParser;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UtenteAdminServiceTest {

    @Autowired
    private UtenteAdminService utenteAdminService;

    @MockitoBean
    private UtenteRepository utenteRepository;

    @MockitoBean
    private UtenteCsvParser utenteCsvParser;

    private UtenteEntity creaUtenteEntityPerTest() {
        return new UtenteEntity(
                1L,
                "Mario",
                "Rossi",
                "mariorossi@gmail.com",
                "Via Roma 1 Roma"
        );
    }

    @Test
    public void creazioneUtenteMassiva() {
        MultipartFile file = new MockMultipartFile(
                "file",
                "input.csv",
                "text/csv",
                "Mario,Rossi,mariorossi@gmail.com,Via Roma 1 Roma".getBytes()
        );

        UtenteDTO dto = new UtenteDTO();
        UtenteEntity entity = creaUtenteEntityPerTest();

        when(utenteCsvParser.parseUtenti(file)).thenReturn(List.of(dto));
        when(utenteRepository.saveAll(any())).thenReturn(List.of(entity));

        List<UtenteDTO> result = utenteAdminService.creazioneUtenteMassiva(file);

        assertEquals(1, result.size());
        assertEquals(entity.getNome(), result.get(0).getNome());

        verify(utenteCsvParser).parseUtenti(file);
        verify(utenteRepository).saveAll(any());
    }

    @Test
    public void creazioneUtenteMassivaFileVuoto() {
        MultipartFile file = new MockMultipartFile(
                "file",
                "utenti.csv",
                "text/csv",
                new byte[0]
        );

        assertThrows(FcgBadRequestException.class, () -> utenteAdminService.creazioneUtenteMassiva(file));
    }

    @Test
    public void ricercaUtenti() {
        UtenteEntity entity = creaUtenteEntityPerTest();

        when(utenteRepository.findByNomeAndCognome("Mario", "Rossi")).thenReturn(List.of(entity));

        List<UtenteDTO> result = utenteAdminService.ricercaUtenti("Mario", "Rossi");

        assertEquals(1, result.size());
        assertEquals("Mario", result.get(0).getNome());

        verify(utenteRepository).findByNomeAndCognome("Mario", "Rossi");
    }
}