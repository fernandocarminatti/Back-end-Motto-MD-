package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Dto.CreateVehicleDto;
import com.example.Motto.MD.Entity.Vehicle;
import com.example.Motto.MD.Entity.VehicleFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
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

    private Vehicle createVehicleForTests(CreateVehicleDto createVehicleDto) {
        Vehicle baseVehicle = VehicleFactory.createVehicle(createVehicleDto);
        entityManager.persist(baseVehicle);
        entityManager.flush();
        return baseVehicle;
    }

    @Test
    @DisplayName("Find by existing plate number")
    void testVehicleExistsByPlateNumber() {
        Vehicle vehicle = createVehicleForTests(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        Optional<Vehicle> vehicleFound = Optional.ofNullable(vehicleRepository.findByPlateNumber("ABC1D23"));
        assertTrue(vehicleFound.isPresent());
    }

    @Test
    @DisplayName("Find by non-existing plate number")
    void testVehicleDoesNotExistByPlateNumber() {
        Optional<Vehicle> vehicleFound = Optional.ofNullable(vehicleRepository.findByPlateNumber("ZZZ9D99"));
        assertFalse(vehicleFound.isPresent());
    }

    @Test
    @DisplayName("Error when saving a vehicle that already exists")
    void testSaveDuplicatedVehicle() {
        createVehicleForTests(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        Vehicle duplicatedVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(duplicatedVehicle));
    }

    @Test
    @DisplayName("Delete a vehicle from the database")
    void testDeleteVehicle(){
        Vehicle vehicle = createVehicleForTests(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        vehicleRepository.deleteById(vehicle.getId());
        assertFalse(vehicleRepository.existsById(vehicle.getId()));
    }

    @Test
    @DisplayName("Update vehicle in the database")
    void testUpdateVehicle(){
        Vehicle baseVehicle = createVehicleForTests(new CreateVehicleDto(2, "2020", "Accord", "ABC1D23"));
        baseVehicle.setPlateNumber("TES5T35");
        vehicleRepository.save(baseVehicle);
        assertTrue(vehicleRepository.existsByPlateNumber("TES5T35"));
        assertFalse(vehicleRepository.existsByPlateNumber("ABC1D23"));
    }

}