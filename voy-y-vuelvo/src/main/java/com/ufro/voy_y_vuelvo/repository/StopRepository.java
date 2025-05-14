package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.trips.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
}
