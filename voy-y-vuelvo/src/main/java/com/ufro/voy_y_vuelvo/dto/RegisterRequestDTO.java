package com.ufro.voy_y_vuelvo.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String rut;
    private String password;
    private String name;
    private String email;
}
