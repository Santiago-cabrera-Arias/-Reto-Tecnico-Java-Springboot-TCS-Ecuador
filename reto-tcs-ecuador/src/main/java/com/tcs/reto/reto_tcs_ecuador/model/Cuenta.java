package com.tcs.reto.reto_tcs_ecuador.model;

import com.tcs.reto.reto_tcs_ecuador.utils.TipoCuenta;
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
public class Cuenta {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long cuentaId;
    @Column(nullable = false)
    private String numeroCuenta;
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    @Column(nullable = false)
    private double saldoInicial;
    @Column(nullable = false)
    private boolean estado;

    //Relacion Uno a muchos con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    //Relacion Uno a muchos con movimientos
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos;



}
