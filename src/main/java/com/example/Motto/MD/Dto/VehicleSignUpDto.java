package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

public record VehicleSignUpDto(
        @Range(min = 1, max = 3, message = "Invalid vehicle type. Accepted values are: 1 - BIKE, 2 - CAR, 3 - TRANSPORTATION")
        @JsonProperty("vehicleType")
        int vehicleType,
        @NotBlank(message = "Manufacturer Year is required.")
        @NotNull(message = "Manufacturer Year must not be Null.")
        @Size(min = 4, max = 4, message = "Invalid manufacture year, keep it between {min} and {max} characters")
        @JsonProperty("manufactureYear")
        String manufactureYear,
        @NotBlank(message = "Model Name is required.")
        @NotNull(message = "Model must not be Null.")
        @JsonProperty("model")
        String model,
        @NotBlank(message = "Plate Number is required. Accepted formats are: ABC1D23")
        @NotNull(message = "Plate Number must not be Null. Accepted formats are: ABC1D23")
        @Size(min = 7, max = 7, message = "Invalid plate number size, Accepted formats are: ABC1D23")
        @JsonProperty("plateNumber")
        String plateNumber
        ) {
}
