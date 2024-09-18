package com.example.Motto.MD.Dto;

import com.example.Motto.MD.Entity.Vehicle;

public record VehicleResponseDto(long id, String manufactureYear, String model, String plateNumber, boolean available, String vehicleType) {

    public static <T extends Vehicle> VehicleResponseDto fromEntity(T vehicle) {
        return new VehicleResponseDto(
                vehicle.getId(),
                vehicle.getManufactureYear(),
                vehicle.getModel(),
                vehicle.getPlateNumber(),
                vehicle.isAvailable(),
                vehicle.getVehicleType()
        );
    }

}
