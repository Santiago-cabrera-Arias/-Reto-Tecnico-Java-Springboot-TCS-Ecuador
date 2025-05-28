package com.tcs.reto.reto_tcs_ecuador.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }
}
