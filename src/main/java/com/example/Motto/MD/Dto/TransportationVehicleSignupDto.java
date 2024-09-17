package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransportationVehicleSignupDto(
        @NotBlank(message = "Manufacturer Year is required.")
        @NotNull(message = "Manufacturer Year must not be Null.")
        @Size(min = 4, max = 4, message = "Invalid manufacture year, keep it between {min} and {max} characters")
        @JsonProperty("manufactureYear")
        String manufactureYear,
        @NotBlank(message = "Model Name is required.")
        @NotNull(message = "Model must not be Null.")
        @JsonProperty("model")
        String model,
        @NotBlank(message = "Plate Number is required.")
        @NotNull(message = "Bike Plate Number must not be Null.")
        @Size(min = 8, max = 8, message = "Invalid plate number, Accepted formats are: ABC-1234")
        @JsonProperty("plateNumber")
        String plateNumber,
        @NotBlank(message = "Vehicle Type is required.")
        @NotNull(message = "Vehicle Type must not be Null.")
        @Size(min = 4, max = 10, message = "Invalid vehicle type, keep it between {min} and {max} characters")
        @JsonProperty("vehicleType")
        String vehicleType
        ) {
}
