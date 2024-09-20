package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Dto.CreateVehicleDto;
import com.example.Motto.MD.Entity.Vehicle;
import com.example.Motto.MD.Entity.VehicleFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("tests")
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Find by existing plate number")
    void testVehicleExistsByPlateNumber() {
        Vehicle vehicle = createAndSaveVehicleForTest(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        Optional<Vehicle> vehicleFound = Optional.ofNullable(vehicleRepository.findByPlateNumber("ABC1D23"));
        assertTrue(vehicleFound.isPresent());
    }

    @Test
    @DisplayName("Find by non-existing plate number")
    void testVehicleDoesNotExistByPlateNumber() {
        Optional<Vehicle> vehicleFound = Optional.ofNullable(vehicleRepository.findByPlateNumber("ABC1D23"));
        assertFalse(vehicleFound.isPresent());
    }

    @Test
    @DisplayName("Save a duplicated vehicle")
    void testSaveDuplicatedVehicle() {
        createAndSaveVehicleForTest(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        Vehicle duplicatedVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(duplicatedVehicle));
    }

    private Vehicle createAndSaveVehicleForTest(CreateVehicleDto createVehicleDto) {
        Vehicle vehicleToSave = VehicleFactory.createVehicle(createVehicleDto);
        entityManager.persist(vehicleToSave);
        entityManager.flush();
        return vehicleToSave;
    }
}