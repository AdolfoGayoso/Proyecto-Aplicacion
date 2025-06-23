package com.ufro.voy_y_vuelvo.dto.trip;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class CreateTripDto {
    private String plateNumber;
    private Integer price;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Integer numTotalSeats;

    private List<Long> stopsIds;
}
