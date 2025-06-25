package com.ufro.voy_y_vuelvo.dto.trip;

import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class TripResponseDto {
    private Long id;
    private boolean active;
    private int numTotalSeats;
    private int numSeatsSold;
    private String plateNumber;
    private int price;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private List<TripStopOrder> stops;
    private String publisherName; // Este es el userName del Publisher
}
