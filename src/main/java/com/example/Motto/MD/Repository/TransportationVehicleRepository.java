package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.TransportationVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationVehicleRepository extends JpaRepository<TransportationVehicle, Long> {
    TransportationVehicle findByPlateNumber(String plateNumber);

    boolean deleteByPlateNumber(String plateNumber);

    boolean existsByPlateNumber(String cnhNumber);
}




