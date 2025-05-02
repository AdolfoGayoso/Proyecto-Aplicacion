package com.ufro.voy_y_vuelvo.service;

public enum AuthenticationResponseMessages {
    EMAIL_ALREADY_EXISTS("El email ya se encuentra registrado"),
    RUT_ALREADY_EXISTS("El rut ya se encuentra registrado"),
    RUT_EMAIL_ALREADY_EXISTT("El rut y email ya se encuentran registrados");

    AuthenticationResponseMessages(String s) {
    }
}




