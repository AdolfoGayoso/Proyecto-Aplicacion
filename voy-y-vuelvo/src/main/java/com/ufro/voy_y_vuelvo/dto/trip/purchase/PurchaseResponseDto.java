package com.ufro.voy_y_vuelvo.dto.trip.purchase;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseResponseDto {

    private Long tripId;
    private Long stopIdFrom;
    private Long stopIdTo;
    private LocalDateTime purchaseDate;
    private String rut;
    private String email;
    private String uuid;

}
