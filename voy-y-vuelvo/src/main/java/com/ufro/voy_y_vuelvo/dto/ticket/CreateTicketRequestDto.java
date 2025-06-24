package com.ufro.voy_y_vuelvo.dto.ticket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequestDto {
    private String purchaseUuid;
    private String tittle;
    private String description;
}
