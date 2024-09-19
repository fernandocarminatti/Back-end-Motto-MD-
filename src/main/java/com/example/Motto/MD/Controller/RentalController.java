package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.CreateRentalDto;
import com.example.Motto.MD.Dto.RentalResponseDto;
import com.example.Motto.MD.Entity.Rental;
import com.example.Motto.MD.Service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/rentals")
public class RentalController {

    RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalResponseDto>> getAllRentals(){
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalResponseDto> rentalsResponseDto = rentals.stream().map(RentalResponseDto::fromEntity).toList();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(rentalsResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable("id") Long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        if(rental.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("{ \n Error: \n [Rental not found] \n }");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(RentalResponseDto.fromEntity(rental.get()));
    }

    @PostMapping("/rent")
    public ResponseEntity<?> createRental(@Valid @RequestBody CreateRentalDto createRentalDto){
        Optional<Rental> rentalCreated = rentalService.createRental(createRentalDto);
        if (rentalCreated.isEmpty() || rentalCreated.get().getRenter() == null || rentalCreated.get().getVehicle() == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: \n [Invalid Vehicle or Renter. Check if Vehicle and Renter exist.] \n }");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/api/v1/rentals/" + rentalCreated.get().getId())).body(RentalResponseDto.fromEntity(rentalCreated.get()));
    }

}