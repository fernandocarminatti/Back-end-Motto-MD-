package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.TransportationVehicleRentalDto;
import com.example.Motto.MD.Repository.VehicleRentalRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleRentalService {

    VehicleRentalRepository vehicleRentalRepository;
    VehicleService vehicleService;
    RenterService renterService;

    public VehicleRentalService(VehicleRentalRepository vehicleRentalRepository, VehicleService vehicleService, RenterService renterService) {
        this.vehicleRentalRepository = vehicleRentalRepository;
        this.vehicleService = vehicleService;
        this.renterService = renterService;
    }

    public boolean createBikeRental(TransportationVehicleRentalDto transportationVehicleRentalDto){
        return false;
    }

}
