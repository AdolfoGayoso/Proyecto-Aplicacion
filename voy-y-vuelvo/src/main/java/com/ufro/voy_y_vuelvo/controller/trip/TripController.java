package com.ufro.voy_y_vuelvo.controller.trip;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.trip.TripSearchRequestDto;
import com.ufro.voy_y_vuelvo.service.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllTrips() {
        ApiResponse<?> response = tripService.findAll();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-all-stop-order-trip-{tripId}")
    public ResponseEntity<ApiResponse<?>> getAllStopOrderTrip(@PathVariable Long tripId) {
        ApiResponse<?> response = tripService.findAllStopOrdersFromTripId(tripId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-trip-by-id-{tripId}")
    public ResponseEntity<ApiResponse<?>> getTripById(@PathVariable Long tripId) {
        ApiResponse<?> response = tripService.findById(tripId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<?>> searchTrips(@RequestBody TripSearchRequestDto request) {
        ApiResponse<?> response = tripService.searchTripByFilters(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
