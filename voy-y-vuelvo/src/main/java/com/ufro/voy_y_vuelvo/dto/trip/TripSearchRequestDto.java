package com.ufro.voy_y_vuelvo.dto.trip;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TripSearchRequestDto {
    private String origin;
    private String destination;
    private LocalDate date;
    private LocalTime departureTime;
}
