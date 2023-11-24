package com.analistas.nexus.model.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analistas.nexus.model.entities.Compra;
import com.analistas.nexus.model.repository.ICompraRepository;



@Service
public class CompraServiceImpl implements ICompraService {

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

    @Override
    public Long obtenerUltimoIdCompra() {
        return compraRepository.findMaxId();
    }

    private String obtenerRolUsuarioDesdeSesion(HttpServletRequest request) {
        // Obtener el objeto Authentication del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si el usuario está autenticado y obtener su rol
        if (authentication != null && authentication.isAuthenticated()) {
            // Puedes adaptar esta lógica según cómo hayas configurado Spring Security
            String rolUsuario = authentication.getAuthorities().iterator().next().getAuthority();
            return rolUsuario;
        }

        // Si el usuario no está autenticado o no tiene un rol válido, puedes devolver
        // un valor predeterminado o manejar el caso según tus necesidades.
        return "ROLE_DEFAULT"; // Por ejemplo, un rol predeterminado si no hay autenticación
    }


}
