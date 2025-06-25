package com.ufro.voy_y_vuelvo.dto.ticket;

import com.ufro.voy_y_vuelvo.model.tickets.SupportTicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTicketStatusRequestDto {
    private Long ticketId;
    private SupportTicketStatus newStatus;
}
