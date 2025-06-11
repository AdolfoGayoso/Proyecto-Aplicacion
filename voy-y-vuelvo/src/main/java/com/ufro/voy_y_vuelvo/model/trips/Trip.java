package com.ufro.voy_y_vuelvo.model.trips;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ufro.voy_y_vuelvo.model.users.Publisher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean active;
    private Integer numTotalSeats;

    private String plateNumber;
    private Integer price;
    private LocalDate departureDate;
    private LocalTime departureTime;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference
    private Publisher publisher;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stopOrder ASC")
    private List<TripStopOrder> stops;

}
