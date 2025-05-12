package com.ufro.voy_y_vuelvo.model.users;

import com.ufro.voy_y_vuelvo.model.trips.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PUBLISHER")
@Getter
@Setter
public class Publisher extends User {

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> publishedTrips = new ArrayList<>();

}