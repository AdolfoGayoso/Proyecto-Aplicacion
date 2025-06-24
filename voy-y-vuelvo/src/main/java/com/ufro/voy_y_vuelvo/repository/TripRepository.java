package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.users.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("""
    SELECT DISTINCT t FROM Trip t
    JOIN t.stops s1
    JOIN t.stops s2
    WHERE s1.stop.name = :origin
      AND s2.stop.name = :destination
      AND s1.stopOrder < s2.stopOrder
      AND t.departureDate = :date
      AND t.departureTime >= :departureTime
""")
    List<Trip> findByFilters(String origin, String destination, LocalDate date, LocalTime departureTime);

    List<Trip> findByPublisher(Publisher publisher);
}
