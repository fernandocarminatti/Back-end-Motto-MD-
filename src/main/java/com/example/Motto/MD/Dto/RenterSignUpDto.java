package com.example.Motto.MD.Dto;

import com.example.Motto.MD.Entity.CnhType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record RenterSignUpDto(
        @JsonProperty("name")
        @NotBlank(message = "Person Name is required.")
        String name,
        @JsonProperty("cnpj")
        @NotBlank(message = "CNPJ is required.")
        @Size(min = 14, max = 18, message = "CNPJ must be between {min} and {max} characters. You could use XX.XXX.XXX/0000X-X format.")
        String cnpj,
        @JsonProperty("birthDate")
        @NotNull(message = "Birth Date is required. You could use YYYY-MM-DD format.")
        @Size(min = 10, max = 10, message = "Birth Date must be between {min} and {max} characters. You could user YYYY-MM-DD format.")
        String birthDate,
        @JsonProperty("cnhNumber")
        @NotBlank(message = "CNH Number is required.")
        @Size(min = 9, max = 9, message = "CNH Number must be between {min} and {max} characters.")
        String cnhNumber,
        @JsonProperty("cnhType")
        @NotNull(message = "CNH Type is required.")
        @Size(min = 1, max = 2, message = "CNH Type must be A, B or AB.")
        String cnhType,
        @JsonProperty("cnhImage")
        @NotNull(message = "CNH Image is required. Supported formats are jpg and bmp.")
        MultipartFile cnhImage) {
}
