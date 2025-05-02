package com.ufro.voy_y_vuelvo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {
    private String rut;
    private String name;
    private String email;
}
