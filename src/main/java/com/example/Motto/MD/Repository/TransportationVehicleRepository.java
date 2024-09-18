package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.TransportationVehicle;
import com.example.Motto.MD.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationVehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByPlateNumber(String plateNumber);

    boolean deleteByPlateNumber(String plateNumber);

    boolean existsByPlateNumber(String cnhNumber);
}




