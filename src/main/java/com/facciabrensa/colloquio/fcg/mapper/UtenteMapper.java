package com.facciabrensa.colloquio.fcg.mapper;

import com.facciabrensa.colloquio.fcg.dto.UtenteDTO;
import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    UtenteEntity utenteDtoToUtenteEntity(UtenteDTO utenteDTO);
    UtenteDTO utenteEntityToUtenteDto(UtenteEntity utenteEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUtenteFromDto(
            UtenteDTO dto,
            @MappingTarget UtenteEntity entity
    );
}
