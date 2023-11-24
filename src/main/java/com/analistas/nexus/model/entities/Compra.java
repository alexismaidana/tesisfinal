package com.analistas.nexus.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha es requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm z")
    @Column(name = "fec_hor")
    private LocalDateTime fechaHora;

    @Size(max = 65)
    private String descripcion;

    @Column(name = "activo", columnDefinition = "boolean default 1")
    private boolean activo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id")
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

        @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_compra")
    private List<LineaCompra> lineas;

    public Compra() {
        activo = true;
        lineas = new ArrayList<>();
        fechaHora = LocalDateTime.now();
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<LineaCompra> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaCompra> lineas) {
        this.lineas = lineas;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    // funcione-----

    public int calcularCantidad() {
        int total = 0;

        for (LineaCompra ln : lineas) {
            total += ln.getCantidad();
        }
        return total;
    }

    public Double calcularTotal() {

        double total = 0.0;
        for (LineaCompra ln : lineas) {
            total += ln.calcularSubtotal();
        }

        return total;
    }

    public void addLinea(LineaCompra linea) {
        lineas.add(linea);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
