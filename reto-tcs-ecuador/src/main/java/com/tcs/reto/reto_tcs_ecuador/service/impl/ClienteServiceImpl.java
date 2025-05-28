package com.tcs.reto.reto_tcs_ecuador.service.impl;

import com.tcs.reto.reto_tcs_ecuador.dto.ClienteRequestDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.ClienteResponseDTO;
import com.tcs.reto.reto_tcs_ecuador.exception.ClienteNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.mapper.ClienteMapper;
import com.tcs.reto.reto_tcs_ecuador.model.Cliente;
import com.tcs.reto.reto_tcs_ecuador.repository.ClienteRepository;
import com.tcs.reto.reto_tcs_ecuador.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ClienteResponseDTO createCliente(ClienteRequestDTO dto) {
        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente saved = clienteRepository.save(cliente);
        return ClienteMapper.toDTO(saved);
    }

    public ClienteResponseDTO updateCliente(Long id, ClienteRequestDTO dto) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setId(id); // <-- el ID viene de Persona

        Cliente actualizado = clienteRepository.save(cliente);

        return ClienteMapper.toDTO(actualizado);
    }


    @Override
    public ClienteResponseDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        return ClienteMapper.toDTO(cliente);
    }

    @Override
    public List<ClienteResponseDTO> getAllClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
