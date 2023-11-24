package com.analistas.nexus.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analistas.nexus.model.entities.Sucursal;

public interface ISucursalRepository extends JpaRepository<Sucursal, Long>{
    
}
