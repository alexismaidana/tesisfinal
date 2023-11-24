package com.analistas.nexus.model.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analistas.nexus.model.entities.Cliente;

public interface IClienteRepository extends JpaRepository< Cliente, Long>{


    Cliente findByEmail(String email);

        
    @Query("select c from Cliente c Where c.nombre like %:criterio% or c.email like %:criterio% and c.activo = true")
    List<Cliente>buscarPor(@Param("criterio") String criterio);

    @Query("select c from Cliente c where c.activo = true")
    List<Cliente>buscarSoloHabilitados();

}
