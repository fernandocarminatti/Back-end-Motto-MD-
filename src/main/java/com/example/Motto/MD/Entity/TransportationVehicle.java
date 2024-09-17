package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TRANSPORTATION")
public class TransportationVehicle extends Vehicle {

    @Column(name = "is_available", nullable = false)
    boolean isAvailable;

    public TransportationVehicle(){};

    public TransportationVehicle(String year, String model, String plateNumber) {
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
