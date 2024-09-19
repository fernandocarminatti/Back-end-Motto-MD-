package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CreateRentalDto;
import com.example.Motto.MD.Entity.*;
import com.example.Motto.MD.Repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    RentalRepository rentalRepository;
    VehicleService vehicleService;
    RenterService renterService;

    public RentalService(RentalRepository rentalRepository, VehicleService vehicleService, RenterService renterService) {
        this.rentalRepository = rentalRepository;
        this.vehicleService = vehicleService;
        this.renterService = renterService;
    }

    public Optional<Rental> createRental(CreateRentalDto createRentalDto) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleByPlateNumber(createRentalDto.vehiclePlateNumber());
        Optional<Renter> renter = renterService.getRenterByCnhNumber(createRentalDto.renterCnhNumber());
        if(vehicle.isEmpty() || renter.isEmpty()){
            throw new IllegalArgumentException("Invalid Vehicle or Renter. Check if Vehicle and Renter exist.");
        }
        boolean validRenter = rentalRepository.existsByRenterId(renter.get().getId());
        if(validRenter){
            throw new IllegalArgumentException("Seems there is already a Rental for this Renter. Current Rental limit is 1 per CNH Number.");
        }
        Rental newRental = RentalFactory.createRental(vehicle.get(), renter.get(), createRentalDto.rentalPeriod());
        rentalRepository.save(newRental);

        vehicle.get().setAvailable(false);
        vehicleService.updateVehicleStatus(vehicle.get());

        return Optional.of(newRental);
    }

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id){
        return rentalRepository.findById(id);
    }

}
