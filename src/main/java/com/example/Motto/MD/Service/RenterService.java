package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CnhImageExchangeDto;
import com.example.Motto.MD.Dto.RenterResponseDto;
import com.example.Motto.MD.Dto.RenterSignUpDto;
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

    public Optional<RenterResponseDto> createRenter(RenterSignUpDto renterSignUp) {
        boolean renter = renterRepository.checkIfRenterExistsByCnhNumber(renterSignUp.cnhNumber());
        if(renter){
            return Optional.empty();
        }
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
        return Optional.of(RenterResponseDto.fromEntity(newRenter));
    }

    public List<RenterResponseDto> getAllRenters() {
        List<Renter> allRenters = renterRepository.findAll();
        return allRenters.stream().map(RenterResponseDto::fromEntity).collect(Collectors.toList());
    }

    public Optional<RenterResponseDto> findByCnhNumber(String cnhNumber) {
        Renter renter = renterRepository.findByCnhNumber(cnhNumber);
        if(renter == null){
            return Optional.empty();
        }
        return Optional.of(RenterResponseDto.fromEntity(renter));
    }

    public Optional<?> changeCnhImage(String cnhNumber, CnhImageExchangeDto cnhImageExchange) {
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
        return Optional.of(RenterResponseDto.fromEntity(renter.get()));
    }

    public boolean deleteRenter(String cnhNumber) {
        Optional<Renter> renter = Optional.ofNullable(renterRepository.findByCnhNumber(cnhNumber));
        if(renter.isEmpty() || renter.get().hasActiveRental()){
            return false;
        } else {
            renterRepository.delete(renter.get());
            return true;
        }
    }

    protected Optional<Renter> getByCnhNumber(String cnhNumber){
        return Optional.ofNullable(renterRepository.findByCnhNumber(cnhNumber));
    }
}
