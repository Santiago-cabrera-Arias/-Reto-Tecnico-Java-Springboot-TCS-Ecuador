package com.tcs.reto.reto_tcs_ecuador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Persona{

    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private boolean estado;

    //Relacion con Cuenta, para acceso bidirecional.
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Cuenta> cuentas;

}
