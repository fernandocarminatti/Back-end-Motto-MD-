package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BikeTransportationVehicleDto(
        @JsonProperty("year")
        String year,
        @JsonProperty("model")
        String model,
        @JsonProperty("plateNumber")
        String plateNumber) {
}
