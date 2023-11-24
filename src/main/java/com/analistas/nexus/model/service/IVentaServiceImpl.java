package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analistas.nexus.model.entities.Venta;
import com.analistas.nexus.model.repository.IVentaRepositoy;

import javax.transaction.Transactional;

@Service
public class IVentaServiceImpl implements IVentaService {

 
    @Autowired
    IVentaRepositoy ventaRepository;

    @Override
    @Transactional
    public List<Venta> buscarTodo() {
        
        return ventaRepository.findAll();
    }

    @Override
    @Transactional
    public Venta buscarPorId(Long id) {
        
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Venta venta) {
        ventaRepository.save(venta);        
    }

}
