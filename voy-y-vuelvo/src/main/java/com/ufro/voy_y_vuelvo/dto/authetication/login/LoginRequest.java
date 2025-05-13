package com.ufro.voy_y_vuelvo.dto.authetication.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}