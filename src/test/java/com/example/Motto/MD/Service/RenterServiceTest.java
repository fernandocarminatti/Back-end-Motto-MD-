package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CnhImageExchangeDto;
import com.example.Motto.MD.Dto.CreateRenterDto;
import com.example.Motto.MD.Dto.RenterResponseDto;
import com.example.Motto.MD.Entity.CnhType;
import com.example.Motto.MD.Entity.Renter;
import com.example.Motto.MD.Repository.RenterRepository;
import com.example.Motto.MD.Service.Storage.StorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RenterServiceTest {

    @Mock RenterRepository renterRepository;
    @Mock StorageService storageService;
    @InjectMocks RenterService renterService;

    @DisplayName("Should Create and Try to persist a Renter")
    @ParameterizedTest
    @CsvSource({
            ".bmp",
            ".jpg",
    })
    void testCreateRenter(String extensions) {
        byte[] imageBytes = "Test CNH Image Data".getBytes();
        MockMultipartFile fakeMultipartFile = new MockMultipartFile("image" + extensions, imageBytes);
        CreateRenterDto renterSignUp = new CreateRenterDto("John", "2000-12-02", "123456789", "A", new MockMultipartFile("image.png", imageBytes));
        Renter renterReturn = new Renter("John", LocalDate.of(2000, 12, 2), "123456789", CnhType.A, "image" + extensions);
        renterReturn.setId(1L);
        when(this.renterRepository.existsByCnhNumber(any())).thenReturn(false);
        when(this.storageService.storeFile(any(), any())).thenReturn(renterSignUp.cnhNumber());
        when(this.renterRepository.save(any())).thenReturn(renterReturn);


        Optional<Renter> renter = renterService.createRenter(renterSignUp);

        assertTrue(renter.isPresent());
        assertEquals(renter.get().getCnhNumber(), renterReturn.getCnhNumber());
    }

    @Test
    @DisplayName("Should fail to create Renter with wrong date format")
    void testCreateRenterFailOnWrongDateFormat() {
        byte[] imageBytes = "Test CNH Image Data".getBytes();
        MockMultipartFile fakeMultipartFile = new MockMultipartFile("image.png" , imageBytes);
        CreateRenterDto renterSignUp = new CreateRenterDto("John", "02-12-2000", "123456789", "A", new MockMultipartFile("image.png", imageBytes));
        when(this.renterRepository.existsByCnhNumber(any())).thenReturn(false);
        when(this.storageService.storeFile(any(), any())).thenReturn(renterSignUp.cnhNumber());

        assertThrows(DateTimeParseException.class, () -> renterService.createRenter(renterSignUp));
    }

    @Test
    @DisplayName("Should return All Renters")
    void testGetAllRenters(){
        List<Renter> listOfRenters = List.of(
                new Renter("John 1", LocalDate.now(), "023456789", CnhType.A, "123456789.png"),
                new Renter("John 2", LocalDate.now(), "123456789", CnhType.AB, "123456789.png"),
                new Renter("John 3", LocalDate.now(), "223456789", CnhType.C, "123456789.png")
        );
        when(this.renterRepository.findAll()).thenReturn(listOfRenters);

        List<Renter> renters = renterService.getAllRenters();

        assertFalse(renters.isEmpty());
        assertEquals(renters.size(), 3);
    }

    @Test
    @DisplayName("Should return Optional Renter by Cnh Number")
    void testGetRenterByCnhNumber(){
        Renter renter = new Renter("John 1", LocalDate.now(), "023456789", CnhType.A, "123456789.png");
        when(this.renterRepository.findByCnhNumber(any())).thenReturn(renter);

        Optional<Renter> renterReturn = renterService.getRenterByCnhNumber(renter.getCnhNumber());

        assertTrue(renterReturn.isPresent());
        assertEquals(renterReturn.get().getCnhNumber(), renter.getCnhNumber());
    }

    @Test
    @DisplayName("Should not return Renter by non-existent Cnh Number")
    void testGetRenterByCnhNumberNotFound(){
        when(this.renterRepository.findByCnhNumber(any())).thenReturn(null);

        Optional<Renter> renterReturn = renterService.getRenterByCnhNumber("000000000");

        assertFalse(renterReturn.isPresent());
    }

    @Test
    @DisplayName("Should update Renter CNH Image")
    void testUpdateRenterByCnhNumber(){
        Renter renter = new Renter("John 1", LocalDate.now(), "023456789", CnhType.A, "023456789.png");
        MockMultipartFile fakeMultipartFile = new MockMultipartFile("image.jpg" , "Test CNH Image Data".getBytes());
        when(this.renterRepository.findByCnhNumber(any())).thenReturn(renter);
        when(this.storageService.storeFile(any(), any())).thenReturn(renter.getCnhNumber());

        Optional<Renter> renterReturn = renterService.changeCnhImage(renter.getCnhNumber(), new CnhImageExchangeDto(List.of(fakeMultipartFile)));

        assertTrue(renterReturn.isPresent());
        assertEquals(renterReturn.get().getCnhImage(), renter.getCnhImage());
    }

    @Test
    @DisplayName("Should delete Renter")
    void testDeleteRenter(){
        Renter renter = new Renter("John 1", LocalDate.now(), "023456789", CnhType.A, "023456789.png");
        when(this.renterRepository.findByCnhNumber(any())).thenReturn(renter);
        doNothing().when(this.renterRepository).delete(renter);

        boolean deleted = renterService.deleteRenter(renter.getCnhNumber());

        assertTrue(deleted);
    }
}