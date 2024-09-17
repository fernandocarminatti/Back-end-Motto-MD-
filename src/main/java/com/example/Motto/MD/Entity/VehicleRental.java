package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class VehicleRental extends Rental {

    private Long id;

    @OneToOne
    Vehicle vehicle;
    @OneToOne
    Renter renter;

    LocalDate createdAt;

    public VehicleRental(){}

    public VehicleRental(Vehicle vehicle, Renter renter) {
        this.vehicle = vehicle;
        this.renter = renter;
        this.createdAt = LocalDate.now();
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    @Override
    public <T extends Vehicle> void setVehicle(T vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public Renter getRenter() {
        return renter;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "BikeRental{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", renter=" + renter +
                ", createdAt=" + createdAt +
                '}';
    }

}
