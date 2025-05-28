package com.tcs.reto.reto_tcs_ecuador.service.impl;

import com.tcs.reto.reto_tcs_ecuador.dto.MovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.exception.CuentaNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.exception.MovimientoNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.mapper.MovimientoMapper;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Movimiento;
import com.tcs.reto.reto_tcs_ecuador.repository.CuentaRepository;
import com.tcs.reto.reto_tcs_ecuador.repository.MovimientoRepository;
import com.tcs.reto.reto_tcs_ecuador.utils.TipoCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceImplTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = new Cuenta();
        cuenta.setCuentaId(1L);
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta(TipoCuenta.Ahorro);
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
    }

    @Test
    void registrarMovimientoDeposito_exitoso() {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta("1234567890");
        dto.setFecha(LocalDate.now());
        dto.setTipoMovimiento("DEPOSITO");
        dto.setValor(200);

        when(cuentaRepository.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.findByCuenta_CuentaId(1L)).thenReturn(new ArrayList<>());
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(i -> i.getArgument(0));

        Movimiento resultado = movimientoService.registrarMovimiento(dto);

        assertEquals(1200.0, resultado.getSaldo());
        assertEquals("DEPOSITO", resultado.getTipoMovimiento());
        verify(movimientoRepository).save(any(Movimiento.class));
    }

    @Test
    void registrarMovimientoRetiro_saldoInsuficiente() {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta("1234567890");
        dto.setFecha(LocalDate.now());
        dto.setTipoMovimiento("RETIRO");
        dto.setValor(1200);

        when(cuentaRepository.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.findByCuenta_CuentaId(1L)).thenReturn(new ArrayList<>());

        MovimientoNotFoundException ex = assertThrows(MovimientoNotFoundException.class,
                () -> movimientoService.registrarMovimiento(dto));

        assertTrue(ex.getMessage().contains("Saldo insuficiente"));
    }

    @Test
    void updateMovimiento_exitoso() {
        // DTO de entrada
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta("1234567890");
        dto.setFecha(LocalDate.now());
        dto.setTipoMovimiento("DEPOSITO");
        dto.setValor(100);
        dto.setSaldo(1100); // Se puede recalcular o usar directamente según la lógica de negocio

        // Cuenta asociada
        when(cuentaRepository.findByNumeroCuenta("1234567890"))
                .thenReturn(Optional.of(cuenta));

        // Verifica existencia del movimiento
        when(movimientoRepository.existsById(1L))
                .thenReturn(true);

        // Simula persistencia del movimiento
        when(movimientoRepository.save(any(Movimiento.class)))
                .thenAnswer(invocation -> {
                    Movimiento mov = invocation.getArgument(0);
                    mov.setMovimientoId(1L);
                    return mov;
                });

        // Llama al método del service
        Movimiento resultado = movimientoService.updateMovimiento(1L, dto);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("DEPOSITO", resultado.getTipoMovimiento());
        assertEquals(1100, resultado.getSaldo());
        assertEquals(1L, resultado.getMovimientoId());
        verify(movimientoRepository).save(any(Movimiento.class));
    }


    @Test
    void deleteMovimiento_exitoso() {
        when(movimientoRepository.existsById(1L)).thenReturn(true);

        movimientoService.deleteMovimiento(1L);

        verify(movimientoRepository).deleteById(1L);
    }

    @Test
    void deleteMovimiento_notFound() {
        when(movimientoRepository.existsById(1L)).thenReturn(false);

        assertThrows(MovimientoNotFoundException.class, () -> movimientoService.deleteMovimiento(1L));
    }

    @Test
    void getMovimientoById_found() {
        Movimiento mov = new Movimiento();
        mov.setMovimientoId(1L);
        when(movimientoRepository.findById(1L)).thenReturn(Optional.of(mov));

        Optional<Movimiento> result = movimientoService.getMovimientoById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void getAllMovimientos() {
        when(movimientoRepository.findAll()).thenReturn(List.of(new Movimiento(), new Movimiento()));

        List<Movimiento> result = movimientoService.getAllMovimientos();

        assertEquals(2, result.size());
    }

    @Test
    void getMovimientosByCuentaId() {
        List<Movimiento> lista = List.of(new Movimiento());
        when(movimientoRepository.findByCuenta_CuentaId(1L)).thenReturn(lista);

        List<Movimiento> result = movimientoService.getMovimientosByCuentaId(1L);

        assertEquals(1, result.size());
    }
}
