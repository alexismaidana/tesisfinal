package com.analistas.nexus.model.service;

import java.util.List;

import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.entities.Venta;




public interface IVentaService {
    
    public List<Venta> buscarTodo();

    public Venta buscarPorId(Long id);
    
    public void guardar(Venta venta);

    Long obtenerUltimoIdVenta();

    public Object buscarSoloHabilitados();

    public Usuario buscarCajero(String nombre);

    // Buscar venta por usuarios //
    List<Venta> findByUsuario(Usuario usuario);

    
}
