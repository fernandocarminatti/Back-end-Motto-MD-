package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Dto.UpdateBikeTransportationVehicle;
import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import com.example.Motto.MD.Exceptions.BikeTransportationVehicleException;
import com.example.Motto.MD.Repository.BikeTransportationVehicleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BikeTransportationVehicleService {

    BikeTransportationVehicleRepository bikeTransportationVehicleRepository;

    public BikeTransportationVehicleService(BikeTransportationVehicleRepository bikeTransportationVehicleRepository) {
        this.bikeTransportationVehicleRepository = bikeTransportationVehicleRepository;
    }

    public BikeTransportationVehicle createBikeTransportationVehicle(BikeTransportationVehicleDto bikeTransportationVehicleDto)  {
        try {
            BikeTransportationVehicle bikeTransportationVehicle = new BikeTransportationVehicle(
                    bikeTransportationVehicleDto.manufactureYear(),
                    bikeTransportationVehicleDto.model(),
                    bikeTransportationVehicleDto.plateNumber().toUpperCase());
            return bikeTransportationVehicleRepository.save(bikeTransportationVehicle);
        } catch (DataIntegrityViolationException e) {
            throw new BikeTransportationVehicleException("Error on Bike Transportation Vehicle SignUp, Bike might already exist. Check sent data and try again", bikeTransportationVehicleDto.plateNumber());
        }
    }

    public List<BikeTransportationVehicle> getAllBikeTransportationVehicles() {
        return bikeTransportationVehicleRepository.findAll();
    }

    public BikeTransportationVehicle getBikeTransportationVehicleByPlateNumber(String plateNumber) {
            return bikeTransportationVehicleRepository.findByPlateNumber(plateNumber);
    }

    public Optional<BikeTransportationVehicle> updateBikeTransportationVehicleByPlateNumber(String plateNumber, UpdateBikeTransportationVehicle updatedBikeTransportationVehicle) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        BikeTransportationVehicle bikeTransportationVehicle = optionalBikeTransportationVehicle.get();
        bikeTransportationVehicle.setPlateNumber(updatedBikeTransportationVehicle.plateNumber().toUpperCase());
        bikeTransportationVehicleRepository.save(bikeTransportationVehicle);
        return Optional.of(bikeTransportationVehicle);
    }

    public boolean deleteBikeTransportationVehicle(String plateNumber) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty() || !optionalBikeTransportationVehicle.get().isAvailable()){
            return false;
        }
        bikeTransportationVehicleRepository.delete(optionalBikeTransportationVehicle.get());
        return true;
    }
}
