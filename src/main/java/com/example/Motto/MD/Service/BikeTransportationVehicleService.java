package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import com.example.Motto.MD.Repository.BikeTransportationVehicleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class BikeTransportationVehicleService {

    BikeTransportationVehicleRepository bikeTransportationVehicleRepository;

    public BikeTransportationVehicleService(BikeTransportationVehicleRepository bikeTransportationVehicleRepository) {
        this.bikeTransportationVehicleRepository = bikeTransportationVehicleRepository;
    }

    public void createBikeTransportationVehicle(BikeTransportationVehicleDto bikeTransportationVehicleDto) throws DataIntegrityViolationException {
        BikeTransportationVehicle bikeTransportationVehicle = new BikeTransportationVehicle(
                bikeTransportationVehicleDto.year(),
                bikeTransportationVehicleDto.model(),
                bikeTransportationVehicleDto.plateNumber());
        bikeTransportationVehicleRepository.save(bikeTransportationVehicle);

    }
}
