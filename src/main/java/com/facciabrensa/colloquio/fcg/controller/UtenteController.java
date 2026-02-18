package com.facciabrensa.colloquio.fcg.controller;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping
    public UtenteDTO creazioneUtente(@RequestBody UtenteDTO utenteDTO) {

        return utenteService.creazioneUtente(utenteDTO);
    }

    @GetMapping
    public List<UtenteDTO> recuperoUtenti() {

        return utenteService.recuperoUtenti();
    }
}
