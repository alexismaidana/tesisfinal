package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Producto;
import com.analistas.nexus.model.repository.IProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService {

    // Inyeccion de Dependencias (dependece Injection - DI)
    @Autowired
    IProductoRepository productoRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarTodo() {

        return productoRepo.buscarSoloHabilitados();
    }

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

   
    @Override
    public Producto buscarPorCodigoBarras(String codigoBarras) {
        List<Producto> productos = productoRepo.findByCodigoBarras(codigoBarras);

        if (!productos.isEmpty()) {
            return productos.get(0); 
        } else {
            return null; 
        }
    }

    @Override
    public String obtenerUltimoCodigo() {
        List<Producto> listaProductos = productoRepo.findAll();
    
        // Verifica si la lista de productos no está vacía
        if (!listaProductos.isEmpty()) {
            // Ordena la lista de productos por ID de forma descendente
            listaProductos.sort((p1, p2) -> p2.getId().compareTo(p1.getId()));
    
            // Obtén el primer producto de la lista (que tiene el ID más alto)
            Producto ultimoProducto = listaProductos.get(0);

            System.out.println("Último producto con ID mayor: " + ultimoProducto);
    
            // Obtén el codigoIdentificacion del último producto y devuélvelo
            return ultimoProducto.getCodigoBarras();
        }
    
        // Si la lista de productos está vacía, puedes manejarlo de acuerdo a tus necesidades
        // Puedes devolver un valor predeterminado, lanzar una excepción, etc.
        return "NEXUS-1"; // Por ejemplo, si no hay productos, se devuelve "PAISA-1"
    }    
   

}
