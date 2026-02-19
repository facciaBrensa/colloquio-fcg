package com.facciabrensa.colloquio.fcg.controller;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.service.UtenteAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Admin utenti", description = "Operazioni da amministratore sugli utenti")
@RestController
@RequestMapping("/admin/utenti")
public class UtenteAdminController {

    @Autowired
    private UtenteAdminService utenteAdminService;

    @Operation(summary = "Creazione utenti massiva tramite file csv")
    @PostMapping(
            value = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<UtenteDTO> creazioneUtenteMassiva(@RequestParam("file") MultipartFile file) {

        return utenteAdminService.creazioneUtenteMassiva(file);
    }

    @Operation(summary = "Ricerca utenti per nome e/o cognome")
    @GetMapping
    public List<UtenteDTO> ricercaUtenti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome
    ) {

        return utenteAdminService.ricercaUtenti(nome, cognome);
    }

}
