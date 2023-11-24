package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Producto;
import com.analistas.nexus.model.repository.IProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService{

    //Inyeccion de Dependencias (dependece Injection - DI)
    @Autowired
    IProductoRepository productoRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarTodo() {
    
        return productoRepo.buscarSoloHabilitados();     }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPor(String criterio) {
        
        return productoRepo.buscarPor(criterio);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto buscarPorId(Long id) {
        
        return productoRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Producto producto) {
       productoRepo.save(producto);
    }
    

}
