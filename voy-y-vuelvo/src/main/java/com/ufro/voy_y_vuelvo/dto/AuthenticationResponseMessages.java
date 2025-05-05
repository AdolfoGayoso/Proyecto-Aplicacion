package com.ufro.voy_y_vuelvo.dto;

public enum AuthenticationResponseMessages {
    RUT_EMAIL_ALREADY_EXISTS("Rut o email ya se encuentra registrado."),
    VERIFICATION_EMAIL_SENT("Se ha enviado un email de verificación."),
    FAIL_TO_SEND_VERIFICATION_EMAIL("No se ha podido enviar el mail de verificacion."),
    CUSTOMER_EMAIL_AUTHENTICATION_SUCCESS("Usuario ha valido su email con exito."),
    CUSTOMER_EMAIL_AUTHENTICATION_FAILURE("No se ha podido validar el email.");

    private final String message;

    AuthenticationResponseMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}




