package com.ufro.voy_y_vuelvo.service.trip;

import com.ufro.voy_y_vuelvo.dto.trip.TripDto;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public TripDto toDto(Trip trip) {
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setActive(trip.getActive());
        tripDto.setNumSeats(trip.getNumSeats());
        tripDto.setPlateNumber(trip.getPlateNumber());
        tripDto.setPrice(trip.getPrice());
        tripDto.setDepartureDate(trip.getDepartureDate());
        tripDto.setDepartureTime(trip.getDepartureTime());

        return tripDto;
    }
}
