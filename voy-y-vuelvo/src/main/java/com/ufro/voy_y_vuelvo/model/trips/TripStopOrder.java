package com.ufro.voy_y_vuelvo.model.trips;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TripStopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "stop_id")
    private Stop stop;

    private Integer availableSeats;

    private Integer stopOrder;
}
