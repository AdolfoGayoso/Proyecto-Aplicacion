package com.ufro.voy_y_vuelvo.dto.authetication.register;

import com.ufro.voy_y_vuelvo.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegisterResponse {
    private UserDto user;
    private String userType;
    private boolean success;

}
