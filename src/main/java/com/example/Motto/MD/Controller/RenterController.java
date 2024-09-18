package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.CnhImageExchangeDto;
import com.example.Motto.MD.Dto.RenterResponseDto;
import com.example.Motto.MD.Dto.CreateRenterDto;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Service.RenterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/renters")
public class RenterController {

    RenterService renterService;

    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping( consumes = "multipart/form-data" )
    public ResponseEntity<?> createRenter(@Valid @ModelAttribute CreateRenterDto renterSignUp) {
        Optional<RenterResponseDto> renter = renterService.createRenter(renterSignUp);
        if(renter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(409))
                    .location(URI.create("/v1/renters/" + renterSignUp.cnhNumber()))
                    .body("{ \n Error: Renter already exists \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .location(URI.create("/v1/renters/" + renterSignUp.cnhNumber()))
                .body(renter.get());
    }

    @GetMapping()
    public ResponseEntity<?> getAllRenters() {
        List<RenterResponseDto> allRenters = renterService.getAllRenters();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(allRenters);
    }

    @GetMapping("/{cnhNumber}")
    public ResponseEntity<?> getRenterByCnhNumber(@Valid @PathVariable String cnhNumber) {
        Optional<Renter> renter = renterService.getRenterByCnhNumber(cnhNumber);
        if(renter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(RenterResponseDto.fromEntity(renter.get()));
    }

    @PostMapping("/{cnhNumber}/update-cnh-image")
    public ResponseEntity<?> changeCnhImage(@PathVariable String cnhNumber, @Valid @ModelAttribute CnhImageExchangeDto cnhImageExchange) {
        Optional<Renter> renter = renterService.changeCnhImage(cnhNumber, cnhImageExchange);
        if(renter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(RenterResponseDto.fromEntity(renter.get()));
    }

    @DeleteMapping("/{cnhNumber}/remove")
    public ResponseEntity<?> deleteRenter(@Valid @PathVariable String cnhNumber) {
        boolean deleted = renterService.deleteRenter(cnhNumber);
        if(deleted){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Not Found or in a Rental Service \n}");
    }

}
