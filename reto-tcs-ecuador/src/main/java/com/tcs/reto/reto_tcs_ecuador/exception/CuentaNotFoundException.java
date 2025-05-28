package com.tcs.reto.reto_tcs_ecuador.exception;

public class CuentaNotFoundException extends RuntimeException {
    public CuentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
