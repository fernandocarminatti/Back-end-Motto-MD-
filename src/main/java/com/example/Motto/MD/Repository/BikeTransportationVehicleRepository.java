package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeTransportationVehicleRepository extends JpaRepository<BikeTransportationVehicle, Long> {
    BikeTransportationVehicle findByPlateNumber(String plateNumber);

    boolean deleteByPlateNumber(String plateNumber);

    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(tv.id) > 0 THEN true ELSE false END FROM tb_bike_transportation_vehicle tv WHERE tv.plateNumber = ?1")
    boolean checkIfBikeTransportationVehicleExistsByPlateNumber(String cnhNumber);
}




