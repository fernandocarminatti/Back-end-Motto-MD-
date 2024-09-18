package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record RenterSignUpDto(
        @JsonProperty("name")
        @NotBlank(message = "Person Name is required.")
        String name,
        @JsonProperty("birthDate")
        @NotNull(message = "Birth Date is required. You could use YYYY-MM-DD format.")
        @Size(min = 10, max = 10, message = "Birth Date must be between {min} and {max} characters. You should use YYYY-MM-DD format.")
        String birthDate,
        @JsonProperty("cnhNumber")
        @NotBlank(message = "CNH Number is required.")
        @Size(min = 9, max = 9, message = "CNH Number must be between {min} and {max} characters.")
        String cnhNumber,
        @JsonProperty("cnhType")
        @NotNull(message = "CNH Type is required.")
        @Pattern(regexp = "^(A|B|AB|C|D)$", message = "CNH Type must be A, B, AB, C or D")
        String cnhType,
        @JsonProperty("cnhImage")
        @NotNull(message = "CNH Image is required. Supported formats are jpg and bmp.")
        MultipartFile cnhImage) {
}
