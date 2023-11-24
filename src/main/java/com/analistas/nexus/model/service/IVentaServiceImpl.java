package com.analistas.nexus.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.analistas.nexus.model.entities.Venta;
import com.analistas.nexus.model.repository.IVentaRepositoy;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
public class IVentaServiceImpl implements IVentaService {

    @Autowired
    IVentaRepositoy ventaRepository;

    @Override
    @Transactional
    public List<Venta> buscarTodo() {

        return ventaRepository.buscarSoloHabilitados();
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

    @Override
    public Long obtenerUltimoIdVenta() {
        return ventaRepository.findMaxId();
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

    @Override
    public Object buscarSoloHabilitados() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarSoloHabilitados'");
    }

}
