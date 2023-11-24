package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Categoria;
import com.analistas.nexus.model.repository.ICategoriaRepository;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

    @Autowired
    ICategoriaRepository categoriaRespository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> buscarTodo() {
        
        return categoriaRespository.findAll();
    }

    @Override
    public List<Categoria> buscarPor(String criterio) {
       
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
       
        return categoriaRespository.findById(id).orElse(null);
    }

    @Override
    public void guardar(Categoria categoria) {
        categoriaRespository.save(categoria);
        
    }

    
}