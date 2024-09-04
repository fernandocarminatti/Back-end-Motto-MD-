package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenterRepository extends JpaRepository<Renter, Long> {
    Renter findByCnhNumber(String cnhNumber);
}
