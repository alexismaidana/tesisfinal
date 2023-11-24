package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Permiso;

public interface IPermisoService {
    
    public List<Permiso> buscarTodo();

    public List<Permiso> buscarPor(String criterio);

    public Permiso buscarPorId(Long id);
    
    public void guardar(Permiso permiso);
}
