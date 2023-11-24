package com.analistas.nexus.model.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Cliente;
import com.analistas.nexus.model.repository.IClienteRepository;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    IClienteRepository clienteRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodo() {
        
        return clienteRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarPor(String criterio) {
       
        return clienteRepo.buscarPor(criterio);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
       
        return clienteRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        clienteRepo.save(cliente);
        
    }
    
}
