package com.analistas.nexus.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analistas.nexus.model.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("select c from Categoria c Where c.nombre like %:criterio%  and c.activo = true")
    List<Categoria> buscarPor(@Param("criterio") String criterio );

    @Query("select c from Categoria c Where c.activo = true")
    List<Categoria> buscarSoloHabilitados( );
    
}
