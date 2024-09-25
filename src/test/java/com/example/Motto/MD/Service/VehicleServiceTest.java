package com.example.Motto.MD.Service;

import com.example.Motto.MD.Dto.CreateVehicleDto;
import com.example.Motto.MD.Dto.UpdateVehicleDto;
import com.example.Motto.MD.Dto.VehicleResponseDto;
import com.example.Motto.MD.Entity.Vehicle;
import com.example.Motto.MD.Entity.VehicleFactory;
import com.example.Motto.MD.Repository.VehicleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock VehicleRepository vehicleRepository;
    @InjectMocks VehicleService vehicleService;

    @DisplayName("Should Create and Try to persist a Vehicle")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testCreateBikeVehicle(int vehicleTypes) {
        Vehicle expectedReturn = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a bike", "ABC1D23"));

        when(this.vehicleRepository.existsByPlateNumber("ABC1D23")).thenReturn(false);
        when(this.vehicleRepository.save(any(Vehicle.class))).thenReturn(expectedReturn);

        Optional<Vehicle> savedVehicle = vehicleService.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a bike", "ABC1D23"));

        assertTrue(savedVehicle.isPresent());
        assertEquals(savedVehicle.get().getVehicleType(), expectedReturn.getVehicleType());
    }

    @DisplayName("Should return empty Optional for a Vehicle that exits")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testCreateVehicleWithExistingPlateNumber(int vehicleTypes) {
        when(this.vehicleRepository.existsByPlateNumber("ABC1D23")).thenReturn(true);

        Optional<Vehicle> savedVehicle = vehicleService.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a Vehicle", "ABC1D23"));

        assertTrue(savedVehicle.isEmpty());
    }

    @Test
    @DisplayName("Should return All Vehicles")
    void testGetAllVehicles() {
        Vehicle bike = VehicleFactory.createVehicle(new CreateVehicleDto(1, "2000", "Just a bike", "ABC1D23"));
        Vehicle car = VehicleFactory.createVehicle(new CreateVehicleDto(2, "2000", "Just a car", "ABC1D24"));
        Vehicle transportation = VehicleFactory.createVehicle(new CreateVehicleDto(3, "2000", "Just a transportation", "ABC1D25"));

        when(this.vehicleRepository.findAll()).thenReturn(List.of(bike, car, transportation));

        assertEquals(vehicleService.getAllVehicles().size(), 3);
    }

    @DisplayName("Should return Optional Vehicle by Plate Number")
    @ParameterizedTest
    @CsvSource({"ABC1D23, 1", "ABC1D24, 2", "ABC1D25, 3"})
    void testGetVehicleByPlateNumber(String plateNumbers, int vehicleTypes) {
        Vehicle returnVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a vehicle", plateNumbers));
        when(this.vehicleRepository.findByPlateNumber(plateNumbers)).thenReturn(returnVehicle);

        Optional<Vehicle> vehicle = vehicleService.getVehicleByPlateNumber(plateNumbers);

        assertTrue(vehicle.isPresent());
        assertEquals(vehicle.get().getPlateNumber(), returnVehicle.getPlateNumber());
        assertEquals(vehicle.get().getVehicleType(), returnVehicle.getVehicleType());
    }

    @DisplayName("Should return Empty Optional Vehicle by Plate Number")
    @ParameterizedTest
    @CsvSource({"ABC1D26, 1", "ABC1D27, 2", "ABC1D28, 3"})
    void testGetVehicleByPlateNumberNotFound(String plateNumbers, int vehicleTypes) {
        when(this.vehicleRepository.findByPlateNumber(plateNumbers)).thenReturn(null);

        Optional<Vehicle> vehicle = vehicleService.getVehicleByPlateNumber(plateNumbers);

        assertTrue(vehicle.isEmpty());
    }

    @DisplayName("Should Update Vehicle Plate Number")
    @ParameterizedTest
    @CsvSource({"ABC1D23, 1", "ABC1D24, 2", "ABC1D25, 3"})
    void testUpdateVehicleByPlateNumber(String plateNumbers, int vehicleTypes) {
        Vehicle baseTestVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a vehicle", "ZZZ0Z00"));
        baseTestVehicle.setId((long) vehicleTypes);
        Vehicle updatedVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a Vehicle", plateNumbers));
        UpdateVehicleDto updateVehicleDto = new UpdateVehicleDto(plateNumbers);

        when(this.vehicleRepository.findByPlateNumber(plateNumbers)).thenReturn(baseTestVehicle);
        when(this.vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Optional<VehicleResponseDto> testVehicle = vehicleService.updateVehicleByPlateNumber(plateNumbers, updateVehicleDto);

        assertTrue(testVehicle.isPresent());
        assertEquals(testVehicle.get().plateNumber(), updatedVehicle.getPlateNumber());
        assertEquals(testVehicle.get().vehicleType(), updatedVehicle.getVehicleType());
    }

    @DisplayName("Should delete a Vehicle")
    @ParameterizedTest
    @CsvSource({"ABC1D23, 1", "ABC1D24, 2", "ABC1D25, 3"})
    void testDeleteVehicle(String plateNumbers, int vehicleTypes) {
        Vehicle baseTestVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a vehicle", plateNumbers));

        when(this.vehicleRepository.findByPlateNumber(plateNumbers)).thenReturn(baseTestVehicle);
        doNothing().when(this.vehicleRepository).delete(baseTestVehicle);

        boolean deleted = vehicleService.deleteVehicle(plateNumbers);

        assertTrue(deleted);
    }

    @DisplayName("Should not delete a Vehicle with Active Rental Service")
    @ParameterizedTest
    @CsvSource({"ABC1D23, 1", "ABC1D24, 2", "ABC1D25, 3"})
    void testDeleteVehicleWithRentalService(String plateNumbers, int vehicleTypes) {
        Vehicle baseTestVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleTypes, "2000", "Just a vehicle", plateNumbers));
        baseTestVehicle.setAvailable(false);

        when(this.vehicleRepository.findByPlateNumber(plateNumbers)).thenReturn(baseTestVehicle);

        boolean deleted = vehicleService.deleteVehicle(plateNumbers);

        assertFalse(deleted);
    }

    @Test
    @DisplayName("Should not delete a Vehicle that does not exist")
    void testDeleteVehicleNotFound() {
        when(this.vehicleRepository.findByPlateNumber("ZZZ0Z00")).thenReturn(null);

        boolean deleted = vehicleService.deleteVehicle("ZZZ0Z00");

        assertFalse(deleted);
    }

}