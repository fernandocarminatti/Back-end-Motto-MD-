package com.example.Motto.MD.Dto;

import com.example.Motto.MD.Entity.CnhType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RenterSignUpDto(
        @JsonProperty("name")
        String name,
        @JsonProperty("cnpj")
        String cnpj,
        @JsonProperty("birthDate")
        LocalDate birthDate,
        @JsonProperty("cnhNumber")
        String cnhNumber,
        @JsonProperty("cnhType")
        CnhType cnhType,
        @JsonProperty("cnhImage")
        String cnhImage) {
}
