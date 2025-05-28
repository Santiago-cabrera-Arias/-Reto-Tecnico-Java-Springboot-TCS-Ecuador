package com.tcs.reto.reto_tcs_ecuador.repository;

import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Movimiento,Long> {

    List<Movimiento> findByCuenta_CuentaId(Long cuentaId);
    List<Movimiento> findByCuenta_Cliente_IdAndFechaBetween(Long clienteId, LocalDate from, LocalDate to);

}
