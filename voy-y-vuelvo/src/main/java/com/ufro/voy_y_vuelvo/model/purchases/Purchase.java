package com.ufro.voy_y_vuelvo.model.purchases;

import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private Long stopIdFrom;
    private Long stopIdTo;

    @ManyToOne
    private User user;

    private String email;
    private String rut;

    private LocalDateTime purchaseDate;
}
