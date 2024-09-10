package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record BikeVehicleSignUpDto(
        @JsonProperty("manufactureYear")
        @Size(min = 4, max = 4, message = "Invalid manufacture year, keep it between 4 and 4 digits")
        String manufactureYear,
        @JsonProperty("model")
        String model,
        @JsonProperty("plateNumber")
        @Size(min = 7, max = 7, message = "Invalid plate number, keep it between 7 and 7 digits. ABC-1234")
        String plateNumber) {
}
