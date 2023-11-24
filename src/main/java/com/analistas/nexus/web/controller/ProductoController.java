package com.analistas.nexus.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.analistas.nexus.model.entities.Categoria;
import com.analistas.nexus.model.entities.Producto;
import com.analistas.nexus.model.entities.Proveedor;
import com.analistas.nexus.model.service.ICategoriaService;
import com.analistas.nexus.model.service.IProductoService;
import com.analistas.nexus.model.service.IProveedorService;

import javax.validation.Valid;

@Controller
@RequestMapping("/productos")
@SessionAttributes("producto")
@Secured({ "ROLE_ADMIN", "ROLE_USER" })
public class ProductoController {

    @Autowired
    IProductoService productoService;

    @Autowired
    ICategoriaService categoriaService;

    @Autowired
    IProveedorService proveedorService;

    @GetMapping("/listado")
    public String listado(Model model) {

        model.addAttribute("titulo", "Listado de Productos");
        model.addAttribute("productos", productoService.buscarTodo());

        return "productos/list";

    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("titulo", "Nuevo Producto");
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.buscarTodo());
        model.addAttribute("proveedores", proveedorService.buscarTodo());

        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Producto producto, BindingResult result, @RequestParam("cate") Long idCate,
            @RequestParam("provee") Long idProvee,
            Model model, RedirectAttributes msgFlash, SessionStatus status) {

        // Verificar si hay errores
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los Errores...");
            return "productos/form";
        }

        producto.setCategoria(categoriaService.buscarPorId(idCate));
        producto.setProveedor(proveedorService.buscarPorId(idProvee));
        productoService.guardar(producto);

        msgFlash.addFlashAttribute("success", "Producto Guardado Correctamente.");
        status.setComplete();

        return "redirect:/productos/listado";
    }

    @GetMapping("/borrar/{id}")
    public String deshabOrHabProducto(@PathVariable("id") Long id, RedirectAttributes msgFlash) {

        Producto producto = productoService.buscarPorId(id);
        producto.setActivo(!producto.isActivo());
        productoService.guardar(producto);
        msgFlash.addFlashAttribute("warning", producto.isActivo() ? "Producto Habilitado" : "Producto Deshabilitado");

        return "redirect:/productos/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {

        Producto producto = productoService.buscarPorId(id);

        model.addAttribute("titulo", "Nuevo Producto");
        model.addAttribute("producto", producto);

        return "productos/form";
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {
        return categoriaService.buscarTodo();
    }

    @ModelAttribute("proveedores")
    public List<Proveedor> getProveedor() {
        return proveedorService.buscarTodo();
    }

    // @GetMapping("/nuevo2")
    // public String nuevo2(Model model) {

    //     model.addAttribute("titulo", "Nuevo Producto");
    //     model.addAttribute("producto", new Producto());
    //     model.addAttribute("categorias", categoriaService.buscarTodo());

    //     return "productos/form2";
    // }

    // @PostMapping("/guardar2")
    // public String guardar2(@Valid Producto producto, BindingResult result, @RequestParam("cat") Long idCat,
    //         Model model, RedirectAttributes msgFlash, SessionStatus status) {

    //     // Verificar si hay errores
    //     if (result.hasErrors()) {
    //         model.addAttribute("danger", "Corrija los Errores...");
    //         return "productos/form2";
    //     }

    //     producto.setCategoria(categoriaService.buscarPorId(idCat));
    //     productoService.guardar(producto);

    //     msgFlash.addFlashAttribute("success", "Producto Guardado Correctamente.");
    //     status.setComplete();

    //     return "redirect:/compras/nuevo";
    // }

    @GetMapping("/generarCodigo")
    @ResponseBody
    public String generarCodigo() {
        // Consulta la base de datos para obtener el último número utilizado
        String ultimoCodigo = productoService.obtenerUltimoCodigo();

        // Si no hay ningún código previo, comienza desde 1, de lo contrario, incrementa el número
        int numeroIncremental = 1;
        if (ultimoCodigo != null && ultimoCodigo.matches("NEXUS(\\d+)")) {
            numeroIncremental = Integer.parseInt(ultimoCodigo.substring(5)) + 1;
        }

        // Formatea el número con ceros a la izquierda según su longitud
        String numeroFormateado = String.format("%03d", numeroIncremental); // "%03d" asegura que siempre haya al menos 3 dígitos

        // Genera el nuevo código sin el guion "-"
        String nuevoCodigo = "NEXUS" + numeroFormateado;

        return nuevoCodigo;
    }

}
