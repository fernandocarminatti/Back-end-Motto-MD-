package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.UpdateVehicleDto;
import com.example.Motto.MD.Dto.VehicleResponseDto;
import com.example.Motto.MD.Dto.VehicleSignUpDto;
import com.example.Motto.MD.Service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<?> createTransportationVehicle(@RequestBody @Valid VehicleSignUpDto vehicleSignUpDto) {
        Optional<?> bikeTransportationVehicle = vehicleService.createTransportationVehicle(vehicleSignUpDto);
        if(bikeTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).location(URI.create("/v1/bikes/" + vehicleSignUpDto.plateNumber())).body("{ \n Error: Bike Transportation Vehicle already exists \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/v1/bikes/" + vehicleSignUpDto.plateNumber())).body(bikeTransportationVehicle.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllTransportationVehicles() {
        List<VehicleResponseDto> allBikeTransportationVehicles = vehicleService.getAllTransportationVehicles();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(allBikeTransportationVehicles);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<?> getTargetTransportationVehicle(@PathVariable String plateNumber) {
        Optional<?> targetTransportationVehicle = vehicleService.getVehicleByPlateNumber(plateNumber);
        if(targetTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(targetTransportationVehicle.get());
    }

    @PatchMapping("/{plateNumber}")
    public ResponseEntity<?> updateTransportationVehicle(@PathVariable String plateNumber,@Valid @RequestBody UpdateVehicleDto updatedPlateNumber) {
        Optional<?> updatedEntity = vehicleService.updateTransportationVehicleByPlateNumber(plateNumber, updatedPlateNumber);
        if (updatedEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(updatedEntity.get());
    }

    @DeleteMapping("/{plateNumber}/remove")
    public ResponseEntity<?> deleteTransportationVehicle(@PathVariable String plateNumber) {
        boolean deleted = vehicleService.deleteTransportationVehicle(plateNumber);
        if(deleted){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Not Found or in a Rental Service \n}");
    }

}
