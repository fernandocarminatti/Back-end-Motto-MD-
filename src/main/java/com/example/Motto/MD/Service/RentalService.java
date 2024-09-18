package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CreateRentalDto;
import com.example.Motto.MD.Entity.*;
import com.example.Motto.MD.Repository.RentalRepository;
import org.springframework.stereotype.Service;

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

    public Rental createRental(CreateRentalDto createRentalDto) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleByPlateNumber(createRentalDto.vehiclePlateNumber());
        Optional<Renter> renter = renterService.getRenterByCnhNumber(createRentalDto.renterCnhNumber());
        if(vehicle.isEmpty() || renter.isEmpty()){
            return new TransportationVehicleRental(null, null, null);
        }
        Rental newRental = RentalFactory.createRental(vehicle.get(), renter.get(), createRentalDto.rentalPeriod());
        rentalRepository.save(newRental);
        return newRental;
    }

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id){
        return rentalRepository.findById(id);
    }

}
