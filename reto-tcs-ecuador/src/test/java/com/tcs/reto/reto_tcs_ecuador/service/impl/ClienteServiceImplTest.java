package com.tcs.reto.reto_tcs_ecuador.service.impl;

import com.tcs.reto.reto_tcs_ecuador.dto.ClienteRequestDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.ClienteResponseDTO;
import com.tcs.reto.reto_tcs_ecuador.exception.ClienteNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.mapper.ClienteMapper;
import com.tcs.reto.reto_tcs_ecuador.model.Cliente;
import com.tcs.reto.reto_tcs_ecuador.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCliente() {
        // Arrange
        ClienteRequestDTO requestDTO = new ClienteRequestDTO();
        requestDTO.setNombre("Juan");
//        requestDTO.setGenero("Masculino");
        requestDTO.setEdad(30);
        requestDTO.setIdentificacion("0102030405");
        requestDTO.setDireccion("Calle Falsa 123");
        requestDTO.setTelefono("0991234567");
        requestDTO.setContrasena("1234");
        requestDTO.setEstado(true);

        Cliente clienteEntity = ClienteMapper.toEntity(requestDTO);
        Cliente savedEntity = new Cliente();
        savedEntity.setId(1L);
        savedEntity.setNombre("Juan");
//        savedEntity.setGenero("Masculino");
        savedEntity.setEdad(30);
        savedEntity.setIdentificacion("0102030405");
        savedEntity.setDireccion("Calle Falsa 123");
        savedEntity.setTelefono("0991234567");
        savedEntity.setContrasena("1234");
        savedEntity.setEstado(true);

        // Simular comportamiento del repositorio
        when(clienteRepository.save(any(Cliente.class))).thenReturn(savedEntity);

        // Act
        ClienteResponseDTO response = clienteService.createCliente(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Juan", response.getNombre());
        assertEquals("Masculino", response.getGenero());
        assertEquals(30, response.getEdad());
        assertEquals("0102030405", response.getIdentificacion());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }


    @Test
    void updateClienteSuccess() {
        Long id = 1L;
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNombre("Pedro");
//        dto.setGenero("Masculino");
        dto.setEdad(25);
        dto.setIdentificacion("0912345678");
        dto.setDireccion("Av. Loja 555");
        dto.setTelefono("0998765432");
        dto.setContrasena("abcd");
        dto.setEstado(true);

        when(clienteRepository.existsById(id)).thenReturn(true);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> {
            Cliente c = i.getArgument(0);
            c.setId(id);
            return c;
        });

        ClienteResponseDTO response = clienteService.updateCliente(id, dto);

        assertEquals("Pedro", response.getNombre());
        assertEquals(id, response.getId());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void updateClienteNotFound() {
        Long id = 99L;
        ClienteRequestDTO dto = new ClienteRequestDTO();
        when(clienteRepository.existsById(id)).thenReturn(false);

        assertThrows(ClienteNotFoundException.class, () -> clienteService.updateCliente(id, dto));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void getClienteByIdSuccess() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Carlos");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO response = clienteService.getClienteById(id);
        assertEquals("Carlos", response.getNombre());
    }

    @Test
    void getClienteByIdNotFound() {
        Long id = 100L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> clienteService.getClienteById(id));
    }

    @Test
    void getAllClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Luis");
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Ana");

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteResponseDTO> clientes = clienteService.getAllClientes();
        assertEquals(2, clientes.size());
    }

    @Test
    void deleteClienteSuccess() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);

        clienteService.deleteCliente(id);

        verify(clienteRepository).deleteById(id);
    }

    @Test
    void deleteClienteNotFound() {
        Long id = 2L;
        when(clienteRepository.existsById(id)).thenReturn(false);

        assertThrows(ClienteNotFoundException.class, () -> clienteService.deleteCliente(id));
    }

}