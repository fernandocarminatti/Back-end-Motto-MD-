package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Dto.SetBikeRenterDto;
import com.example.Motto.MD.Dto.UpdateBikeTransportationVehicle;
import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Repository.BikeTransportationVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BikeTransportationVehicleService {

    BikeTransportationVehicleRepository bikeTransportationVehicleRepository;
    RenterService renterService;

    public BikeTransportationVehicleService(BikeTransportationVehicleRepository bikeTransportationVehicleRepository, RenterService renterService) {
        this.bikeTransportationVehicleRepository = bikeTransportationVehicleRepository;
        this.renterService = renterService;
    }

    public Optional<BikeTransportationVehicle> createBikeTransportationVehicle(BikeTransportationVehicleDto bikeTransportationVehicleDto)  {
        boolean bikeChecker = bikeTransportationVehicleRepository.checkIfBikeTransportationVehicleExistsByPlateNumber(bikeTransportationVehicleDto.plateNumber());
        if(bikeChecker){
            return Optional.empty();
        }
        BikeTransportationVehicle bikeTransportationVehicle = new BikeTransportationVehicle(
                bikeTransportationVehicleDto.manufactureYear(),
                bikeTransportationVehicleDto.model(),
                bikeTransportationVehicleDto.plateNumber().toUpperCase());
        bikeTransportationVehicleRepository.save(bikeTransportationVehicle);
        return Optional.of(bikeTransportationVehicle);
    }

    public List<BikeTransportationVehicle> getAllBikeTransportationVehicles() {
        return bikeTransportationVehicleRepository.findAll();
    }

    public Optional<BikeTransportationVehicle> getBikeTransportationVehicleByPlateNumber(String plateNumber) {
            return Optional.of(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
    }

    public Optional<BikeTransportationVehicle> updateBikeTransportationVehicleByPlateNumber(String plateNumber, UpdateBikeTransportationVehicle updatedBikeTransportationVehicle) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        optionalBikeTransportationVehicle.get().setPlateNumber(updatedBikeTransportationVehicle.plateNumber().toUpperCase());
        return optionalBikeTransportationVehicle;
    }

    public boolean deleteBikeTransportationVehicle(String plateNumber) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty() || !optionalBikeTransportationVehicle.get().isAvailable()){
            return false;
        }
        bikeTransportationVehicleRepository.delete(optionalBikeTransportationVehicle.get());
        return true;
    }

    public Optional<BikeTransportationVehicle> setRenter(String plateNumber, SetBikeRenterDto setBikeRenterDto) {
        Optional<BikeTransportationVehicle> bikeTranspVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        Optional<Renter> renter = renterService.findByCnhNumber(setBikeRenterDto.renterCnhNumber());
        if(bikeTranspVehicle.isEmpty() || renter.isEmpty()){
            return Optional.empty();
        }
        bikeTranspVehicle.get().setRenter(renter.get());
        bikeTransportationVehicleRepository.save(bikeTranspVehicle.get());
        return bikeTranspVehicle;
    }
}
