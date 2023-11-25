package com.analistas.nexus.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nroFactura;
    private double total;

    @NotNull(message = "La fecha es requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm z")
    @Column(name = "fec_hor")
    private LocalDateTime fechaHora;

    @Size(max = 65)
    private String descripcion;

    @Column(name = "activo", columnDefinition = "boolean default 1")
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_venta")
    private List<LineaVenta> lineas;

    public Venta() {
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

    public int getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(int nroFactura) {
        this.nroFactura = nroFactura;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<LineaVenta> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaVenta> lineas) {
        this.lineas = lineas;
    }

    public int calcularCantidad() {
        int total = 0;

        for (LineaVenta ln : lineas) {
            total += ln.getCantidad();
        }
        return total;
    }

   
    public Double calcularTotal() {

        double total = 0.0;
        for (LineaVenta ln : lineas) {
            total += ln.calcularSubtotal();
        }

        return total;
    }

    public void addLinea(LineaVenta linea) {
        lineas.add(linea);
    }

    public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
