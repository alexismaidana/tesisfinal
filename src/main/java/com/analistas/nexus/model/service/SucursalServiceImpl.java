package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analistas.nexus.model.entities.Sucursal;
import com.analistas.nexus.model.repository.ISucursalRepository;


@Service
public class SucursalServiceImpl  implements ISucursalService{

    @Autowired
    ISucursalRepository sucursalRepository;

    @Override
    public void guardar(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    @Override
    public List<Sucursal> buscarTodos() {
        return sucursalRepository.findAll();
    }

    @Override
    public List<Sucursal> buscarPor(String criterio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPor'");
    }

    @Override
    public Sucursal buscarPorId(Long id) {
       return sucursalRepository.findById(id).orElse(null);
    }
    
}
