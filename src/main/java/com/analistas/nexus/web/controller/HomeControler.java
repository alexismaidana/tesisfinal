package com.analistas.nexus.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.analistas.nexus.model.entities.LineaVenta;
import com.analistas.nexus.model.entities.Permiso;
import com.analistas.nexus.model.entities.Producto;
import com.analistas.nexus.model.entities.Sucursal;
import com.analistas.nexus.model.entities.Usuario;
import com.analistas.nexus.model.entities.Venta;
import com.analistas.nexus.model.service.ICategoriaService;
import com.analistas.nexus.model.service.IClienteService;
import com.analistas.nexus.model.service.ILineaVentaService;
import com.analistas.nexus.model.service.IPermisoService;
import com.analistas.nexus.model.service.IProductoService;
import com.analistas.nexus.model.service.ISucursalService;
import com.analistas.nexus.model.service.IUsuarioService;
import com.analistas.nexus.model.service.IVentaService;

@Controller
public class HomeControler {

    private final Logger log = LoggerFactory.getLogger(HomeControler.class);

    @Autowired
    IProductoService productoService;
    @Autowired
    ICategoriaService categoriaService;
    @Autowired
    ILineaVentaService lineaVentaService;
    @Autowired
    IUsuarioService usuarioService;
    @Autowired
    IVentaService ventaService;
    @Autowired
    IClienteService clienteService;
    @Autowired
    IPermisoService permisoService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ISucursalService sucursalService;
    @Autowired
    private JavaMailSender mailSender;

    // Para almacenar las lineas de la venta Online
    List<LineaVenta> lineaVenta = new ArrayList<LineaVenta>();

    // Datos de la venta Online
    Venta venta = new Venta();

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

    @GetMapping("/detalleProducto/{id}")
    // public String detalleProducto(Model model) {
    public String detalleProducto(@PathVariable("id") Long id, Integer cantidad, Model model) {

        // model.addAttribute("titulo", "Nexus Informatica");

        // Detalle Productos//
        model.addAttribute("titulo", "Descripcion del Producto");
        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto", producto);

        return "detalleProducto";
    }

    // public String detalleProducto(Model model) {
    // model.addAttribute("titulo", "Nexus Informatica");
    // return "detalleProducto";
    // }

    // Vista Productos agregados al carrito - Datos de la venta Online
    @PostMapping("/carrito")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String addCarrito(@RequestParam Long id, @RequestParam Integer cant,
            Model model) {

        model.addAttribute("titulo", "Carrito de Compras");
        LineaVenta linea = new LineaVenta();
        Producto producto = new Producto();
        double calcularTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cant);
        producto = optionalProducto.get();

        linea.setCantidad(cant);
        linea.setPrecioActual(producto.getPrecio());
        linea.setProducto(producto);

        // validar que le producto no se añada 2 veces
        // Long idproducto = producto.getId();
        // boolean ingresado = lineaVenta.stream().anyMatch(prod ->
        // prod.getProducto().getId() == idproducto);

        // if (!ingresado) {
        // Agregar linea a carrito
        // lineaVenta.add(linea);
        // }
        // else {
        // linea.setCantidad(linea.getCantidad() + cant);
        // lineaVentaService.guardar(linea);
        // log.info("Producto Repetido: {}", optionalProducto.get());
        // log.info("Cantidad: {}", cant);
        // }
        lineaVenta.add(linea);

        // linea.setCantidad(linea.getCantidad() + cant);
        // linea.setCantidad(cant);

        // Actualizar stock de producto:
        producto.setStockGeneral(producto.getStockGeneral() - cant);
        productoService.guardar(producto);
        log.info("Producto Stock: {}", producto.getStockGeneral());

        // Calcular total de la venta
        calcularTotal = lineaVenta.stream().mapToDouble(lineas -> lineas.calcularSubtotal()).sum();

        venta.setTotal(calcularTotal);
        model.addAttribute("carrito", lineaVenta);
        model.addAttribute("venta", venta);

