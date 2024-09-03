package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Service.BikeTransportationVehicleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> createBikeTransportationVehicle(@RequestBody @Validated BikeTransportationVehicleDto bikeTransportationVehicleDto) {
        try {
            bikeTransportationVehicleService.createBikeTransportationVehicle(bikeTransportationVehicleDto);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Duplicate \n}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("{ \n Error: Internal Server Error \n}");
        }
        return ResponseEntity.ok().build();
    }

}
