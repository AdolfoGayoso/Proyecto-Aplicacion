package com.ufro.voy_y_vuelvo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripEntityRepository extends JpaRepository<TripEntity, Long> {
}
