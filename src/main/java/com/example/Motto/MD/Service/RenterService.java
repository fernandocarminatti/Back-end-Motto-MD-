package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.RenterSignUpDto;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Repository.RenterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RenterService {

    RenterRepository renterRepository;

    public RenterService(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    public Optional<Renter> createRenter(RenterSignUpDto renterSignUp) {
        boolean renterExistence = renterRepository.checkIfRenterExistsByCnhNumber(renterSignUp.cnhNumber());
        if(renterExistence){
            return Optional.empty();
        }
        Renter newRenter = new Renter(
                renterSignUp.name(),
                renterSignUp.cnpj(),
                renterSignUp.birthDate(),
                renterSignUp.cnhNumber(),
                renterSignUp.cnhType(),
                renterSignUp.cnhImage()
        );
        renterRepository.save(newRenter);
        return Optional.of(newRenter);
    }

    public Renter findByCnhNumber(String cnhNumber) {
        return renterRepository.findByCnhNumber(cnhNumber);
    }

}
