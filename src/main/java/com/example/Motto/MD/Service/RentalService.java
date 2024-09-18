package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CreateRentalDto;
import com.example.Motto.MD.Entity.*;
import com.example.Motto.MD.Repository.RentalRepository;
import org.springframework.stereotype.Service;

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
            return new TransportationVehicleRental(null, null);
        }
        Rental newRental = RentalFactory.createRental(vehicle.get(), renter.get());
        rentalRepository.save(newRental);
        return newRental;
    }
}
