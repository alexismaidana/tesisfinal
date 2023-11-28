package com.analistas.nexus.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.analistas.nexus.model.entities.Sucursal;
import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.service.IPermisoService;
import com.analistas.nexus.model.service.ISucursalService;
import com.analistas.nexus.model.service.IUsuarioService;

import javax.validation.Valid;

@Controller
@RequestMapping("/usuarios")
@SessionAttributes("usuario")
@Secured({ "ROLE_ADMIN", "ROLE_USER" })
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IPermisoService permisoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ISucursalService sucursalService;

    @Autowired
    private JavaMailSender mailSender;

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
        model.addAttribute("sucursales", sucursalService.buscarTodos());


        return "usuarios/form";
    }

    // @PostMapping("/guardar")
    // public String guardar(@Valid Usuario usuario, BindingResult result, @RequestParam("rol") Long idRol,
    //         Model model, RedirectAttributes msgFlash, SessionStatus status) {

    //     // Verificar si hay errores de validación
    //     if (result.hasErrors()) {
    //         model.addAttribute("danger", "Corrija los Errores...");
    //         return "usuarios/form";
    //     }

    //     // Verificar si el objeto Usuario es nuevo (sin id) o existente (con id)
    //     if (usuario.getId() == null) {
    //         usuario.setClave(passwordEncoder.encode(usuario.getClave()));
    //     }

    //     usuario.setPermiso(permisoService.buscarPorId(idRol));
    //     usuarioService.guardar(usuario);

    //     SimpleMailMessage mailMessage = new SimpleMailMessage();
    //     mailMessage.setTo(usuario.getEmail());
    //     mailMessage.setSubject("Usuario Generado - Nexus Informatica tu conección con la tecnologia");
    //     String messageText = "Al Señor " + usuario.getNombre() + "\n\n"
    //             + "Nos comunicamos desde la Admisnitración de Nexus Informatica, a fin de informarle que se ha  generado un usuario en el sistema de Admisión y Cupo del Servicio Penitenciario de la Provincia del Chaco. Para ingresar al sistema, siga estos pasos:\n\n"
    //             + "1. Ingrese al siguiente enlace que lo llevará al Sistema de Admisión y Cupo: [nexusInformatica.com.ar/login]\n"
    //             + "2. En el campo de usuario, ingrese su Nombre y Apellido, todo en minúsculas y sin espacios.\n"
    //             + "3. La contraseña por defecto es su Número de Documento sin puntos ni espacios.\n\n"
    //             + "Saludos cordiales,\n"
    //             + "Nexus Informatica.";
    //     mailMessage.setText(messageText);
    //     mailSender.send(mailMessage);

    //     msgFlash.addFlashAttribute("success", "Usuario Registrado Correctamente.");
    //     status.setComplete();

    //     return "redirect:/usuarios/listado";
    // }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult result, @RequestParam("permiso") Long idPermiso,
            @RequestParam("sucursalAsignada") Long idSucursal, Model model, RedirectAttributes msgFlash, SessionStatus status) {

        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los Errores...");
            return "usuarios/form";
        }

        if (usuario.getId() == null) {
            // Esto es para nuevos usuarios
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        } else {
            // Esto es para usuarios existentes
            Usuario usuarioExistente = usuarioService.buscarPorId(usuario.getId());
            if (usuarioExistente != null) {
                usuarioExistente.setNombre(usuario.getNombre());
                usuario = usuarioExistente;
            } else {
                model.addAttribute("danger", "Usuario no encontrado");
                return "usuarios/form";
            }
        }

        // Asignar el permiso al usuario
        Permiso permisoEmpleado = permisoService.buscarPorId(idPermiso);
        if (permisoEmpleado != null) {
            usuario.setPermiso(permisoEmpleado);
        } else {
            model.addAttribute("danger", "Permiso no encontrado");
            return "usuarios/form";
        }

        // Asignar la sucursal al usuario
        Sucursal sucursalUno = sucursalService.buscarPorId(idSucursal);
        if (sucursalUno != null) {
            usuario.setSucursalAsignada(sucursalUno);
        } else {
            model.addAttribute("danger", "Sucursal no encontrada");
            return "usuarios/form";
        }

        usuarioService.guardar(usuario);
         SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(usuario.getEmail());
        mailMessage.setSubject("Usuario Generado - Nexus Informatica tu conección con la tecnologia");
        String messageText = "Al Señor " + usuario.getNombre() + "\n\n"
                + "Nos comunicamos desde la Admisnitración de Nexus Informatica, a fin de informarle que se ha generado un usuario en el sistema en el cual tendrá beneficios y descuentos exclusivos. Para ingresar al sistema, siga estos pasos:\n\n"
                + "1. Ingrese al siguiente enlace que lo llevará al Sistema de Admisión y Cupo: [nexusInformatica.com.ar/login]\n"
                + "2. En el campo de usuario, ingrese su USUARIO generado, todo en minúsculas y sin espacios.\n"
                + "3. La contraseña por es la que eligio en nuestra web, sin puntos ni espacios.\n\n"
                + "Saludos cordiales,\n"
                + "Nexus Informatica.";
        mailMessage.setText(messageText);
        mailSender.send(mailMessage);

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

     @ModelAttribute("sucursales")
    public List<Sucursal> getSucursales(){
        return sucursalService.buscarTodos();
    }
}
