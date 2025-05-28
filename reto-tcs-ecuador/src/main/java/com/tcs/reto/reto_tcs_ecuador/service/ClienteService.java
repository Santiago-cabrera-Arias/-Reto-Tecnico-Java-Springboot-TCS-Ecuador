package com.tcs.reto.reto_tcs_ecuador.service;

import com.tcs.reto.reto_tcs_ecuador.dto.ClienteRequestDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO createCliente(ClienteRequestDTO dto);

    ClienteResponseDTO updateCliente(Long id, ClienteRequestDTO dto);

    ClienteResponseDTO getClienteById(Long id);

    List<ClienteResponseDTO> getAllClientes();

    void deleteCliente(Long id);
}
