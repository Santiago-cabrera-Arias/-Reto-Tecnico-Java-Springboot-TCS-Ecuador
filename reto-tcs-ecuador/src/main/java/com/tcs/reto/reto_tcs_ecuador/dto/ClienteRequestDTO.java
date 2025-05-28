package com.tcs.reto.reto_tcs_ecuador.dto;

import com.tcs.reto.reto_tcs_ecuador.utils.Genero;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull(message = "El género es obligatorio")
    private Genero genero;
    @Min(value = 0, message = "La edad no puede ser negativa")
    private int edad;
    @Pattern(regexp = "^[0-9]{10}$", message = "La identificación debe tener 10 dígitos")
    private String identificacion;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;
    @Size(min = 4, max = 20, message = "La contraseña debe tener entre 4 y 20 caracteres")
    private String contrasena;
    private boolean estado;

}