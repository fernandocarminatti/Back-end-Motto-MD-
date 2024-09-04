package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Dto.UpdateBikeTransportationVehicle;
import com.example.Motto.MD.Entity.BikeTransportationVehicle;
import com.example.Motto.MD.Service.BikeTransportationVehicleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v1/bike-transportation-vehicle")
public class BikeTransportationVehicleController {

    BikeTransportationVehicleService bikeTransportationVehicleService;

    public BikeTransportationVehicleController(BikeTransportationVehicleService bikeTransportationVehicleService) {
        this.bikeTransportationVehicleService = bikeTransportationVehicleService;
    }

    @PostMapping
    public ResponseEntity<?> createBikeTransportationVehicle(@RequestBody @Validated BikeTransportationVehicleDto bikeTransportationVehicleDto) {
        BikeTransportationVehicle bikeTransportationVehicle;
        try {
            bikeTransportationVehicle = bikeTransportationVehicleService.createBikeTransportationVehicle(bikeTransportationVehicleDto);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Bike Already Exists \n}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("{ \n Error: Internal Server Error \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/v1/bike-transportation-vehicle/" + bikeTransportationVehicle.getPlateNumber())).body(bikeTransportationVehicle);
    }

    @GetMapping
    public ResponseEntity<?> getAllBikeTransportationVehicles() {
        List<BikeTransportationVehicle> allBikeTransportationVehicles;
        try {
            allBikeTransportationVehicles = bikeTransportationVehicleService.getAllBikeTransportationVehicles();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("{ \n Error: Internal Server Error \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(allBikeTransportationVehicles);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<?> getTargetBikeTransportationVehicle(@PathVariable String plateNumber) {
        Optional<BikeTransportationVehicle> singleBikeTransportationVehicle = Optional.ofNullable(bikeTransportationVehicleService.getBikeTransportationVehicleByPlateNumber(plateNumber));
        if(singleBikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(singleBikeTransportationVehicle.get());
    }

    @PatchMapping("/{plateNumber}")
    public ResponseEntity<?> updateBikeTransportationVehicle(@PathVariable String plateNumber, @RequestBody UpdateBikeTransportationVehicle updatedPlateNumber) {
        Optional<BikeTransportationVehicle> updatedEntity;
        try {
            updatedEntity = Optional.ofNullable(bikeTransportationVehicleService.updateBikeTransportationVehicleByPlateNumber(plateNumber, updatedPlateNumber));
            if (updatedEntity.isEmpty()) {
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(updatedEntity.get());
    }

    @DeleteMapping("/{plateNumber}")
    public ResponseEntity<?> deleteBikeTransportationVehicle(@PathVariable String plateNumber) {
        try {
            boolean deleted = bikeTransportationVehicleService.deleteBikeTransportationVehicle(plateNumber);
            if(deleted){
                return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
            }
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Not Found or in a Rental Service \n}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("{ \n Error: Internal Server Error \n}");
        }
    }

}
