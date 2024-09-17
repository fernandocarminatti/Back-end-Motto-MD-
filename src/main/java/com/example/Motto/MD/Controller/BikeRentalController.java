package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.TransportationVehicleRentalDto;
import com.example.Motto.MD.Service.VehicleRentalService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/rentals/bikes")
public class BikeRentalController {

    VehicleRentalService vehicleRentalService;

    public BikeRentalController(VehicleRentalService vehicleRentalService) {
        this.vehicleRentalService = vehicleRentalService;
    }

    @PostMapping("/bikes")
    public ResponseEntity<?> createBikeRental(@RequestBody TransportationVehicleRentalDto transportationVehicleRentalDto){
        boolean rentalCreated = vehicleRentalService.createBikeRental(transportationVehicleRentalDto);
        return rentalCreated ? ResponseEntity.status(HttpStatusCode.valueOf(201)).build() : ResponseEntity.status(HttpStatusCode.valueOf(409)).build();
    }

}
