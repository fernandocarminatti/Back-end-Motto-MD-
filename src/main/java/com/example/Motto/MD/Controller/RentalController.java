package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.CreateRentalDto;
import com.example.Motto.MD.Dto.RentalResponseDto;
import com.example.Motto.MD.Entity.Rental;
import com.example.Motto.MD.Service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/rentals")
public class RentalController {

    RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<?> createBikeRental(@Valid @RequestBody CreateRentalDto createRentalDto){
        Optional<Rental> rentalCreated = Optional.ofNullable(rentalService.createRental(createRentalDto));
        if (rentalCreated.isEmpty() || rentalCreated.get().getRenter() == null || rentalCreated.get().getVehicle() == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("Error: Invalid Vehicle or Renter");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/api/v1/rentals/" + rentalCreated.get().getId())).body(RentalResponseDto.fromEntity(rentalCreated.get()));
    }

}
