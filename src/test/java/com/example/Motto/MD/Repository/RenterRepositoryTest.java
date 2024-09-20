package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.CnhType;
import com.example.Motto.MD.Entity.Renter;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("tests")
class RenterRepositoryTest {

    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private EntityManager entityManager;

    private Renter createRenterForTests(String name, String birthDate, String cnhNumber, String cnhType, String cnhImage) {
        Renter baseRenter = new Renter (
                name,
                LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                cnhNumber,
                CnhType.valueOf(cnhType),
                cnhImage
        );
        renterRepository.save(baseRenter);
        renterRepository.flush();
        return baseRenter;
    }

    @Test
    @DisplayName("Find by CNH Number")
    void testFindRenterByCNHNumber() {
        createRenterForTests("John", "1900/01/01", "123456789", "A", "randomImage.jpg");
        assertTrue(renterRepository.existsByCnhNumber("123456789"));
    }

    @Test
    @DisplayName("Search for non-existing Renter by CNH Number")
    void testFindNonExistingRenterByCNHNumber() {
        assertFalse(renterRepository.existsByCnhNumber("123456789"));
    }

    @Test
    @DisplayName("Delete a Renter from the database")
    void testDeleteRenter(){
        Renter renter = createRenterForTests("John", "1900/01/01", "123456789", "A", "randomImage.jpg");
        renterRepository.deleteById(renter.getId());
        assertFalse(renterRepository.existsById(renter.getId()));
    }

    @Test
    @DisplayName("Update Renter in the database")
    void testUpdateRenter(){
        Renter baseRenter = createRenterForTests("John", "1900/01/01", "123456789", "A", "randomImage.jpg");
        baseRenter.setName("John Doe");
        renterRepository.save(baseRenter);
        assertEquals(renterRepository.findByCnhNumber("123456789").getName(), "John Doe");
    }

    @Test
    @DisplayName("Error when saving a Renter that already exists")
    void testSaveDuplicatedRenter() {
        createRenterForTests("John", "1900/01/01", "123456789", "A", "randomImage.jpg");
        Renter duplicatedRenter = new Renter(
                "John",
                LocalDate.parse("1900/01/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                "123456789",
                CnhType.A,
                "randomImage.jpg"
        );
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(duplicatedRenter));
    }

    @Test
    @DisplayName("Error when saving a Renter with invalid CNH Type")
    void testSaveRenterWithInvalidCnhType() {
        assertThrows(IllegalArgumentException.class, () -> new Renter(
                "John",
                LocalDate.parse("1900/01/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                "123456789",
                CnhType.valueOf("Z"),
                "randomImage.jpg"
        ));
    }

    @Test
    @DisplayName("Error when saving a Renter with invalid Birth Date Format (YYYY/MM/DD)")
    void testSaveRenterWithInvalidBirthDateFormat() {
        assertThrows(DateTimeParseException.class, () -> new Renter(
                "John",
                LocalDate.parse("1900/01/01", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "123456789",
                CnhType.A,
                "randomImage.jpg"
        ));
    }
}