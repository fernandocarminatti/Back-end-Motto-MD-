package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRentalDto(
        @NotBlank(message = "Vehicle Plate Number is required.")
        @NotNull(message = "Vehicle Plate Number must not be Null.")
        @Size(min = 7, max = 7, message = "Vehicle Plate Number must be between {min} and {max} characters.")
        @JsonProperty("plateNumber")
        String vehiclePlateNumber,
        @NotBlank(message = "Renter CNH Number is required.")
        @NotNull(message = "Renter CNH Number must not be Null.")
        @Size(min = 9, max = 9, message = "Renter CNH Number must be between {min} and {max} characters.")
        @JsonProperty("cnhNumber")
        String renterCnhNumber
        ) { }
