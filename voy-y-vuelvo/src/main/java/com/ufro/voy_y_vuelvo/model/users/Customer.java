package com.ufro.voy_y_vuelvo.model.users;

import com.ufro.voy_y_vuelvo.model.purchases.Purchase;
import com.ufro.voy_y_vuelvo.model.tickets.SupportTicket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
public class Customer extends User {

    private Integer points = 0;

    private Boolean emailVerified = false;
    private String emailVerificationCode;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupportTicket> supportTickets = new ArrayList<>();

}