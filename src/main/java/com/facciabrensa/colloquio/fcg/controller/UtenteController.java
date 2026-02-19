package com.facciabrensa.colloquio.fcg.controller;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.service.UtenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Utenti", description = "Operazioni CRUD sugli utenti")
@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Operation(summary = "Creazione nuovo utente")
    @PostMapping
    public UtenteDTO creazioneUtente(@RequestBody UtenteDTO utenteDTO) {

        return utenteService.creazioneUtente(utenteDTO);
    }

    @Operation(summary = "Recupero completo utenti")
    @GetMapping
    public List<UtenteDTO> recuperoUtenti() {

        return utenteService.recuperoUtenti();
    }

    @Operation(summary = "Aggiornamento utente")
    @PutMapping("/{idUtente}")
    public void aggiornaUtente(
            @PathVariable Long idUtente,
            @RequestBody UtenteDTO utenteDTO
    ) {

        utenteService.aggiornaUtente(idUtente, utenteDTO);
    }

    @Operation(summary = "Eliminazione utente")
    @DeleteMapping("/{idUtente}")
    public void eliminaUtente(@PathVariable Long idUtente) {

        utenteService.eliminaUtente(idUtente);
    }
}