        return "carrito";
    }

    // Ver productos actual en carrito
    @GetMapping("/getCarrito")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String getCarrito(Model model, HttpSession session) {
        model.addAttribute("titulo", "Carrito de Compras");
        model.addAttribute("carrito", lineaVenta);
        model.addAttribute("venta", venta);

        // sesion
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "/carrito";
    }

    // Eliminar un producto del carrito
    @GetMapping("/delete/carrito/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String deleteProductoCarrito(@PathVariable Long id, Model model) {

        // lista nueva de prodcutos
        List<LineaVenta> ordenesNueva = new ArrayList<LineaVenta>();

        for (LineaVenta lineaVenta : lineaVenta) {
            if (lineaVenta.getProducto().getId() != id) {
                ordenesNueva.add(lineaVenta);

            }
        }

        // Lista con los productos actualizados
        lineaVenta = ordenesNueva;

        double calcularTotal = 0;
        calcularTotal = lineaVenta.stream().mapToDouble(lineas -> lineas.calcularSubtotal()).sum();

        venta.setTotal(calcularTotal);
        model.addAttribute("carrito", lineaVenta);
        model.addAttribute("venta", venta);

        return "Carrito";
    }

    @GetMapping("/resumen")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String resumen(Model model, HttpSession session, Principal principal) {

        Usuario usuario = ventaService.buscarCajero(principal.getName());

        model.addAttribute("carrito", lineaVenta);
        model.addAttribute("venta", venta);
        model.addAttribute("usuario", usuario);

        return "resumenventa";
    }

    @GetMapping("/guardarVenta")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String guardarVenta(HttpSession session, RedirectAttributes flash,
            SessionStatus status, Principal principal, @RequestParam(defaultValue = "1") Long idCliente) {

        // venta.setFechaHora(fechaHora);
        venta.setNroFactura(0);

        // usuario
        Usuario usuario = ventaService.buscarCajero(principal.getName());
        venta.setCliente(clienteService.buscarPorId(idCliente));
        venta.setUsuario(usuario);
        ventaService.guardar(venta);

        // guardar lineasVenta
        for (LineaVenta dt : lineaVenta) {
            dt.setVenta(venta);
            lineaVentaService.guardar(dt);

        }

        /// limpiar lista y orden
        venta = new Venta();
        lineaVenta.clear();

        status.setComplete();
        flash.addFlashAttribute("success", "Venta registrada por " + usuario.getNombre());
        return "redirect:/getCompras";
    }

    // OBTENER DETALLES DE COMPRAS/PRODUCTOS
    @GetMapping("/getCompras")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String getCompras(Model model, HttpSession session, Principal principal) {

        // model.addAttribute("sesion", session.getAttribute("id_usuario"));
        // usuarioService.buscarPorId(Long.parseLong(session.getAttribute("id_usuario").toString()));

        Usuario usuario = ventaService.buscarCajero(principal.getName());
        List<Venta> compras = ventaService.findByUsuario(usuario);

        model.addAttribute("compras", compras);

        return "compras";
    }

    @GetMapping("/getDetalle/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_CLIENTE" })
    public String getDetalle(Model model, @PathVariable Long id) {
        log.info("Id de la orden {}", id);
        Venta venta = ventaService.buscarPorId(id);

        model.addAttribute("detalles", venta.getLineas());

        return "detallecompra";
    }

    // USUARIO ECOMMERCE REGISTRO
    @GetMapping("/registro")
    public String registro(Model model) {

        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("permisos", permisoService.buscarTodo());

        return "/registro";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult result,
            @RequestParam(defaultValue = "4") Long idPer, @RequestParam("sucursalAsignada") Long idSucursal, 
            Model model, RedirectAttributes msgFlash, SessionStatus status) {

        // Verificar si hay errores...
        if (result.hasErrors()) {
            model.addAttribute("danger", "Corrija los errores");
            return "/registro";
        }

        log.info("Registro de Usuario {}", usuario);
        usuario.setPermiso(permisoService.buscarPorId(idPer));
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        usuario.setSucursalAsignada(sucursalService.buscarPorId(idSucursal));
        usuarioService.guardar(usuario);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(usuario.getEmail());
        mailMessage.setSubject("Usuario Generado - Nexus Informatica tu conección con la tecnologia");
        String messageText = "Al Señor " + usuario.getNombre() + "\n\n"
                + "Nos comunicamos desde la Administración de Nexus Informatica, a fin de informarle que se ha generado un usuario en el sistema en el cual tendrá beneficios y descuentos exclusivos. Para ingresar al sistema, siga estos pasos:\n\n"
                + "1. Ingrese al siguiente enlace que lo llevará a nuestra Web: [nexusInformatica.com.ar/login]\n"
                + "2. En el campo de usuario, ingrese su generado, todo en minúsculas y sin espacios.\n"
                + "3. La contraseña por es la que eligio en nuestra web, sin puntos ni espacios.\n\n"
                + "Saludos cordiales,\n"
                + "Nexus Informatica.";
        mailMessage.setText(messageText);
        mailSender.send(mailMessage);

        model.addAttribute("success", "Usuario registrado correctamente");
        status.setComplete();
        return "redirect:/login";
    }

    @ModelAttribute("permisos")
    public List<Permiso> getPermisos() {
        return permisoService.buscarTodo();
    }

    @ModelAttribute("sucursales")
    public List<Sucursal> getSucursales() {
        return sucursalService.buscarTodos();
    }

}