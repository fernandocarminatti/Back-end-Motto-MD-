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
        Optional<Renter> renterCheck = Optional.ofNullable(renterRepository.findByCnhNumber(renterSignUp.cnhNumber()));
        if(renterCheck.isEmpty()){
            renterCheck = Optional.of(new Renter(
                    renterSignUp.name(),
                    renterSignUp.cnpj(),
                    renterSignUp.birthDate(),
                    renterSignUp.cnhNumber(),
                    renterSignUp.cnhType(),
                    renterSignUp.cnhImage()
            ));
            System.out.println(renterSignUp.cnhNumber());
            return Optional.of(renterRepository.save(renterCheck.get()));
        }
        return Optional.empty();
    }

    public Renter findByCnhNumber(String cnhNumber) {
        return renterRepository.findByCnhNumber(cnhNumber);
    }
}
