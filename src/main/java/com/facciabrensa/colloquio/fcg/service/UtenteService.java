package com.facciabrensa.colloquio.fcg.service;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.exception.FcgNotFoundException;
import com.facciabrensa.colloquio.fcg.mapper.UtenteMapper;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private UtenteRepository utenteRepository;

    public UtenteDTO creazioneUtente(UtenteDTO utenteDTO) {
        UtenteEntity utenteEntity = utenteMapper.utenteDtoToUtenteEntity(utenteDTO);
        utenteEntity = utenteRepository.save(utenteEntity);

        return utenteMapper.utenteEntityToUtenteDto(utenteEntity);
    }

    public List<UtenteDTO> recuperoUtenti() {
        return utenteRepository.findAll()
                .stream()
                .map(utenteMapper::utenteEntityToUtenteDto)
                .toList();
    }

    public void aggiornaUtente(Long idUtente, UtenteDTO utenteDTO) {
        UtenteEntity utenteEntity = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new FcgNotFoundException("Utente non trovato."));

        utenteMapper.updateUtenteFromDto(utenteDTO, utenteEntity);
        utenteRepository.save(utenteEntity);
    }

    public void eliminaUtente(Long idUtente) {
        if (!utenteRepository.existsById(idUtente)) {
            throw new FcgNotFoundException("Utente non trovato.");
        }

        utenteRepository.deleteById(idUtente);
    }
}
