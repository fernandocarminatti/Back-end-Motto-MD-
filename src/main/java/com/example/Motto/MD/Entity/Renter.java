package com.example.Motto.MD.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name="cnh_number", unique = true, length = 9)
    private String cnhNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "cnh_type")
    private CnhType cnhType;
    @Column(name = "cnh_image")
    private String cnhImage;

    public Renter(){};

    public Renter(String name, LocalDate birthDate, String cnhNumber, CnhType cnhType, String cnhImage) {
        this.name = name;
        this.birthDate = birthDate;
        this.cnhNumber = cnhNumber;
        this.cnhType = cnhType;
        this.cnhImage = cnhImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CnhType getCnhType() {
        return cnhType;
    }

    public void setCnhType(CnhType cnhType) {
        this.cnhType = cnhType;
    }

    public String getCnhImage() {
        return cnhImage;
    }

    public void setCnhImage(String cnhImage) {
        this.cnhImage = cnhImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Renter renter)) return false;
        return Objects.equals(id, renter.id) && Objects.equals(name, renter.name) && Objects.equals(birthDate, renter.birthDate) && Objects.equals(cnhNumber, renter.cnhNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, cnhNumber);
    }
}
