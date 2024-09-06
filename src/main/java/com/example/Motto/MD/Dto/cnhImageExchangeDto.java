package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record cnhImageExchangeDto(
        @JsonProperty("cnhNumber")
        @NotBlank(message = "CNH Number is required.")
        @Size(min = 9, max = 9, message = "CNH Number must be between {min} and {max} characters.")
        String cnhNumber,
        @JsonProperty("cnhImage")
        @NotNull(message = "CNH Image is required. Supported formats are jpg and bmp.")
        List<MultipartFile> cnhImage) {

        public cnhImageExchangeDto {
                if(cnhImage.size() != 1){
                        throw new IllegalArgumentException("Only one CNH Image is allowed.");
                }
        }

}
