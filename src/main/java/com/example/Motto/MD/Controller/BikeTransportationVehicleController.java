package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.BikeResponseDto;
import com.example.Motto.MD.Dto.BikeTransportationVehicleDto;
import com.example.Motto.MD.Dto.SetBikeRenterDto;
import com.example.Motto.MD.Dto.UpdateBikeTransportationVehicle;
import com.example.Motto.MD.Service.BikeTransportationVehicleService;
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
        Optional<BikeResponseDto> bikeTransportationVehicle = bikeTransportationVehicleService.createBikeTransportationVehicle(bikeTransportationVehicleDto);
        if(bikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).location(URI.create("/v1/bike-transportation-vehicle/" + bikeTransportationVehicleDto.plateNumber())).body("{ \n Error: Bike Transportation Vehicle already exists \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/v1/bike-transportation-vehicle/" + bikeTransportationVehicleDto.plateNumber())).body(bikeTransportationVehicle.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllBikeTransportationVehicles() {
        List<BikeResponseDto> allBikeTransportationVehicles = bikeTransportationVehicleService.getAllBikeTransportationVehicles();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(allBikeTransportationVehicles);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<?> getTargetBikeTransportationVehicle(@PathVariable String plateNumber) {
        Optional<BikeResponseDto> singleBikeTransportationVehicle = bikeTransportationVehicleService.getBikeTransportationVehicleByPlateNumber(plateNumber);
        if(singleBikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(singleBikeTransportationVehicle.get());
    }

    @PatchMapping("/{plateNumber}")
    public ResponseEntity<?> updateBikeTransportationVehicle(@PathVariable String plateNumber, @RequestBody UpdateBikeTransportationVehicle updatedPlateNumber) {
        Optional<BikeResponseDto> updatedEntity = bikeTransportationVehicleService.updateBikeTransportationVehicleByPlateNumber(plateNumber, updatedPlateNumber);
        if (updatedEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(updatedEntity.get());
    }

    @DeleteMapping("/{plateNumber}")
    public ResponseEntity<?> deleteBikeTransportationVehicle(@PathVariable String plateNumber) {
        boolean deleted = bikeTransportationVehicleService.deleteBikeTransportationVehicle(plateNumber);
        if(deleted){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Not Found or in a Rental Service \n}");
    }

    @PostMapping("/{plateNumber}/rent")
    public ResponseEntity<?> setRenter(@PathVariable String plateNumber,@RequestBody SetBikeRenterDto setBikeRenterDto) {
        Optional<BikeResponseDto> bikeTransportationVehicle = bikeTransportationVehicleService.setRenter(plateNumber, setBikeRenterDto);
        if(bikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(bikeTransportationVehicle.get());
    }

    @PostMapping("/{plateNumber}/return")
    public ResponseEntity<?> returnBikeTransportationVehicle(@PathVariable String plateNumber) {
        Optional<BikeResponseDto> bikeTransportationVehicle = bikeTransportationVehicleService.returnBikeTransportationVehicle(plateNumber);
        if(bikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(bikeTransportationVehicle.get());
    }

}
