package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.RenterSignUpDto;
import com.example.Motto.MD.Dto.cnhImageExchangeDto;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Service.RenterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/v1/renter")
public class RenterController {

    RenterService renterService;

    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping( consumes = "multipart/form-data" )
    public ResponseEntity<Renter> createRenter(@Valid @ModelAttribute RenterSignUpDto renterSignUp) {
        Renter renter = renterService.createRenter(renterSignUp);
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .location(
                        URI.create("/v1/renter/" + renter.getId()))
                .body(renter);
    }

    @GetMapping("/{cnhNumber}")
    public ResponseEntity<Renter> getRenterByCnhNumber(@PathVariable String cnhNumber) {
        Renter renter = renterService.findByCnhNumber(cnhNumber);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(renter);
    }

    @PostMapping("/{cnhNumber}")
    public ResponseEntity<Renter> changeCnhImage(@Valid @ModelAttribute cnhImageExchangeDto cnhImageExchange) {
        Renter renter = renterService.changeCnhImage(cnhImageExchange);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(renter);
    }

}
