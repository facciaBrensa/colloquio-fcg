package com.facciabrensa.colloquio.fcg.service;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.exception.FcgBadRequestException;
import com.facciabrensa.colloquio.fcg.mapper.UtenteMapper;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import com.facciabrensa.colloquio.fcg.parser.UtenteCsvParser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UtenteAdminService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private UtenteCsvParser utenteCsvParser;

    @Transactional
    public List<UtenteDTO> creazioneUtenteMassiva(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FcgBadRequestException("Il file non può essere vuoto.");
        }

        List<UtenteDTO> listaUtentiDto = utenteCsvParser.parseUtenti(file);

        List<UtenteEntity> listaUtenti = listaUtentiDto
                .stream()
                .map(utenteMapper::utenteDtoToUtenteEntity)
                .toList();

        listaUtenti = utenteRepository.saveAll(listaUtenti);

        return listaUtenti
                .stream()
                .map(utenteMapper::utenteEntityToUtenteDto)
                .toList();
    }

    public List<UtenteDTO> ricercaUtenti(String nome, String cognome) {
        return utenteRepository.findByNomeAndCognome(nome, cognome)
                .stream()
                .map(utenteMapper::utenteEntityToUtenteDto)
                .toList();
    }
}
