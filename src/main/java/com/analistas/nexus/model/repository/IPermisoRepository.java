package com.analistas.nexus.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analistas.nexus.model.entities.Permiso;

public interface IPermisoRepository extends JpaRepository<Permiso, Long> {
    
}
