package com.ufro.voy_y_vuelvo.service.trip;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import com.ufro.voy_y_vuelvo.repository.TripStopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripStopOrderService {

    private final TripStopOrderRepository tripStopOrderRepository;

    public ApiResponse<List<TripStopOrder>> findAllStopsFromTripId(Long tripId) {
        List<TripStopOrder> stops = tripStopOrderRepository.findTripStopOrderByTripId(tripId);

        if (stops == null || stops.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    "No se encontraron paradas para el viaje con ID: " + tripId,
                    null
            );
        }

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas encontradas correctamente para el viaje con ID: " + tripId,
                stops
        );
    }
}
