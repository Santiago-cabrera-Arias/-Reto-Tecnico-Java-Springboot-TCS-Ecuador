package com.tcs.reto.reto_tcs_ecuador.service.impl;

import com.tcs.reto.reto_tcs_ecuador.exception.CuentaNotFoundException;
import com.tcs.reto.reto_tcs_ecuador.model.Cuenta;
import com.tcs.reto.reto_tcs_ecuador.repository.CuentaRepository;
import com.tcs.reto.reto_tcs_ecuador.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta updateCuenta(Long id, Cuenta cuenta) {
        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNotFoundException("Cuenta no encontrada con ID: " + id);
        }
        cuenta.setCuentaId(id); // importante para asegurarse que actualiza y no crea nueva
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void deleteCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNotFoundException("No se puede eliminar. Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    @Override
    public Optional<Cuenta> getCuentaById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    public List<Cuenta> getCuentasByClienteId(Long clienteId) {
        return cuentaRepository.findByCliente_Id(clienteId);
    }
}
