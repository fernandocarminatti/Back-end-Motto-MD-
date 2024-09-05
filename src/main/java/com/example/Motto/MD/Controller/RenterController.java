package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.RenterSignUpDto;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Service.RenterService;
import com.example.Motto.MD.Exceptions.StorageException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        try{
            Optional<Renter> optionalRenter = renterService.createRenter(renterSignUp);
            if(optionalRenter.isEmpty()){
                return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("{ \n Error: Renter Already Exists \n}");
            }
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).location(URI.create("/v1/renter/" + optionalRenter.get().getId())).body(optionalRenter.get());
        } catch(StorageException e ) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("{ \n Error: " + e.getMessage() + " \n Reason: " + e.getCause().getMessage() + " \n}");
        }
    }

    @GetMapping("/{cnhNumber}")
    public ResponseEntity<?> getRenterByCnhNumber(@PathVariable String cnhNumber) {
        Optional<Renter> optionalRenter = Optional.ofNullable(renterService.findByCnhNumber(cnhNumber));
        if(optionalRenter.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(optionalRenter.get());
    }


}
