package com.example.Motto.MD.Dto;


import com.example.Motto.MD.Entity.Renter;

import java.util.Map;

public record RenterResponseDto(long id, String name, String CnhNumber, String CnhImage) {

    public static RenterResponseDto fromEntity(Renter renter) {
        return new RenterResponseDto(
                renter.getId(),
                renter.getName(),
                renter.getCnhNumber(),
                renter.getCnhImage()
        );
    }
}
