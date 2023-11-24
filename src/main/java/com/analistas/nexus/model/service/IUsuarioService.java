package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Usuario;

public interface IUsuarioService {

    Usuario findByNombreOrEmail(String nombre, String email);
   
    public void guardar (Usuario usuario);
    
    public List<Usuario> buscarTodos();

    public List<Usuario> buscarPor(String criterio);

    public Usuario buscarPorId(Long id);

    public Usuario buscarPorNombre(String nombreUsuario);

    public Long obtenerSucursalAsignada();
}
