package com.analistas.nexus.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analistas.nexus.model.entities.Compra;



public interface ICompraRepository extends JpaRepository<Compra, Long> {

    @Query("select p from Proveedor p Where p.razon_soc like %:criterio% or p.direccion like %:criterio% and p.activo = true")
    List<Compra> buscarPor(@Param("criterio") String criterio );

    
    @Query("select c from Compra c Where c.activo = true")
    List<Compra> buscarSoloHabilitados( );


    @Query("SELECT MAX(c.id) FROM Compra c")
    Long findMaxId();
    
}
