package com.example.Motto.MD.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_renter")
public class Renter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cnpj;
    private LocalDate birthDate;
    private String cnhNumber;
    private String cnhType;
    private String cnhImage;

    @OneToOne
    private BikeTransportationVehicle bikeTransportationVehicle;

    public Renter(){};

    public Renter(String name, String cnpj, LocalDate birthDate, String cnhNumber, String cnhType, String cnhImage) {
        this.name = name;
        this.cnpj = cnpj;
        this.birthDate = birthDate;
        this.cnhNumber = cnhNumber;
        this.cnhType = cnhType;
        this.cnhImage = cnhImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCnhNumber() {
        return cnhNumber;
    }

    public void setCnhNumber(String cnhNumber) {
        this.cnhNumber = cnhNumber;
    }

    public String getCnhType() {
        return cnhType;
    }

    public void setCnhType(String cnhType) {
        this.cnhType = cnhType;
    }

    public String getCnhImage() {
        return cnhImage;
    }

    public void setCnhImage(String cnhImage) {
        this.cnhImage = cnhImage;
    }

    public BikeTransportationVehicle getBikeTransportationVehicle() {
        return bikeTransportationVehicle;
    }

    public void setBikeTransportationVehicle(BikeTransportationVehicle bikeTransportationVehicle) {
        this.bikeTransportationVehicle = bikeTransportationVehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Renter renter)) return false;
        return Objects.equals(id, renter.id) && Objects.equals(name, renter.name) && Objects.equals(cnpj, renter.cnpj) && Objects.equals(birthDate, renter.birthDate) && Objects.equals(cnhType, renter.cnhType) && Objects.equals(cnhImage, renter.cnhImage) && Objects.equals(bikeTransportationVehicle, renter.bikeTransportationVehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnpj, birthDate);
    }
}
