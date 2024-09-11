package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_bike_transportation_vehicle")
public class BikeTransportationVehicle implements TransportationVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manufacture_year")
    private String manufactureYear;
    private String model;

    @Column(nullable = false, unique = true, length = 8)
    private String plateNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    Renter renter;

    public BikeTransportationVehicle(){};

    public BikeTransportationVehicle(String year, String model, String plateNumber) {
        this.manufactureYear = year;
        this.model = model;
        this.plateNumber = plateNumber;
    }

    public Long getId() {
        return id;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
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
                "year='" + manufactureYear + '\'' +
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

    public boolean isAvailable(){
        return renter == null;
    }
}
