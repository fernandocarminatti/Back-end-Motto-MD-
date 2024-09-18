package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.Rental;
import com.example.Motto.MD.Entity.TransportationVehicleRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
