package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeTransportationVehicleRepository extends JpaRepository<BikeTransportationVehicle, Long> {
    BikeTransportationVehicle findByPlateNumber(String plateNumber);

    boolean deleteByPlateNumber(String plateNumber);

}


