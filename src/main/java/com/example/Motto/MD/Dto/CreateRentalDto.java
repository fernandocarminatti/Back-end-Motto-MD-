package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

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
        String renterCnhNumber,
        @NotBlank(message = "Rental Period is required.")
        @NotNull(message = "Rental Period must not be Null.")
        @Pattern(regexp = "^(7|15|30|45|50)$", message = "Rental Period must be 7, 15, 30, 45 or 50 days")
        @JsonProperty("rentalPeriod")
        String rentalPeriod
        ) { }
