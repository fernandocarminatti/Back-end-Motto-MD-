package com.example.Motto.MD.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BIKE")
public class BikeVehicle extends Vehicle {

    @Column(name = "is_available", nullable = false)
    boolean isAvailable;

    public BikeVehicle(){};

    public BikeVehicle(String year, String model, String plateNumber) {
        super();
        setManufactureYear(year);
        setModel(model);
        setPlateNumber(plateNumber);
        this.isAvailable = true;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }
    @Override
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

}