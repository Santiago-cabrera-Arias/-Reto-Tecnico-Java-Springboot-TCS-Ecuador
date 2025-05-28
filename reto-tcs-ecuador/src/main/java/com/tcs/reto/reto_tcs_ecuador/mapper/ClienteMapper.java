package com.tcs.reto.reto_tcs_ecuador.mapper;

import com.tcs.reto.reto_tcs_ecuador.dto.ClienteRequestDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.ClienteResponseDTO;
import com.tcs.reto.reto_tcs_ecuador.model.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.isEstado());
        return cliente;
    }

    public static ClienteResponseDTO toDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEstado(cliente.isEstado());
        return dto;
    }
}

