package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Sucursal;

public interface ISucursalService {

    public void guardar (Sucursal sucursal);
    
    public List<Sucursal> buscarTodos();

    public List<Sucursal> buscarPor(String criterio);

    public Sucursal buscarPorId(Long id);
    
}
