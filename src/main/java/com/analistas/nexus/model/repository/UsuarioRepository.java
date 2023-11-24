package com.analistas.nexus.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analistas.nexus.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByNombreOrEmail(String nombre, String email);

    Usuario findByNombre(String nombreUsuario);

    @Query("select u from Usuario u Where u.nombre like %:criterio% or u.email like %:criterio% and u.activo = true")
    List<Usuario> buscarPor(@Param("criterio") String criterio );

    @Query("select u from Usuario u Where u.activo = true")
    List<Usuario> buscarSoloHabilitados( );
}

