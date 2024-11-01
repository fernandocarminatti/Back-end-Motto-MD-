package com.example.Motto.MD.Dto;


import com.example.Motto.MD.Entity.CnhType;
import com.example.Motto.MD.Entity.Renter;

import java.util.Map;

public record RenterResponseDto(long id, String name, String birthDate, String cnhNumber, CnhType cnhType, String cnhImage) {

    public static RenterResponseDto fromEntity(Renter renter) {
        return new RenterResponseDto(
                renter.getId(),
                renter.getName(),
                renter.getBirthDate().toString(),
                renter.getCnhNumber(),
                renter.getCnhType(),
                renter.getCnhImage()
        );
    }
}
