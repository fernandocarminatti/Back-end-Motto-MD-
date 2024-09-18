package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.*;
import com.example.Motto.MD.Service.TransportationVehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v1/vehicles")
public class TransportationVehicleController {

    TransportationVehicleService transportationVehicleService;

    public TransportationVehicleController(TransportationVehicleService transportationVehicleService) {
        this.transportationVehicleService = transportationVehicleService;
    }

    @PostMapping
    public ResponseEntity<?> createTransportationVehicle(@RequestBody @Valid VehicleSignUpDto vehicleSignUpDto) {
        Optional<?> bikeTransportationVehicle = transportationVehicleService.createTransportationVehicle(vehicleSignUpDto);
        if(bikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).location(URI.create("/v1/bikes/" + vehicleSignUpDto.plateNumber())).body("{ \n Error: Bike Transportation Vehicle already exists \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/v1/bikes/" + vehicleSignUpDto.plateNumber())).body(bikeTransportationVehicle.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllTransportationVehicles() {
        List<VehicleResponseDto> allBikeTransportationVehicles = transportationVehicleService.getAllTransportationVehicles();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(allBikeTransportationVehicles);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<?> getTargetTransportationVehicle(@PathVariable String plateNumber) {
        Optional<?> targetTransportationVehicle = transportationVehicleService.getTransportationVehicleByPlateNumber(plateNumber);
        if(targetTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(targetTransportationVehicle.get());
    }

    @PatchMapping("/{plateNumber}")
    public ResponseEntity<?> updateTransportationVehicle(@PathVariable String plateNumber,@Valid @RequestBody UpdateTransportationVehicleDto updatedPlateNumber) {
        Optional<?> updatedEntity = transportationVehicleService.updateTransportationVehicleByPlateNumber(plateNumber, updatedPlateNumber);
        if (updatedEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(updatedEntity.get());
    }

    @DeleteMapping("/{plateNumber}/remove")
    public ResponseEntity<?> deleteTransportationVehicle(@PathVariable String plateNumber) {
        boolean deleted = transportationVehicleService.deleteTransportationVehicle(plateNumber);
        if(deleted){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Not Found or in a Rental Service \n}");
    }

}
