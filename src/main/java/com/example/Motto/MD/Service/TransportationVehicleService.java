package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.*;
import com.example.Motto.MD.Entity.TransportationVehicle;
import com.example.Motto.MD.Entity.Vehicle;
import com.example.Motto.MD.Repository.TransportationVehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransportationVehicleService {

    TransportationVehicleRepository transportationVehicleRepository;
    RenterService renterService;

    public TransportationVehicleService(TransportationVehicleRepository transportationVehicleRepository, RenterService renterService) {
        this.transportationVehicleRepository = transportationVehicleRepository;
        this.renterService = renterService;
    }

    @Transactional
    public Optional<VehicleResponseDto> createTransportationVehicle(TransportationVehicleSignupDto transportationVehicleSignupDto)  {
        boolean vehicleChecker = transportationVehicleRepository.existsByPlateNumber(transportationVehicleSignupDto.plateNumber());
        if(vehicleChecker){
            return Optional.empty();
        }
        TransportationVehicle transportationVehicle = new TransportationVehicle(
                transportationVehicleSignupDto.manufactureYear(),
                transportationVehicleSignupDto.model(),
                transportationVehicleSignupDto.plateNumber().toUpperCase());
        transportationVehicleRepository.save(transportationVehicle);
        return Optional.of(VehicleResponseDto.fromEntity(transportationVehicle));
    }

    public List<VehicleResponseDto> getAllTransportationVehicles() {
        List<? extends Vehicle> allTransportationVehicles = transportationVehicleRepository.findAll();
        return allTransportationVehicles.stream().map(VehicleResponseDto::fromEntity).collect(Collectors.toList());
    }

    public Optional<?> getTransportationVehicleByPlateNumber(String plateNumber) {
        Optional<? extends Vehicle> optionalTransportationVehicle = Optional.ofNullable(transportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(VehicleResponseDto.fromEntity(optionalTransportationVehicle.get()));
    }

    public Optional<?> updateTransportationVehicleByPlateNumber(String plateNumber, UpdateTransportationVehicleDto updatedBikeTransportationVehicle) {
        Optional<TransportationVehicle> optionalTransportationVehicle = Optional.ofNullable(transportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        optionalTransportationVehicle.get().setPlateNumber(updatedBikeTransportationVehicle.plateNumber().toUpperCase());
        transportationVehicleRepository.save(optionalTransportationVehicle.get());

        return Optional.of(VehicleResponseDto.fromEntity(optionalTransportationVehicle.get()));
    }

    public boolean deleteTransportationVehicle(String plateNumber) {
        Optional<TransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(transportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty() || !optionalBikeTransportationVehicle.get().isAvailable()){
            return false;
        }
        transportationVehicleRepository.delete(optionalBikeTransportationVehicle.get());
        return true;
    }
}
