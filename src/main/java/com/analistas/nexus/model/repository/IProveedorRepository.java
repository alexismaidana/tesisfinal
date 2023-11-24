package com.analistas.nexus.model.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analistas.nexus.model.entities.Proveedor;

public interface IProveedorRepository extends JpaRepository<Proveedor, Long>{
    
    @Query("select p from Proveedor p Where p.razon_soc like %:criterio% or p.direccion like %:criterio% and p.activo = true")
    List<Proveedor> buscarPor(@Param("criterio") String criterio );
   
    @Query("select p from Proveedor p where  p.activo = true")
    List<Proveedor>buscarSoloHabilitados();
    
}
