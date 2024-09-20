package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {
    Renter findByCnhNumber(String cnhNumber);
    boolean existsByCnhNumber(String cnhNumber);
}
