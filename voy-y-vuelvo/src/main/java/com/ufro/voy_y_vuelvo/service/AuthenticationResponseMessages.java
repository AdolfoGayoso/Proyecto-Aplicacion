package com.ufro.voy_y_vuelvo.service;

public enum AuthenticationResponseMessages {
    RUT_EMAIL_ALREADY_EXISTS("Rut o email ya se encuentra registrado"),
    REGISTER_SUCCESSFUL("Nuevo usuario registrado con exito"),;

    private final String message;

    AuthenticationResponseMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}




