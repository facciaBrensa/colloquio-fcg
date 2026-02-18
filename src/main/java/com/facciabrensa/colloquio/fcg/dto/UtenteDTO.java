package com.facciabrensa.colloquio.fcg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String indirizzo;
}