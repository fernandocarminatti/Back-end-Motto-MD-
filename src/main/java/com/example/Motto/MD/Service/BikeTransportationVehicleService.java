package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.BikeResponseDto;
import com.example.Motto.MD.Dto.BikeVehicleSignUpDto;
import com.example.Motto.MD.Dto.SetBikeRenterDto;
import com.example.Motto.MD.Dto.UpdateBikeTransportationVehicle;
import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Repository.BikeTransportationVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeTransportationVehicleService {

    BikeTransportationVehicleRepository bikeTransportationVehicleRepository;
    RenterService renterService;

    public BikeTransportationVehicleService(BikeTransportationVehicleRepository bikeTransportationVehicleRepository, RenterService renterService) {
        this.bikeTransportationVehicleRepository = bikeTransportationVehicleRepository;
        this.renterService = renterService;
    }

    public Optional<BikeResponseDto> createBikeTransportationVehicle(BikeVehicleSignUpDto bikeVehicleSignUpDto)  {
        boolean bikeChecker = bikeTransportationVehicleRepository.checkIfBikeTransportationVehicleExistsByPlateNumber(bikeVehicleSignUpDto.plateNumber());
        if(bikeChecker){
            return Optional.empty();
        }
        BikeTransportationVehicle bikeTransportationVehicle = new BikeTransportationVehicle(
                bikeVehicleSignUpDto.manufactureYear(),
                bikeVehicleSignUpDto.model(),
                bikeVehicleSignUpDto.plateNumber().toUpperCase());
        bikeTransportationVehicleRepository.save(bikeTransportationVehicle);
        return Optional.of(BikeResponseDto.fromEntity(bikeTransportationVehicle));
    }

    public List<BikeResponseDto> getAllBikeTransportationVehicles() {
        List<BikeTransportationVehicle> allBikeTransportationVehicles = bikeTransportationVehicleRepository.findAll();
        return allBikeTransportationVehicles.stream().map(BikeResponseDto::fromEntity).collect(Collectors.toList());
    }

    public Optional<BikeResponseDto> getBikeTransportationVehicleByPlateNumber(String plateNumber) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(BikeResponseDto.fromEntity(optionalBikeTransportationVehicle.get()));
    }

    public Optional<BikeResponseDto> updateBikeTransportationVehicleByPlateNumber(String plateNumber, UpdateBikeTransportationVehicle updatedBikeTransportationVehicle) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty()){
            return Optional.empty();
        }
        optionalBikeTransportationVehicle.get().setPlateNumber(updatedBikeTransportationVehicle.plateNumber().toUpperCase());
        bikeTransportationVehicleRepository.save(optionalBikeTransportationVehicle.get());

        return Optional.of(BikeResponseDto.fromEntity(optionalBikeTransportationVehicle.get()));
    }

    public boolean deleteBikeTransportationVehicle(String plateNumber) {
        Optional<BikeTransportationVehicle> optionalBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        if(optionalBikeTransportationVehicle.isEmpty() || !optionalBikeTransportationVehicle.get().isAvailable()){
            return false;
        }
        bikeTransportationVehicleRepository.delete(optionalBikeTransportationVehicle.get());
        return true;
    }

    public Optional<BikeResponseDto> setRenter(String plateNumber, SetBikeRenterDto setBikeRenterDto) {
        Optional<BikeTransportationVehicle> bikeTranspVehicle = Optional.ofNullable(bikeTransportationVehicleRepository.findByPlateNumber(plateNumber));
        Optional<Renter> renter = renterService.getRenterFullDataByCnhNumber(setBikeRenterDto.renterCnhNumber());
        if(bikeTranspVehicle.isEmpty() || renter.isEmpty()){
            return Optional.empty();
        }
        renter.get().setBikeTransportationVehicle(bikeTranspVehicle.get());
        bikeTranspVehicle.get().setRenter(renter.get());
        bikeTransportationVehicleRepository.save(bikeTranspVehicle.get());
        return Optional.of(BikeResponseDto.fromEntity(bikeTranspVehicle.get()));
    }

    public Optional<BikeResponseDto> returnBikeTransportationVehicle(String plateNumber) {
        boolean bikeChecker = bikeTransportationVehicleRepository.checkIfBikeTransportationVehicleExistsByPlateNumber(plateNumber);
        if(!bikeChecker){
            return Optional.empty();
        }
        BikeTransportationVehicle bikeTransportationVehicle = bikeTransportationVehicleRepository.findByPlateNumber(plateNumber);
        bikeTransportationVehicle.getRenter().setBikeTransportationVehicle(null);
        bikeTransportationVehicle.setRenter(null);
        bikeTransportationVehicleRepository.save(bikeTransportationVehicle);
        return Optional.of(BikeResponseDto.fromEntity(bikeTransportationVehicle));
    }
}
