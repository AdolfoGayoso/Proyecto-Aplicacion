package com.ufro.voy_y_vuelvo.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TripCreationDTO {
    private int price;
    private int numSeats;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Long publisherId;
    private List<StopDTO> stops;
}