package com.example.Motto.MD.Entity;

import com.example.Motto.MD.Dto.VehicleSignUpDto;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class VehicleFactory {

    public static Vehicle createVehicle(VehicleSignUpDto vehicleSignUpDto) {
        switch (vehicleSignUpDto.vehicleType()) {
            case 1:
                return new BikeVehicle(
                        vehicleSignUpDto.manufactureYear(),
                        vehicleSignUpDto.model(),
                        vehicleSignUpDto.plateNumber().toUpperCase()
                );
            case 3:
                return new TransportationVehicle(
                        vehicleSignUpDto.manufactureYear(),
                        vehicleSignUpDto.model(),
                        vehicleSignUpDto.plateNumber().toUpperCase()
                );
            default:
                throw new HttpMessageNotReadableException("Invalid vehicle type");
        }
    }
}
