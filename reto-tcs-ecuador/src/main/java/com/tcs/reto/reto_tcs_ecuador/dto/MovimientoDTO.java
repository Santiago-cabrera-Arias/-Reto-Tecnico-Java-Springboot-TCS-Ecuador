package com.tcs.reto.reto_tcs_ecuador.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimientoDTO {

    private Long movimientoId;

    @NotNull(message = "La fecha del movimiento es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;

    @NotBlank(message = "El tipo de movimiento no puede estar vacío")
    private String tipoMovimiento;

    @DecimalMin(value = "0.01", message = "El valor del movimiento debe ser mayor a 0")
    private double valor;

    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private double saldo;

    @NotBlank(message = "Debe especificar el número de cuenta")
    private String numeroCuenta;
}

