package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_bike_transportation_vehicle")
public class BikeTransportationVehicle implements TransportationVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private String model;

    @Column(unique = true)
    private String plateNumber;

    @OneToOne
    Renter renter;

    public BikeTransportationVehicle(){};

    public BikeTransportationVehicle(String year, String model, String plateNumber) {
        this.year = year;
        this.model = model;
        this.plateNumber = plateNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public String toString() {
        return "BikeTransportationVehicle{" +
                "year='" + year + '\'' +
                ", model='" + model + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                '}';
    }

    @Override
    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    @Override
    public Renter getRenter() {
        return renter;
    }
}
