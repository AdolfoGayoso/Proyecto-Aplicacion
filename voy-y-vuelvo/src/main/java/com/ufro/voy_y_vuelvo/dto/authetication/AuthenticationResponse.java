package com.ufro.voy_y_vuelvo.dto.authetication;

import com.ufro.voy_y_vuelvo.dto.user.UserDto;

public class AuthenticationResponse {
    private UserDto user;
    private String userType;
    private String message;
    private boolean success;
}
