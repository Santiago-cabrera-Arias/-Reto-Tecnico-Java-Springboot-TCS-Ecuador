package com.tcs.reto.reto_tcs_ecuador.dto;

import com.tcs.reto.reto_tcs_ecuador.utils.TipoCuenta;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CuentaDTO {

    private Long cuentaId;

    @NotBlank(message = "El número de cuenta no puede estar vacío")
    private String numeroCuenta;

    @NotNull(message = "El tipo de cuenta no puede estar vacío")
    private TipoCuenta tipoCuenta;

    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private double saldoInicial;

    @NotNull(message = "El estado de la cuenta debe ser especificado")
    private boolean estado;

    @NotNull(message = "Debe especificarse un cliente asociado")
    private Long clienteId;
}
