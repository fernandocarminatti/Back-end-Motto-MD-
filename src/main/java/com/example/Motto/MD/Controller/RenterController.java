package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.RenterSignUpDto;
import com.example.Motto.MD.Dto.CnhImageExchangeDto;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Service.RenterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/v1/renter")
public class RenterController {

    RenterService renterService;

    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping( consumes = "multipart/form-data" )
    public ResponseEntity<?> createRenter(@Valid @ModelAttribute RenterSignUpDto renterSignUp) {
        Optional<Renter> renter = renterService.createRenter(renterSignUp);
        if(renter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(409))
                    .location(URI.create("/v1/renter/" + renterSignUp.cnhNumber()))
                    .body("{ \n Error: Renter already exists \n}");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .location(URI.create("/v1/renter/" + renter.get().getCnhNumber()))
                .body(renter);
    }

    @GetMapping("/{cnhNumber}")
    public ResponseEntity<?> getRenterByCnhNumber(@PathVariable String cnhNumber) {
        Optional<Renter> renter = renterService.findByCnhNumber(cnhNumber);
        if(renter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(renter);
    }

    @PostMapping("/{cnhNumber}/update-cnh-image")
    public ResponseEntity<?> changeCnhImage(@Valid @ModelAttribute CnhImageExchangeDto cnhImageExchange) {
        Optional<Renter> renter = renterService.changeCnhImage(cnhImageExchange);
        if(renter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(renter);
    }

}
