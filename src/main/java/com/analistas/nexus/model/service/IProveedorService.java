package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Proveedor;


public interface IProveedorService {
    
    public List<Proveedor> buscarTodo();

    public List<Proveedor> buscarPor(String criterio);

    public Proveedor buscarPorId(Long id);
    
    public void guardar(Proveedor proveedores);

}
