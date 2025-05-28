package com.tcs.reto.reto_tcs_ecuador.repository;

import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
    List<Cuenta> findByCliente_Id(Long clienteId);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

}
