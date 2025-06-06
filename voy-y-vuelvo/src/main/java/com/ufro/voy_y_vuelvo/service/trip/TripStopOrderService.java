package com.ufro.voy_y_vuelvo.service.trip;

import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import com.ufro.voy_y_vuelvo.repository.TripStopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripStopOrderService {

    private final TripStopOrderRepository tripStopOrderRepository;

    public List<TripStopOrder> findAllStopsFromTripId(Long tripId) {

    }
}
