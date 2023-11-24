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
import com.analistas.nexus.model.entities.Compra;
import com.analistas.nexus.model.entities.LineaCompra;
import com.analistas.nexus.model.entities.Producto;
import com.analistas.nexus.model.entities.Proveedor;
import com.analistas.nexus.model.service.ICategoriaService;
import com.analistas.nexus.model.service.ICompraService;
import com.analistas.nexus.model.service.IProductoService;
import com.analistas.nexus.model.service.IProveedorService;

import javax.validation.Valid;

@Controller
@RequestMapping("/compras")
@SessionAttributes("compra")
@Secured({ "ROLE_ADMIN", "ROLE_USER" })
public class CompraController {

    @Autowired
    IProductoService productoService;

    @Autowired
    ICompraService compraService;

    @Autowired
    IProveedorService proveedorService;

    @Autowired
    ICategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {

        model.addAttribute("titulo", "Listado de Compras");
        model.addAttribute("compras", compraService.buscarTodo());

        return "compras/list";
    }

    @GetMapping("/nuevo")
    public String nuevaCompra(Model model) {

        model.addAttribute("titulo", "Nueva Compra");
        model.addAttribute("compra", new Compra());
        model.addAttribute("proveedores", proveedorService.buscarTodo());
        model.addAttribute("categorias", categoriaService.buscarTodo());

        return "compras/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Compra compra, BindingResult result,
            @RequestParam(name = "item_id[]") List<String> itemIds,
            @RequestParam(name = "cantidad[]") List<String> cantidades,
            @RequestParam("prov") Long idProv,@RequestParam("cat") Long idCat,
            Model model, RedirectAttributes flash, SessionStatus status) {

        // Verificar los errores:
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los errores...");
            return "compras/form";
        }

        // Verificar si hay productos en la venta...
        if (itemIds == null || itemIds.size() == 1) {
            model.addAttribute("titulo", "Nueva Compra");
            model.addAttribute("warning", "Añadir productos a la Compra...");
            return "compras/form";
        }

        // Si no hay errores...
        LineaCompra linea;
        Producto producto;

        // Cargar las lineas en la venta...
        for (int i = 0; i < itemIds.size() - 1; i++) {

            linea = new LineaCompra();
            Long id = Long.parseLong(itemIds.get(i));
            int cant = Integer.parseInt(cantidades.get(i));

            producto = productoService.buscarPorId(id);

            linea.setProducto(producto);
            linea.setPrecioActual(producto.getPrecio());
            linea.setCantidad(cant);

            // No tendría que llegar acá...
            if (cant > producto.getStock()) {
                model.addAttribute("titulo", "Nueva Compra");

                return "compras/form";
            }

            // Actualizar stock del producto:
            producto.setStock(producto.getStock() + cant);

            compra.addLinea(linea);

        }
        compra.setProveedor(proveedorService.buscarPorId(idProv));
        compra.setCategoria(categoriaService.buscarPorId(idCat));
        // Guardar la venta...
        compraService.guardar(compra);
        status.setComplete();
        flash.addFlashAttribute("success", "Compra registrada...");

        return "redirect:/compras/listado";
    }

    @GetMapping("/borrar/{id}")
    public String deshabOrHabCompra(@PathVariable("id") Long id, RedirectAttributes msgFlash) {

        Compra compra = compraService.buscarPorId(id);
        compra.setActivo(!compra.isActivo());
        compraService.guardar(compra);
        msgFlash.addFlashAttribute("warning", compra.isActivo() ? "Compra Habilitado" : "Compra Deshabilitado");

        return "redirect:/compras/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {

        Compra compra = compraService.buscarPorId(id);

        model.addAttribute("titulo", "Nueva Compra");
        model.addAttribute("compra", compra);

        return "compras/form";
    }

    @GetMapping(value = "/buscar-productos/{criterio}", produces = { "application/json" })
    public @ResponseBody List<Producto> buscarProductos(@PathVariable("criterio") String texto) {
        return productoService.buscarPor(texto);
    }

    @ModelAttribute("proveedores")
    public List<Proveedor> getProveedor() {
        return proveedorService.buscarTodo();
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {
        return categoriaService.buscarTodo();
    }

}
