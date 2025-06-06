package com.ufro.voy_y_vuelvo.dto.trip;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TripDto {
    private Long id;
    private Boolean active;
    private Integer numSeats;
    private String plateNumber;
    private Integer price;
    private LocalDate departureDate;
    private LocalTime departureTime;

    private StopDto origin;
    private StopDto destination;
}
