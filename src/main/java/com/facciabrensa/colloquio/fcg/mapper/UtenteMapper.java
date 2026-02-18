package com.facciabrensa.colloquio.fcg.mapper;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {

    public UtenteEntity mapUtenteDtoToUtenteEntity(UtenteDTO utenteDTO) {
        return UtenteEntity.builder()
                .id(utenteDTO.getId())
                .nome(utenteDTO.getNome())
                .cognome(utenteDTO.getCognome())
                .email(utenteDTO.getEmail())
                .indirizzo(utenteDTO.getIndirizzo())
                .build();
    }

    public UtenteDTO mapUtenteEntityToUtenteDto(UtenteEntity utenteEntity) {
        return UtenteDTO.builder()
                .id(utenteEntity.getId())
                .nome(utenteEntity.getNome())
                .cognome(utenteEntity.getCognome())
                .email(utenteEntity.getEmail())
                .indirizzo(utenteEntity.getIndirizzo())
                .build();
    }
}
