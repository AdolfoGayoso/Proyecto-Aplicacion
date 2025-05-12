package com.ufro.voy_y_vuelvo.model.users;

import lombok.Getter;

@Getter
public enum UserType {
    CUSTOMER("CUSTOMER"),
    PUBLISHER("PUBLISHER");

    private final String userType;

    UserType(String value) {
        this.userType = value;
    }

}