package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Proveedor;
import com.analistas.nexus.model.repository.IProveedorRepository;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    IProveedorRepository proveedorRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> buscarTodo() {
        return proveedorRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> buscarPor(String criterio) {
        return proveedorRepo.buscarPor(criterio);
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor buscarPorId(Long id) {
        return proveedorRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Proveedor proveedor) {
       proveedorRepo.save(proveedor);
    }

}
