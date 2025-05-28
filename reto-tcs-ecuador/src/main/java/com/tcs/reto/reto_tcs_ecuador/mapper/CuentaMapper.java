package com.tcs.reto.reto_tcs_ecuador.mapper;

import com.tcs.reto.reto_tcs_ecuador.dto.CuentaDTO;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.model.Cliente;

public class CuentaMapper {

    public static Cuenta toEntity(CuentaDTO dto, Cliente cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.isEstado());
        cuenta.setCliente(cliente);
        return cuenta;
    }

    public static CuentaDTO toDTO(Cuenta cuenta) {
        CuentaDTO dto = new CuentaDTO();
        dto.setCuentaId(cuenta.getCuentaId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setEstado(cuenta.isEstado());
        return dto;
    }
}
