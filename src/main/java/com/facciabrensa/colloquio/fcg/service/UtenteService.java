package com.facciabrensa.colloquio.fcg.service;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import com.facciabrensa.colloquio.fcg.mapper.UtenteMapper;
import com.facciabrensa.colloquio.fcg.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteService {

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private UtenteRepository utenteRepository;

    public UtenteDTO creazioneUtente(UtenteDTO utenteDTO) {
        UtenteEntity utenteEntity = utenteMapper.mapUtenteDtoToUtenteEntity(utenteDTO);
        utenteEntity = utenteRepository.save(utenteEntity);

        return utenteMapper.mapUtenteEntityToUtenteDto(utenteEntity);
    }

    public List<UtenteDTO> recuperoUtenti() {
        return utenteRepository.findAll()
                .stream()
                .map(utenteMapper::mapUtenteEntityToUtenteDto)
                .collect(Collectors.toList());
    }
}
