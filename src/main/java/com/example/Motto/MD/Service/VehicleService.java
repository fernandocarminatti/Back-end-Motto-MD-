package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.UpdateVehicleDto;
import com.example.Motto.MD.Dto.VehicleResponseDto;
import com.example.Motto.MD.Dto.CreateVehicleDto;
import com.example.Motto.MD.Entity.Vehicle;
import com.example.Motto.MD.Entity.VehicleFactory;
import com.example.Motto.MD.Repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Optional<Vehicle> createVehicle(CreateVehicleDto createVehicleDto)  {
        if (vehicleRepository.existsByPlateNumber(createVehicleDto.plateNumber().toUpperCase())) {
            return Optional.empty();
        }
        Vehicle customVehicle = VehicleFactory.createVehicle(createVehicleDto);
        vehicleRepository.save(customVehicle);
        return Optional.of(customVehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleByPlateNumber(String plateNumber) {
        return Optional.ofNullable(vehicleRepository.findByPlateNumber(plateNumber));
    }

    public Optional<VehicleResponseDto> updateVehicleByPlateNumber(String plateNumber, UpdateVehicleDto updatedBikeTransportationVehicle) {
        Optional<Vehicle> optionalVehicle = Optional.ofNullable(vehicleRepository.findByPlateNumber(plateNumber));
        if(optionalVehicle.isEmpty()){
            return Optional.empty();
        }
        optionalVehicle.get().setPlateNumber(updatedBikeTransportationVehicle.plateNumber().toUpperCase());
        vehicleRepository.save(optionalVehicle.get());

        return Optional.of(VehicleResponseDto.fromEntity(optionalVehicle.get()));
    }

    public boolean deleteVehicle(String plateNumber) {
        Optional<Vehicle> optionalVehicle = Optional.ofNullable(vehicleRepository.findByPlateNumber(plateNumber));
        if(optionalVehicle.isEmpty() || !optionalVehicle.get().isAvailable()){
            return false;
        }
        vehicleRepository.delete(optionalVehicle.get());
        return true;
    }

    public boolean updateVehicleStatus(Vehicle vehicle){
        vehicleRepository.save(vehicle);
        return true;
    }
}
