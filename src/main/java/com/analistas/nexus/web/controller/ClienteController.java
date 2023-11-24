package com.analistas.nexus.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.analistas.nexus.model.entities.Cliente;
import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.service.IClienteService;
import com.analistas.nexus.model.service.IPermisoService;
import com.analistas.nexus.model.service.IUsuarioService;

@Controller
@RequestMapping("/clientes")
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    IClienteService clienteService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IPermisoService permisoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/listado")
    public String listado(Model model) {

        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clienteService.buscarTodo());

        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("titulo", "Registrarte.");
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("permisos", usuarioService.buscarTodos());

        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Cliente cliente, BindingResult result,
            Model model, RedirectAttributes msgFlash, SessionStatus status) {

        // Verificar si hay errores
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los Errores...");
            System.out.println("Corrija los Errores...");
            return "clientes/form";
        }
        // Se registra el cliente
        clienteService.guardar(cliente);

        // Se definen los atributos del usuario en base al cliente
        Long permiso_id = 3L;
        Usuario usuario = new Usuario();
        usuario.setNombre(cliente.getNombre());
        usuario.setClave(passwordEncoder.encode(cliente.getDni())); // Se define el dni como contrasena
        usuario.setEmail(cliente.getEmail());
        usuario.setPermiso(permisoService.buscarPorId(permiso_id)); // Se le asigna el permiso de cliente

        // Se registra el usuario
        usuarioService.guardar(usuario);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(usuario.getEmail());
        mailMessage.setSubject("Usuario Generado - Nexus Informatica tu conección con la tecnologia");
        String messageText = "Al Señor " + usuario.getNombre() + "\n\n"
                + "Nos comunicamos desde la Admisnitración de Nexus Informatica, a fin de informarle que se ha generado un usuario en el sistema de Admisión y Cupo del Servicio Penitenciario de la Provincia del Chaco. Para ingresar al sistema, siga estos pasos:\n\n"
                + "1. Ingrese al siguiente enlace que lo llevará al Sistema de Admisión y Cupo: [nexusInformatica.com.ar/login]\n"
                + "2. En el campo de usuario, ingrese su Nombre y Apellido, todo en minúsculas y sin espacios.\n"
                + "3. La contraseña por defecto es su Número de Documento sin puntos ni espacios.\n\n"
                + "Saludos cordiales,\n"
                + "Nexus Informatica.";
        mailMessage.setText(messageText);
        mailSender.send(mailMessage);

        msgFlash.addFlashAttribute("success", "Usuario Registrado Correctamente.");
        status.setComplete();

        return "redirect:/home";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/borrar/{id}")
    public String deshabOrHabProducto(@PathVariable("id") Long id, RedirectAttributes msgFlash) {

        Cliente cliente = clienteService.buscarPorId(id);
        cliente.setActivo(!cliente.isActivo());
        clienteService.guardar(cliente);
        ;
        msgFlash.addFlashAttribute("warning", cliente.isActivo() ? "Cliente Habilitado" : "Cliente Deshabilitado");

        return "redirect:/clientes/listado";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {

        Cliente cliente = clienteService.buscarPorId(id);

        model.addAttribute("titulo", "Nuevo Cliente");
        model.addAttribute("cliente", cliente);

        return "clientes/form";
    }

}
