package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Compra;

public interface ICompraService {

    public List<Compra> buscarTodo();

    public Compra buscarPorId(Long id);

    public List<Compra> buscarPor(String criterio);
    
    public void guardar(Compra compra);
    
}
