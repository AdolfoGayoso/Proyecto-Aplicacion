package com.ufro.voy_y_vuelvo.model.tickets;

import com.ufro.voy_y_vuelvo.model.users.UserCustomer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issue;
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status = SupportTicketStatus.OPEN.toString();

    @ManyToOne
    @JoinColumn(name = "user_customer_id")
    private UserCustomer createdBy;
}
