package com.analistas.nexus.web.controller;



import java.security.Principal;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.entities.cambiarClave;
import com.analistas.nexus.model.service.IUsuarioService;

import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
@RequestMapping("/usuarios")
public class CambiarContraseniaController {

   
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUsuarioService usuarioService;


    @GetMapping("/nuevaClave")
    public String mostrarFormularioCambioClave(Model model) {
        model.addAttribute("titulo", "Cambiar Contraseña");
        model.addAttribute("cambiarClave", new cambiarClave()); // Agregar el objeto cambiarClave al modelo
        return "usuarios/cambioClave";
    }

    @Transactional
    @PostMapping("/cambiarContrasena")
    public String cambiarContrasena(@ModelAttribute("cambiarClave") @Valid cambiarClave cambiarClave,
            BindingResult result,
            Principal principal,
            RedirectAttributes redirectAttributes, RedirectAttributes msgFlash, SessionStatus status) {

        if (result.hasErrors()) {
            return "usuarios/cambioClave";
        }

        String nombre = principal.getName();
        System.out.println("Nombre de usuario obtenido: " + nombre);

        Usuario usuario = usuarioService.buscarPorNombre(nombre);

        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con el nombre: " + nombre);
            // Manejar el caso de que no se encuentre el usuario
            return "redirect:/error"; 
        }

        System.out.println("Usuario encontrado: " + usuario.toString());

        if (!passwordEncoder.matches(cambiarClave.getClaveActual(), usuario.getClave())) {
            result.rejectValue("claveActual", "error.claveActual", "La contraseña actual no es correcta.");
            cambiarClave.setClaveActual("");
            return "usuarios/cambioClave";
        }

        // Verificación de nulidad de la nueva contraseña
        if (cambiarClave.getNuevaclave() == null) {
            result.rejectValue("confirmarclave", "error.confirmarclave", "La nueva contraseña no puede estar vacía.");
            return "usuarios/cambioClave";
        }

        // Verificar que las contraseñas nuevas coincidan
        if (!cambiarClave.getNuevaclave().equals(cambiarClave.getConfirmarclave())) {
            result.rejectValue("confirmarclave", "error.confirmarclave", "Las contraseñas nuevas no coinciden.");
            return "usuarios/cambioClave";
        }

        String nuevaClaveEncriptada = passwordEncoder.encode(cambiarClave.getNuevaclave());
        usuario.setClave(nuevaClaveEncriptada);
        System.out.println("nueva clave: " + nuevaClaveEncriptada);
        usuarioService.guardar(usuario);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(usuario.getEmail()); 
        mailMessage.setSubject("Cambio de contraseña exitoso");
        mailMessage.setText("Su contraseña ha sido cambiada exitosamente");
        mailSender.send(mailMessage);

        msgFlash.addFlashAttribute("success", "Contraseña cambiada correctamente.");
        status.setComplete();

        return "redirect:/home";
    }

}

