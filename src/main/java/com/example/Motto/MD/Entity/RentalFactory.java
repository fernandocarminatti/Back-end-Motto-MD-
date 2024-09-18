package com.example.Motto.MD.Entity;

public class RentalFactory {

    public static Rental createRental(Vehicle vehicle, Renter renter) {
        return switch (vehicle.getVehicleType()) {
            case "BikeVehicle" -> new BikeRental(vehicle, renter);
            case "TransportationVehicle" -> new TransportationVehicleRental(vehicle, renter);
            default -> throw new IllegalArgumentException("Error: Could not define Vehicle Type");
        };
    }
}
