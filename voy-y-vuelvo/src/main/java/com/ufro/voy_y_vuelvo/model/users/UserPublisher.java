package com.ufro.voy_y_vuelvo.model.users;

import com.ufro.voy_y_vuelvo.model.trips.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserPublisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String  userType = UserType.PUBLISHER.toString();

    private String password;
    private String rut;
    private String userName;
    private String email;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> publishedTrips = new ArrayList<>();
}
