package com.example.Motto.MD.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("BIKE")
public class BikeRental extends Rental {

    public BikeRental(){}

    public BikeRental(Vehicle vehicle, Renter renter) {
        super();
        setVehicle(vehicle);
        setRenter(renter);
        setCreatedAt(LocalDate.now());
    }

}
