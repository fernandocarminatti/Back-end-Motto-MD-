package com.example.Motto.MD.Repository;

import com.example.Motto.MD.Entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {
    Renter findByCnhNumber(String cnhNumber);

    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(r.id) > 0 THEN true ELSE false END FROM tb_renter r WHERE r.cnh_number = ?1")
    boolean checkIfRenterExistsByCnhNumber(String cnhNumber);

}
