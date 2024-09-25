package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.CreateVehicleDto;
import com.example.Motto.MD.Dto.UpdateVehicleDto;
import com.example.Motto.MD.Dto.VehicleResponseDto;
import com.example.Motto.MD.Entity.Vehicle;
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
    public ResponseEntity<?> createVehicle(@RequestBody @Valid CreateVehicleDto createVehicleDto) {
        Optional<Vehicle> vehicle = vehicleService.createVehicle(createVehicleDto);
        if(vehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).location(URI.create("/api/v1/vehicles" + createVehicleDto.plateNumber())).body("Error: Vehicle already exists");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/api/v1/vehicles" + createVehicleDto.plateNumber())).body(VehicleResponseDto.fromEntity(vehicle.get()));
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        List<VehicleResponseDto> allVehicles = vehicleService.getAllVehicles().stream().map(VehicleResponseDto::fromEntity).toList();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(allVehicles);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<?> getTargetVehicle(@PathVariable String plateNumber) {
        Optional<?> targetTransportationVehicle = vehicleService.getVehicleByPlateNumber(plateNumber);
        if(targetTransportationVehicle.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(targetTransportationVehicle.get());
    }

    @PatchMapping("/{plateNumber}")
    public ResponseEntity<?> updateVehicle(@PathVariable String plateNumber, @Valid @RequestBody UpdateVehicleDto updatedPlateNumber) {
        Optional<?> updatedEntity = vehicleService.updateVehicleByPlateNumber(plateNumber, updatedPlateNumber);
        if (updatedEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(updatedEntity.get());
    }

    @DeleteMapping("/{plateNumber}/remove")
    public ResponseEntity<?> deleteVehicle(@PathVariable String plateNumber) {
        boolean deleted = vehicleService.deleteVehicle(plateNumber);
        if(deleted){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Error: Not Found or in a Rental Service");
    }

}
