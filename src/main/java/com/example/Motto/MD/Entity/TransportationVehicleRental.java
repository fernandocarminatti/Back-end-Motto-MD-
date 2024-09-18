package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("TRANSPORTATION")
public class TransportationVehicleRental extends Rental {

    public TransportationVehicleRental(){}

    public TransportationVehicleRental(Vehicle vehicle, Renter renter, String rentalPeriod) {
        super();
        setVehicle(vehicle);
        setRenter(renter);
        setCreatedAt(LocalDate.now());
        setStartDate(getCreatedAt().plusDays(1));
        setDueDate(getStartDate().plusDays(Integer.parseInt(rentalPeriod)));
    }

}
