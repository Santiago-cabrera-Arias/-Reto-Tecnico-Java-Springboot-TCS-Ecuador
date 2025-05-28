package com.tcs.reto.reto_tcs_ecuador.model;

import com.tcs.reto.reto_tcs_ecuador.utils.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Column(nullable = false)
    private int edad;
    @Column(nullable = false)
    private String identificacion;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String telefono;

}
