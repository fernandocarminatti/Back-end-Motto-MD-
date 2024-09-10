package com.example.Motto.MD.Dto;

import com.example.Motto.MD.Entity.BikeTransportationVehicle;

import java.util.Map;

public record BikeResponseDto(long id, String manufactureYear, String model, String plateNumber, Map<String, Object> rentalData) {

    public static BikeResponseDto fromEntity(BikeTransportationVehicle bikeTransportationVehicle) {
        Map<String, Object> rentalData;
        if(bikeTransportationVehicle.isAvailable()){
            rentalData = Map.of("Situation: ", "Available");
        } else {
            rentalData = Map.of(
                    "Situation: ", "Unavailable",
                    "Renter ID: ", bikeTransportationVehicle.getRenter().getId(),
                    "Renter Name: ", bikeTransportationVehicle.getRenter().getName(),
                    "Renter Location: ", "/v1/renter/" + bikeTransportationVehicle.getRenter().getCnhNumber()
            );
        }
        return new BikeResponseDto(
                bikeTransportationVehicle.getId(),
                bikeTransportationVehicle.getManufactureYear(),
                bikeTransportationVehicle.getModel(),
                bikeTransportationVehicle.getPlateNumber(),
                rentalData
        );
    }

}
