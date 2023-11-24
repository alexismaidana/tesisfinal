package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Compra;
import com.analistas.nexus.model.repository.ICompraRepository;

@Service
public class CompraServiceImpl implements ICompraService{

    @Autowired
    ICompraRepository compraRepository;

    @Override
    public List<Compra> buscarTodo() {
        return compraRepository.findAll();
    }

    @Override
    public Compra buscarPorId(Long id) {

        return compraRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> buscarPor(String criterio) {
       
        return compraRepository.buscarPor(criterio);

    }

    @Override
    @Transactional
    public void guardar(Compra compra) {
        compraRepository.save(compra);
    }
    
}
