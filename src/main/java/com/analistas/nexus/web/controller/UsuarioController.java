package com.analistas.nexus.web.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.analistas.nexus.model.entities.Permiso;
import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.service.IPermisoService;
import com.analistas.nexus.model.service.IUsuarioService;

import javax.validation.Valid;

@Controller
@RequestMapping("/usuarios")
@SessionAttributes("usuario")
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class UsuarioController {

    @Autowired
     IUsuarioService usuarioService;

    @Autowired
    IPermisoService permisoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/listado")
    public String listado(Model model) {

        model.addAttribute("titulo", "Listado de Usuarios");
        model.addAttribute("usuarios", usuarioService.buscarTodos());

        return "usuarios/list";

    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("permisos", permisoService.buscarTodo());

        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult result, @RequestParam("rol") Long idRol,
            Model model, RedirectAttributes msgFlash, SessionStatus status) {

        // Verificar si hay errores
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los Errores...");
            return "usuarios/form";
        }

        usuario.setPermiso(permisoService.buscarPorId(idRol));
        
        //if(usuario.getId() == 0)
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        
            usuarioService.guardar(usuario);

        msgFlash.addFlashAttribute("success", "Usuario Registrado Correctamente.");
        status.setComplete();

        return "redirect:/usuarios/listado";
    }

    @GetMapping("/borrar/{id}")
    public String deshabOrHabUsuario(@PathVariable("id") Long id, RedirectAttributes msgFlash) {

        Usuario usuario = usuarioService.buscarPorId(id);
        usuario.setActivo(!usuario.isActivo());
        usuarioService.guardar(usuario);
        msgFlash.addFlashAttribute("warning", usuario.isActivo() ? "Usuario Habilitado" : "Usuario Deshabilitado");

        return "redirect:/usuarios/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {

        Usuario usuario = usuarioService.buscarPorId(id);

        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", usuario);

        return "usuarios/form";
    }

    @ModelAttribute("permisos")
    public List<Permiso> getPermisos() {
        return permisoService.buscarTodo();
    }

}
