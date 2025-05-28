package com.tcs.reto.reto_tcs_ecuador.dto;

import com.tcs.reto.reto_tcs_ecuador.utils.Genero;
import lombok.Data;

@Data
public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private Genero genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private boolean estado;

}
