package com.tcs.reto.reto_tcs_ecuador.service;

import com.tcs.reto.reto_tcs_ecuador.dto.DetalleMovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.MovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Movimiento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoService {

    Movimiento registrarMovimiento(MovimientoDTO dto);
    Movimiento updateMovimiento(Long id, MovimientoDTO dto);
    void deleteMovimiento(Long id);
    Optional<Movimiento> getMovimientoById(Long id);
    List<Movimiento> getAllMovimientos();
    List<Movimiento> getMovimientosByCuentaId(Long cuentaId);
    List<DetalleMovimientoDTO> getMovimientosByClienteAndDateRange(Long clienteId, LocalDate from, LocalDate to);
}
