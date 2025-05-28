package com.tcs.reto.reto_tcs_ecuador.service;

import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {

    Cuenta createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(Long id, Cuenta cuenta);
    void deleteCuenta(Long id);
    Optional<Cuenta> getCuentaById(Long id);
    List<Cuenta> getAllCuentas();
    List<Cuenta> getCuentasByClienteId(Long clienteId);

}
