package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BikeTransportationVehicleDto(
        @JsonProperty("manufactureYear")
        String manufactureYear,
        @JsonProperty("model")
        String model,
        @JsonProperty("plateNumber")
        String plateNumber) {
}
