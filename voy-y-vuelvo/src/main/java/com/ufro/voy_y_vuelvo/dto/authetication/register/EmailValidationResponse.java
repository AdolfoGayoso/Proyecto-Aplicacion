package com.ufro.voy_y_vuelvo.dto.authetication.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailValidationResponse {
    private String emailToValidate;
    private Boolean success;
}
