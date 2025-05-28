package com.tcs.reto.reto_tcs_ecuador.service.impl;

import com.tcs.reto.reto_tcs_ecuador.dto.DetalleMovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.MovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.exception.CuentaNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.exception.MovimientoNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.mapper.MovimientoMapper;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Movimiento;
import com.tcs.reto.reto_tcs_ecuador.repository.CuentaRepository;
import com.tcs.reto.reto_tcs_ecuador.repository.MovimientoRepository;
import com.tcs.reto.reto_tcs_ecuador.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public Movimiento registrarMovimiento(MovimientoDTO dto) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + dto.getNumeroCuenta()));

        // Obtener todos los movimientos previos, ordenados
        List<Movimiento> movimientosPrevios = movimientoRepository
                .findByCuenta_CuentaId(cuenta.getCuentaId())
                .stream()
                .sorted(Comparator.comparing(Movimiento::getFecha).thenComparing(Movimiento::getMovimientoId))
                .collect(Collectors.toList());

        double saldoAnterior = cuenta.getSaldoInicial();
        for (Movimiento mov : movimientosPrevios) {
            if (mov.getFecha().isBefore(dto.getFecha()) || mov.getFecha().isEqual(dto.getFecha())) {
                saldoAnterior = mov.getSaldo(); // último saldo conocido
            }
        }

        double nuevoSaldo;
        switch (dto.getTipoMovimiento().toUpperCase()) {
            case "DEPOSITO":
                nuevoSaldo = saldoAnterior + dto.getValor();
                break;
            case "RETIRO":
                nuevoSaldo = saldoAnterior - dto.getValor();
                if (nuevoSaldo < 0) {
                    throw new MovimientoNotFoundException("Saldo insuficiente para realizar el retiro");
                }
                break;
            default:
                throw new MovimientoNotFoundException("Tipo de movimiento no válido: " + dto.getTipoMovimiento());
        }

        Movimiento movimiento = MovimientoMapper.toEntity(dto, cuenta);
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(dto.getFecha());

        return movimientoRepository.save(movimiento);
    }

    @Override
    public Movimiento updateMovimiento(Long id, MovimientoDTO dto) {
        if (!movimientoRepository.existsById(id)) {
            throw new MovimientoNotFoundException("Movimiento no encontrado con ID: " + id);
        }

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + dto.getNumeroCuenta()));

        Movimiento movimiento = MovimientoMapper.toEntity(dto, cuenta);
        movimiento.setMovimientoId(id);

        return movimientoRepository.save(movimiento);
    }


    @Override
    public void deleteMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new MovimientoNotFoundException("No se puede eliminar. Movimiento no encontrado con ID: " + id);
        }
        movimientoRepository.deleteById(id);
    }

    @Override
    public Optional<Movimiento> getMovimientoById(Long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public List<Movimiento> getMovimientosByCuentaId(Long cuentaId) {
        return movimientoRepository.findByCuenta_CuentaId(cuentaId);
    }

    @Override
    public List<DetalleMovimientoDTO> getMovimientosByClienteAndDateRange(Long clienteId, LocalDate from, LocalDate to) {
        List<Movimiento> movimientos = movimientoRepository
                .findByCuenta_Cliente_IdAndFechaBetween(clienteId, from, to)
                .stream()
                .sorted(Comparator.comparing(Movimiento::getFecha)
                        .thenComparing(Movimiento::getMovimientoId)) // Orden cronológico
                .collect(Collectors.toList());

        List<DetalleMovimientoDTO> detalleList = new ArrayList<>();
        Map<Long, Double> saldoCuentaMap = new HashMap<>(); // <cuentaId, saldo>

        for (Movimiento mov : movimientos) {
            DetalleMovimientoDTO dto = new DetalleMovimientoDTO();

            Long cuentaId = mov.getCuenta().getCuentaId();
            double saldoAnterior = saldoCuentaMap.getOrDefault(cuentaId, mov.getCuenta().getSaldoInicial());

            dto.setFecha(mov.getFecha());
            dto.setCliente(mov.getCuenta().getCliente().getNombre());
            dto.setNumeroCuenta(mov.getCuenta().getNumeroCuenta());
            dto.setTipoCuenta(mov.getCuenta().getTipoCuenta().name());
            dto.setEstado(mov.getCuenta().isEstado());
            dto.setMovimiento(mov.getTipoMovimiento().equalsIgnoreCase("RETIRO") ? -mov.getValor() : mov.getValor());
            dto.setSaldoInicial(saldoAnterior);

            double nuevoSaldo;
            if (mov.getTipoMovimiento().equalsIgnoreCase("DEPOSITO")) {
                nuevoSaldo = saldoAnterior + mov.getValor();
            } else if (mov.getTipoMovimiento().equalsIgnoreCase("RETIRO")) {
                nuevoSaldo = saldoAnterior - mov.getValor();
            } else {
                throw new IllegalArgumentException("Tipo de movimiento desconocido: " + mov.getTipoMovimiento());
            }

            dto.setSaldoDisponible(nuevoSaldo);
            saldoCuentaMap.put(cuentaId, nuevoSaldo);

            detalleList.add(dto);
        }

        return detalleList;
    }
}
