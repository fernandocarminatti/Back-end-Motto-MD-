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
import java.util.stream.Collectors;

@Service
public class VehicleService {

    VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Optional<VehicleResponseDto> createTransportationVehicle(CreateVehicleDto createVehicleDto)  {
        if (vehicleRepository.existsByPlateNumber(createVehicleDto.plateNumber().toUpperCase())) {
            return Optional.empty();
        }
        Vehicle customVehicle = VehicleFactory.createVehicle(createVehicleDto);
        vehicleRepository.save(customVehicle);
        return Optional.of(VehicleResponseDto.fromEntity(customVehicle));
    }

    public List<VehicleResponseDto> getAllTransportationVehicles() {
        List<? extends Vehicle> allTransportationVehicles = vehicleRepository.findAll();
        return allTransportationVehicles.stream().map(VehicleResponseDto::fromEntity).collect(Collectors.toList());
    }

    public Optional<Vehicle> getVehicleByPlateNumber(String plateNumber) {
        return Optional.ofNullable(vehicleRepository.findByPlateNumber(plateNumber));
    }

    public Optional<?> updateTransportationVehicleByPlateNumber(String plateNumber, UpdateVehicleDto updatedBikeTransportationVehicle) {
        Optional<Vehicle> optionalTransportationVehicle = Optional.ofNullable(vehicleRepository.findByPlateNumber(plateNumber));
        if(optionalTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        optionalTransportationVehicle.get().setPlateNumber(updatedBikeTransportationVehicle.plateNumber().toUpperCase());
        vehicleRepository.save(optionalTransportationVehicle.get());

        return Optional.of(VehicleResponseDto.fromEntity(optionalTransportationVehicle.get()));
    }

    public boolean deleteTransportationVehicle(String plateNumber) {
        Optional<Vehicle> optionalBikeTransportationVehicle = Optional.ofNullable(vehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty() || !optionalBikeTransportationVehicle.get().isAvailable()){
            return false;
        }
        vehicleRepository.delete(optionalBikeTransportationVehicle.get());
        return true;
    }
}
