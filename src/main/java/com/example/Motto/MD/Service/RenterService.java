package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.RenterSignUpDto;
import com.example.Motto.MD.Dto.cnhImageExchangeDto;
import com.example.Motto.MD.Entity.CnhType;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Exceptions.RenterNotFoundException;
import com.example.Motto.MD.Exceptions.SignUpException;
import com.example.Motto.MD.Exceptions.StorageException;
import com.example.Motto.MD.Repository.RenterRepository;
import com.example.Motto.MD.Service.Storage.StorageService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RenterService {

    RenterRepository renterRepository;
    StorageService storageService;

    public RenterService(RenterRepository renterRepository, StorageService storageService) {
        this.renterRepository = renterRepository;
        this.storageService = storageService;
    }

    public Renter createRenter(RenterSignUpDto renterSignUp) {
        try {
            String fileUploadPath = storageService.storeFile(renterSignUp.cnhImage(), renterSignUp.cnhNumber());
            Renter newRenter = new Renter(
                    renterSignUp.name(),
                    renterSignUp.cnpj(),
                    LocalDate.parse(renterSignUp.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    renterSignUp.cnhNumber(),
                    CnhType.valueOf(renterSignUp.cnhType().toUpperCase()),
                    fileUploadPath
            );
            renterRepository.save(newRenter);
            return newRenter;
        } catch (DataIntegrityViolationException e) {
            throw new SignUpException("Error on SignUp, Renter might already exist. Check sent data and try again");
        }
    }

    public Renter findByCnhNumber(String cnhNumber) {
        try {
            return renterRepository.findByCnhNumber(cnhNumber);
        } catch (RenterNotFoundException e) {
            throw new RenterNotFoundException("Renter not found");
        }
    }

    public Renter changeCnhImage(cnhImageExchangeDto cnhImageExchange) {
        try {
            Renter renter = findByCnhNumber(cnhImageExchange.cnhNumber());
            String fileUploadPath = storageService.storeFile(cnhImageExchange.cnhImage().get(0), renter.getCnhNumber());
            renter.setCnhImage(fileUploadPath);
            return renterRepository.save(renter);
        } catch (StorageException e) {
            throw new StorageException("Error trying to change CNH Image. Check sent data and try again");
        }
    }

}
