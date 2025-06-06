package com.ufro.voy_y_vuelvo.controller.trip;

import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.service.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/get-all")
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }
}
