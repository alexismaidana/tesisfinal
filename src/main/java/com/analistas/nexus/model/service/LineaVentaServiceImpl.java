package com.analistas.nexus.model.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.LineaVenta;
import com.analistas.nexus.model.repository.ILineaVentaRepository;

@Service
public class LineaVentaServiceImpl implements ILineaVentaService {
    
    @Autowired
    ILineaVentaRepository lineaVentaRepo;

    @Override
    @Transactional(readOnly = true)
    public List<LineaVenta> buscarPor(String criterio) {

        return lineaVentaRepo.buscarPor(criterio);
    }

    @Override
    @Transactional(readOnly = true)
    public LineaVenta buscarPorId(Long id) {

        return lineaVentaRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaVenta> buscarTodo() {

        return lineaVentaRepo.buscarSoloHabilitados();
    }

    @Override
    @Transactional
    public void guardar(LineaVenta lineaVenta) {

        lineaVentaRepo.save(lineaVenta);
    }   

}
