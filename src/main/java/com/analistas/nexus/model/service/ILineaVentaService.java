package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.LineaVenta;

public interface ILineaVentaService {

    public List<LineaVenta> buscarTodo();

    public List<LineaVenta> buscarPor(String criterio);

    public LineaVenta buscarPorId(Long id);

    public void guardar(LineaVenta LineaVenta);

}

