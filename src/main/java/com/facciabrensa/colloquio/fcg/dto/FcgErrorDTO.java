package com.facciabrensa.colloquio.fcg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FcgErrorDTO {
    private int status;
    private String messaggio;
}
