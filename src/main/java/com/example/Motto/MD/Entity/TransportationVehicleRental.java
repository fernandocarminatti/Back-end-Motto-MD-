package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("TRANSPORTATION")
public class TransportationVehicleRental extends Rental {

    public TransportationVehicleRental(){}

    public TransportationVehicleRental(Vehicle vehicle, Renter renter) {
        super();
        setVehicle(vehicle);
        setRenter(renter);
        setCreatedAt(LocalDate.now());
    }

}
