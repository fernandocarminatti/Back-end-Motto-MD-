package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CnhImageExchangeDto;
import com.example.Motto.MD.Dto.RenterResponseDto;
import com.example.Motto.MD.Dto.CreateRenterDto;
import com.example.Motto.MD.Entity.CnhType;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Repository.RenterRepository;
import com.example.Motto.MD.Service.Storage.StorageService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RenterService {

    RenterRepository renterRepository;
    StorageService storageService;

    public RenterService(RenterRepository renterRepository, StorageService storageService) {
        this.renterRepository = renterRepository;
        this.storageService = storageService;
    }

    public Optional<Renter> createRenter(CreateRenterDto renterSignUp) {
        boolean renter = renterRepository.existsByCnhNumber(renterSignUp.cnhNumber());
        if(renter){
            return Optional.empty();
        }
        String fileUploadPath = storageService.storeFile(renterSignUp.cnhImage(), renterSignUp.cnhNumber());
        Renter newRenter = new Renter(
                renterSignUp.name(),
                LocalDate.parse(renterSignUp.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                renterSignUp.cnhNumber(),
                CnhType.valueOf(renterSignUp.cnhType().toUpperCase()),
                fileUploadPath
        );
        renterRepository.save(newRenter);
        return Optional.of(newRenter);
    }

    public List<Renter> getAllRenters() {
        return renterRepository.findAll();
    }

    public Optional<Renter> getRenterByCnhNumber(String cnhNumber) {
        return Optional.ofNullable(renterRepository.findByCnhNumber(cnhNumber));
    }

    public Optional<Renter> changeCnhImage(String cnhNumber, CnhImageExchangeDto cnhImageExchange) {
        Optional<Renter> renter = Optional.ofNullable(renterRepository.findByCnhNumber(cnhNumber));
        if(renter.isEmpty()){
            return renter;
        }
        if(renter.get().getCnhImage() != null || Objects.equals(renter.get().getCnhImage(), "")){
            storageService.deleteFile(renter.get().getCnhImage());
        }
        String fileUploadPath = storageService.storeFile(cnhImageExchange.cnhImage().get(0), renter.get().getCnhNumber());
        renter.get().setCnhImage(fileUploadPath);
        renterRepository.save(renter.get());
        return renter;
    }

    public boolean deleteRenter(String cnhNumber) {
        Optional<Renter> renter = Optional.ofNullable(renterRepository.findByCnhNumber(cnhNumber));
        if(renter.isEmpty()){
            return false;
        }
        renterRepository.delete(renter.get());
        storageService.deleteFile(renter.get().getCnhImage());
        return true;
    }
}
