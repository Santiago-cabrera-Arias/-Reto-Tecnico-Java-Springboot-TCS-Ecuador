package com.tcs.reto.reto_tcs_ecuador.repository;

import com.tcs.reto.reto_tcs_ecuador.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
