package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripStopOrderRepository extends JpaRepository<TripStopOrder, Long> {
    List<TripStopOrder> findTripStopOrderByTripId(Long tripId);
}
