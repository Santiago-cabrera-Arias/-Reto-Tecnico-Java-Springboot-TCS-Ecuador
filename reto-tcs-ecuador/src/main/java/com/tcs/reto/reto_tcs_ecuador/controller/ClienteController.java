package com.tcs.reto.reto_tcs_ecuador.controller;

import com.tcs.reto.reto_tcs_ecuador.dto.ClienteRequestDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.ClienteResponseDTO;
import com.tcs.reto.reto_tcs_ecuador.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.createCliente(dto);
        return ResponseEntity.ok(response);
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.updateCliente(id, dto);
        return ResponseEntity.ok(response);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorId(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.getClienteById(id);
        return ResponseEntity.ok(response);
    }

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> lista = clienteService.getAllClientes();
        return ResponseEntity.ok(lista);
    }

    // Eliminar cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
