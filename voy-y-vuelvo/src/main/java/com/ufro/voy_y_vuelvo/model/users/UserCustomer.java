package com.ufro.voy_y_vuelvo.model.users;

import com.ufro.voy_y_vuelvo.model.purchases.Purchase;
import com.ufro.voy_y_vuelvo.model.tickets.SupportTicket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String  userType = UserType.CUSTOMER.toString();

    private String password;
    private String rut;
    private String userName;
    private String email;

    private int points;

    private boolean emailVerified;
    private String emailVerificationCode;

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<SupportTicket> supportTickets = new ArrayList<>();

}
