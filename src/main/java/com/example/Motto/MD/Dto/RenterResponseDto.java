package com.example.Motto.MD.Dto;


import com.example.Motto.MD.Entity.Renter;

import java.util.Map;

public record RenterResponseDto(long id, String name, String CnhNumber, String CnhImage, Map<String, Object> bikeData) {

    public static RenterResponseDto fromEntity(Renter renter) {
        Map<String, Object> bikeData;
        if(renter.getBikeTransportationVehicle() == null){
            bikeData = Map.of("Rentals: ", "None");
        } else {
            bikeData = Map.of(
                    "Rentals: ", renter.getBikeTransportationVehicle().getId(),
                    "Rental Location: ", "/v1/bike/" + renter.getBikeTransportationVehicle().getId()
            );
        }
        return new RenterResponseDto(
                renter.getId(),
                renter.getName(),
                renter.getCnhNumber(),
                renter.getCnhImage(),
                bikeData
        );
    }
}
