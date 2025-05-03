package com.ufro.voy_y_vuelvo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price;
    private int numSeats;
    private String occupationMatrix;
    private LocalDate departureDate;
    private LocalTime departureTime;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    @OneToMany(mappedBy = "id")
    private List<StopEntity> stops;




}
