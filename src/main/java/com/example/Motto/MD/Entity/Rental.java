package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tb_vehicle_rental")
public abstract class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Vehicle vehicle;
    @OneToOne
    private Renter renter;
    private LocalDate createdAt;

    public abstract void setRenter(Renter renter);

    public abstract<T extends Vehicle> void setVehicle(T vehicle);

    public abstract Renter getRenter();

    public abstract Vehicle getVehicle();
}
