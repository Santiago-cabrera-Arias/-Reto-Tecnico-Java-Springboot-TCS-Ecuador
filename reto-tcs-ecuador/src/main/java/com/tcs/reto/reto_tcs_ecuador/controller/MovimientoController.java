package com.tcs.reto.reto_tcs_ecuador.controller;

import com.tcs.reto.reto_tcs_ecuador.dto.DetalleMovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.MovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.exception.CuentaNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.mapper.MovimientoMapper;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Movimiento;
import com.tcs.reto.reto_tcs_ecuador.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin("*")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDTO> registrarMovimiento(@Valid @RequestBody MovimientoDTO dto) {
        Movimiento mov = movimientoService.registrarMovimiento(dto);
        return ResponseEntity.ok(MovimientoMapper.toDTO(mov));
    }

    // Obtener todos los movimientos
    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> listarMovimientos() {
        return ResponseEntity.ok(
                movimientoService.getAllMovimientos()
                        .stream()
                        .map(MovimientoMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    // Obtener movimientos por cuenta
    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<MovimientoDTO>> movimientosPorCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(
                movimientoService.getMovimientosByCuentaId(cuentaId)
                        .stream()
                        .map(MovimientoMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    // Obtener movimientos por cliente en un rango de fechas
    @GetMapping("/cliente/{clienteId}/reportes")
    public ResponseEntity<List<DetalleMovimientoDTO>> movimientosPorClienteYFechas(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        List<DetalleMovimientoDTO> movimientos = movimientoService.getMovimientosByClienteAndDateRange(clienteId, desde, hasta);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerMovimientoPorId(@PathVariable Long id) {
        Optional<Movimiento> movimiento = movimientoService.getMovimientoById(id);
        return movimiento.map(value -> ResponseEntity.ok(MovimientoMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar movimiento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO> actualizarMovimiento(@PathVariable Long id,
                                                              @Valid @RequestBody MovimientoDTO dto) {
        Movimiento actualizado = movimientoService.updateMovimiento(id, dto);
        return ResponseEntity.ok(MovimientoMapper.toDTO(actualizado));
    }



}
