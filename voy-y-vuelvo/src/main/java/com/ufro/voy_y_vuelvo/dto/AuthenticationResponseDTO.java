package com.ufro.voy_y_vuelvo.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {
    private CustomerDTO customer;
    private String message;
    private boolean success;
}