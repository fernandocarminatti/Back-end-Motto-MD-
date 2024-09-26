package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CnhImageExchangeDto(
        @JsonProperty("cnhImage")
        List<MultipartFile> cnhImage) {
        public CnhImageExchangeDto {
                if(cnhImage == null){
                        throw new IllegalArgumentException("CNH Image is required.");
                }
                if(cnhImage.size() != 1){
                        throw new IllegalArgumentException("Only one CNH Image is allowed.");
                }
        }
}
