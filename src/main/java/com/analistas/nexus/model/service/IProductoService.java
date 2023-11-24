package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Producto;

public interface IProductoService {
    
    public List<Producto> buscarTodo();

    public List<Producto> buscarPor(String criterio);

    public Producto buscarPorId(Long id);

    public void guardar (Producto producto);
    
}
