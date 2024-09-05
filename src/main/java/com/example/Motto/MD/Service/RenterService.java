package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.RenterSignUpDto;
import com.example.Motto.MD.Entity.CnhType;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Repository.RenterRepository;
import com.example.Motto.MD.Service.Storage.StorageService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RenterService {

    RenterRepository renterRepository;
    StorageService storageService;

    public RenterService(RenterRepository renterRepository, StorageService storageService) {
        this.renterRepository = renterRepository;
        this.storageService = storageService;
    }

    public Optional<Renter> createRenter(RenterSignUpDto renterSignUp) {

        String fileUploadPath = storageService.storeFile(renterSignUp.cnhImage(), renterSignUp.cnhNumber());

        boolean renterExistence = renterRepository.checkIfRenterExistsByCnhNumber(renterSignUp.cnhNumber());
        if(renterExistence){
            return Optional.empty();
        }
        Renter newRenter = new Renter(
                renterSignUp.name(),
                renterSignUp.cnpj(),
                LocalDate.parse(renterSignUp.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                renterSignUp.cnhNumber(),
                CnhType.valueOf(renterSignUp.cnhType().toUpperCase()),
                fileUploadPath
        );
        renterRepository.save(newRenter);
        return Optional.of(newRenter);
    }

    public Renter findByCnhNumber(String cnhNumber) {
        return renterRepository.findByCnhNumber(cnhNumber);
    }
}
