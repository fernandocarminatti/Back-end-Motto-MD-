package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CnhImageExchangeDto(

        @JsonProperty("cnhImage")
        @NotNull(message = "CNH Image is required. Supported formats are jpg and bmp.")
        List<MultipartFile> cnhImage) {

        public CnhImageExchangeDto {
                if(cnhImage.size() != 1){
                        throw new IllegalArgumentException("Only one CNH Image is allowed.");
                }
        }

}
