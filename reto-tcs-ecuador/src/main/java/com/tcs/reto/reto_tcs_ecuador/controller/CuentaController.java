package com.tcs.reto.reto_tcs_ecuador.controller;

import com.tcs.reto.reto_tcs_ecuador.dto.CuentaDTO;
import com.tcs.reto.reto_tcs_ecuador.mapper.CuentaMapper;
import com.tcs.reto.reto_tcs_ecuador.model.Cliente;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.repository.ClienteRepository;
import com.tcs.reto.reto_tcs_ecuador.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuentas")
@CrossOrigin("*")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ClienteRepository clienteRepository;

    // Crear cuenta
    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@Valid @RequestBody CuentaDTO dto) {
        Cliente cliente = getClienteEntityById(dto.getClienteId());
        Cuenta cuenta = CuentaMapper.toEntity(dto, cliente);
        Cuenta saved = cuentaService.createCuenta(cuenta);
        return ResponseEntity.ok(CuentaMapper.toDTO(saved));
    }

    // Actualizar cuenta
    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(@PathVariable Long id,
                                                      @Valid @RequestBody CuentaDTO dto) {
        Cliente cliente = getClienteEntityById(dto.getClienteId());
        Cuenta cuenta = CuentaMapper.toEntity(dto, cliente);
        cuenta.setCuentaId(id);
        Cuenta updated = cuentaService.updateCuenta(id, cuenta);
        return ResponseEntity.ok(CuentaMapper.toDTO(updated));
    }

    // Obtener cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerCuentaPorId(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.getCuentaById(id).orElseThrow();
        return ResponseEntity.ok(CuentaMapper.toDTO(cuenta));
    }

    // Listar todas las cuentas
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        List<CuentaDTO> cuentas = cuentaService.getAllCuentas()
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cuentas);
    }

    // Listar cuentas por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaDTO>> cuentasPorCliente(@PathVariable Long clienteId) {
        List<CuentaDTO> cuentas = cuentaService.getCuentasByClienteId(clienteId)
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cuentas);
    }

    // Eliminar cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }

    private Cliente getClienteEntityById(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
    }
}
