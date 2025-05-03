package com.ufro.voy_y_vuelvo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class PublisherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String password;



    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<TripEntity> tripsPublished;
}
