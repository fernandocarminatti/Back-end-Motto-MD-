package com.example.Motto.MD.Entity;

public class RentalFactory {

    public static Rental createRental(Vehicle vehicle, Renter renter, String rentalPeriod) {
        return switch (vehicle.getVehicleType()) {
            case "BikeVehicle" -> new BikeRental(vehicle, renter, rentalPeriod);
            case "TransportationVehicle" -> new TransportationVehicleRental(vehicle, renter, rentalPeriod);
            default -> throw new IllegalArgumentException("Error: Could not define Vehicle Type");
        };
    }
}
