package com.ufro.voy_y_vuelvo.dto.authetication.register;

import com.ufro.voy_y_vuelvo.model.users.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegisterRequest {

    private String email;
    private String password;
    private String userName;
    private String rut;

    private String userType = UserType.CUSTOMER.getUserType();
    private Integer points = 0;
    private Boolean emailVerified = false;
}