package com.example.Motto.MD.Entity;

import com.example.Motto.MD.Dto.CreateVehicleDto;

public class VehicleFactory {

    public static Vehicle createVehicle(CreateVehicleDto createVehicleDto) {
        switch (createVehicleDto.vehicleType()) {
            case 1:
                return new BikeVehicle(
                        createVehicleDto.manufactureYear(),
                        createVehicleDto.model(),
                        createVehicleDto.plateNumber().toUpperCase()
                );
            case 3:
                return new TransportationVehicle(
                        createVehicleDto.manufactureYear(),
                        createVehicleDto.model(),
                        createVehicleDto.plateNumber().toUpperCase()
                );
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }
}
