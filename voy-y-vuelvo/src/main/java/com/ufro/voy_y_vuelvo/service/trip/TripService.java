package com.ufro.voy_y_vuelvo.service.trip;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.trip.TripSearchRequestDto;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import com.ufro.voy_y_vuelvo.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripStopOrderService tripStopOrderService;

    public ApiResponse<List<Trip>> findAll() {
        List<Trip> trips = tripRepository.findAll();
        return new ApiResponse<>(HttpStatus.OK.value(), "Lista de viajes obtenida correctamente", trips);
    }

    public ApiResponse<Trip> findById(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        if (trip == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Viaje no encontrado", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Viaje encontrado", trip);
    }

    public ApiResponse<List<TripStopOrder>> findAllStopOrdersFromTripId(Long tripId) {
        return tripStopOrderService.findAllStopsFromTripId(tripId);
    }

    public ApiResponse<List<Trip>> searchTripByFilters(TripSearchRequestDto request) {
        List<Trip> trips = tripRepository.findByFilters(
                request.getOrigin(),
                request.getDestination(),
                request.getDate(),
                request.getDepartureTime()
        );

        if (trips.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No se encontraron viajes.", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "Viajes encontrados", trips);
    }


}
