package com.example.Motto.MD.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("BIKE")
public class BikeVehicleRental extends Rental {

    public BikeVehicleRental(){}

    public BikeVehicleRental(Vehicle vehicle, Renter renter, String rentalPeriod) {
        super();
        setVehicle(vehicle);
        setRenter(renter);
        setCreatedAt(LocalDate.now());
        setStartDate(getCreatedAt().plusDays(1));
        setDueDate(getStartDate().plusDays(Integer.parseInt(rentalPeriod)));
    }

}
