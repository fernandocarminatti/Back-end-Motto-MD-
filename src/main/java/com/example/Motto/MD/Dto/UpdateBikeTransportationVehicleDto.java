package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateBikeTransportationVehicleDto(
        @JsonProperty("plateNumber")
        @NotNull(message = "Plate Number must not be Null.")
        @NotBlank(message = "Plate Number is required.")
        @Size(min = 8, max = 8, message = "Invalid plate number, Accepted formats are: ABC-1234")
        String plateNumber) {
}
