package com.tcs.reto.reto_tcs_ecuador.mapper;

import com.tcs.reto.reto_tcs_ecuador.dto.DetalleMovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.dto.MovimientoDTO;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Movimiento;

public class MovimientoMapper {

    public static Movimiento toEntity(MovimientoDTO dto, Cuenta cuenta) {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dto.getFecha());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(dto.getValor());
        movimiento.setSaldo(dto.getSaldo());
        movimiento.setCuenta(cuenta);
        return movimiento;
    }


    public static MovimientoDTO toDTO(Movimiento mov) {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setMovimientoId(mov.getMovimientoId());
        dto.setFecha(mov.getFecha());
        dto.setTipoMovimiento(mov.getTipoMovimiento());
        dto.setValor(mov.getValor());
        dto.setSaldo(mov.getSaldo());
        dto.setNumeroCuenta(mov.getCuenta().getNumeroCuenta());
        return dto;
    }


}
