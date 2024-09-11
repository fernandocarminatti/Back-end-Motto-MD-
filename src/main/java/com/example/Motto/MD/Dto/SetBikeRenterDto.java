package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = false)
public record SetBikeRenterDto(
        @JsonProperty("renterCnhNumber")
        @NotBlank(message = "Renter CNH Number is required.")
        @Size(min = 9, max = 9, message = "Renter CNH Number must be between {min} and {max} characters.")
        String renterCnhNumber) {
}
