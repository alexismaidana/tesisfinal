package com.analistas.nexus.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.analistas.nexus.model.entities.LineaVenta;
import com.analistas.nexus.model.entities.Producto;
import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.entities.Venta;
import com.analistas.nexus.model.service.IProductoService;
import com.analistas.nexus.model.service.IUsuarioService;
import com.analistas.nexus.model.service.IVentaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/ventas")
@SessionAttributes("venta")
@Secured({ "ROLE_ADMIN", "ROLE_USER" })
public class ventasController {

    @Autowired
    IProductoService productoService;

    @Autowired
    IVentaService ventaService;

    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("titulo", "Listado de Ventas");
        List<Venta> ventas = ventaService.buscarTodo();
        model.addAttribute("ventas", ventas);
        return "ventas/list";
    }

    @GetMapping("/nuevo")
    public String nuevaVenta(Model model) {

        model.addAttribute("titulo", "Nueva Venta");
        model.addAttribute("venta", new Venta());

        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Venta venta, BindingResult result,
            @RequestParam(name = "item_id[]") List<String> itemIds,
            @RequestParam(name = "cantidad[]") List<String> cantidades,
            Model model, RedirectAttributes flash, SessionStatus status) {

        // Verificar los errores:
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los errores...");
            return "ventas/form";
        }

        // Verificar si hay productos en la venta...
        if (itemIds == null || itemIds.size() == 1) {
            model.addAttribute("titulo", "Nueva Venta");
            model.addAttribute("warning", "Añadir productos a la venta...");
            return "ventas/form";
        }

        // Si no hay errores...
        LineaVenta linea;
        Producto producto;
        Long sucursalUsuario = obtenerSucursalUsuario();

        // Cargar las lineas en la venta...
        for (int i = 0; i < itemIds.size() - 1; i++) {

            linea = new LineaVenta();
            Long id = Long.parseLong(itemIds.get(i));
            int cant = Integer.parseInt(cantidades.get(i));

            producto = productoService.buscarPorId(id);

            linea.setProducto(producto);
            linea.setPrecioActual(producto.getPrecio());
            linea.setCantidad(cant);

            // No tendría que llegar acá...
            if (cant > producto.getStockSucursalUno() || cant > producto.getStockSucursalDos()) {
                model.addAttribute("titulo", "Nueva Venta");
                model.addAttribute("danger", "No hay stock suficiente...");
                return "ventas/form";
            }

                       

            // Actualizar stock de la sucursal y stock general:
            if (sucursalUsuario == 1) {
                producto.setStockSucursalUno(producto.getStockSucursalUno() - cant);
            } else if (sucursalUsuario == 2) {
                producto.setStockSucursalDos(producto.getStockSucursalDos() - cant);
            }

            venta.addLinea(linea);

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.buscarPorNombre(nombreUsuario);
        venta.setUsuario(usuario);

        // Guardar la venta...
        ventaService.guardar(venta);
        status.setComplete();
        flash.addFlashAttribute("success", "Registrado con exito...");

        return "redirect:/ventas/listado";
    }

    @GetMapping(value = "/buscar-productos/{criterio}", produces = { "application/json" })
    @ResponseBody
    public List<Producto> buscarProductos(@PathVariable("criterio") String texto) {
        System.out.println("Recibiendo solicitud de búsqueda con criterio: " + texto);

        List<Producto> productos = productoService.buscarPor(texto);

        System.out.println("Productos encontrados:");
        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getDescripcion()); // Ajusta esto
        }

        return productos;
    }

    @GetMapping(value = "/obtener-rol-usuario", produces = { "application/json" })
    public @ResponseBody String obtenerRolUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String rolUsuario = authentication.getAuthorities().iterator().next().getAuthority();
            return "{\"rol\":\"" + rolUsuario + "\"}";
        }
        return "{\"rol\":\"ROLE_DEFAULT\"}";
    }

    @GetMapping(value = "/obtener-sucursal-usuario", produces = { "application/json" })
    public @ResponseBody Long obtenerSucursalUsuario() {
        Long sucursalUsuario = usuarioService.obtenerSucursalAsignada(); // Reemplaza con la lógica para obtener la sucursal real
        
        return sucursalUsuario;
    }


    @GetMapping("/borrar/{id}")
    public String deshabOrHabVenta(@PathVariable("id") Long id, RedirectAttributes msgFlash) {

        Venta venta = ventaService.buscarPorId(id);
        System.out.printf("#### ----- " + venta.getId() + " ----- ####");
        venta.setActivo(!venta.isActivo());
        ventaService.guardar(venta);
        msgFlash.addFlashAttribute("warning", venta.isActivo()
                ? "Venta Habilitada"
                : "Se eliminó la venta #" + venta.getId());

        return "redirect:/ventas/listado";
    }

    @GetMapping("/generar-numero-venta")
    public ResponseEntity<?> generarNumeroVenta() {
        Long ultimoIdVenta = ventaService.obtenerUltimoIdVenta();
        Long numeroVenta = (ultimoIdVenta != null) ? ultimoIdVenta + 1 : 1;

        return ResponseEntity.ok("{\"numeroVenta\":" + numeroVenta + "}");
    }

    @GetMapping("/detalle/{id}")
    public String detalleVenta(@PathVariable Long id, Model model) {
        Venta venta = ventaService.buscarPorId(id);

        if (venta == null) {
            return "redirect:/ventas/listado";
        }

        model.addAttribute("titulo", "Detalle de Venta #" + id);
        model.addAttribute("venta", venta);
        return "ventas/detalle";
    }

}
