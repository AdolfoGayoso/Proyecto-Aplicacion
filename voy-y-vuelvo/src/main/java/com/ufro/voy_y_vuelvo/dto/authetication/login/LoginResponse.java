package com.ufro.voy_y_vuelvo.dto.authetication.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String email;
    private String username;
    private String userType;
}
