package com.example.Motto.MD.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RentalFactory {

    public static final List<String> ACCEPT_BIKE_RENTAL = Collections.singletonList("A");
    public static final List<String> ACCEPT_CAR_RENTAL = Arrays.asList("B", "AB", "C", "D");
    public static final List<String> ACCEPT_TRANSPORTATION_RENTAL = Arrays.asList("C", "D");

    public static Rental createRental(Vehicle vehicle, Renter renter, String rentalPeriod) {
        switch (vehicle.getVehicleType()) {
            case "BikeVehicle":
                if(ACCEPT_BIKE_RENTAL.contains(renter.getCnhType().toString())){
                    return new BikeVehicleRental(vehicle, renter, rentalPeriod);
                }
            throw new IllegalArgumentException("Invalid CNH Type for this Rental. Renter should have A license.");
            case "CarVehicle":
                if(ACCEPT_CAR_RENTAL.contains(renter.getCnhType().toString())){
                    return new CarVehicleRental(vehicle, renter, rentalPeriod);
                }
            throw new IllegalArgumentException("Invalid CNH Type for this Rental. Renter should have B or AB license.");
            case "TransportationVehicle":
                if(ACCEPT_TRANSPORTATION_RENTAL.contains(renter.getCnhType().toString())){
                    return new TransportationVehicleRental(vehicle, renter, rentalPeriod);
                }
            throw new IllegalArgumentException("Invalid CNH Type for this Rental. Renter should have C or D license.");
            default:
                throw new IllegalStateException("Invalid CNH Type for this Rental, or Invalid Vehicle Type.");
        }
    }
}
