package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Service.BikeTransportationVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/bike-transportation-vehicle")
public class BikeTransportationVehicleController {

    BikeTransportationVehicleService bikeTransportationVehicleService;

    public BikeTransportationVehicleController(BikeTransportationVehicleService bikeTransportationVehicleService) {
        this.bikeTransportationVehicleService = bikeTransportationVehicleService;
    }

    @PostMapping
    public ResponseEntity<?> createBikeTransportationVehicle(@RequestBody BikeTransportationVehicleDto bikeTransportationVehicleDto) {
        try {
            bikeTransportationVehicleService.createBikeTransportationVehicle(bikeTransportationVehicleDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
