package com.analistas.nexus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.analistas.nexus.model.service.ICategoriaService;
import com.analistas.nexus.model.service.IProductoService;

@Controller
public class HomeControler {

    @Autowired
    IProductoService productoService;
    @Autowired
    ICategoriaService categoriaService;

    @GetMapping({ "/", "/home" })
    public String home(Model model) {

        model.addAttribute("titulo", "Nexus Informatica");
        model.addAttribute("productos", productoService.buscarTodo());
        model.addAttribute("categorias", categoriaService.buscarTodo());

        return "home";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {

        model.addAttribute("titulo", "Nexus Informatica");

        return "contacto";
    }

    @GetMapping("/conocenos")
    public String conocenos(Model model) {

        model.addAttribute("titulo", "Nexus Informatica");

        return "conocenos";
    }

    @GetMapping("/detalleProducto")
    public String detalleProducto(Model model) {

        model.addAttribute("titulo", "Nexus Informatica");

        return "detalleProducto";
    }
}
