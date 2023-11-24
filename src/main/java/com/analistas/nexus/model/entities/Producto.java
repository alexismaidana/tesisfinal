package com.analistas.nexus.model.entities;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_bar", length = 13)
    private String codigoBarras;

    @Size(max = 50)
    //@NotBlank(message = "El titulo es requerido.")
    private String titulo;

    @Size(max = 500)
    @NotBlank(message = "La descripcion es requerida.")
    private String descripcion;

    @NotNull(message = "El precio es requerido.")
    @NumberFormat(pattern = "#,##0.00", style = Style.CURRENCY)
    private BigDecimal precio;

    @NotNull(message = "Ingrese la cantidad de Stock")
    private int stock;

    @Column(name = "lnk_img", length = 500)
    private String linkimagen;

    @Column(name = "activo", columnDefinition = "boolean default 1")
    private boolean activo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id")
    private Proveedor proveedor;

    public Producto() {
        activo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
  

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getLinkimagen() {
        return linkimagen;
    }

    public void setLinkimagen(String linkimagen) {
        this.linkimagen = linkimagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return  id + " - " + descripcion + " - " + precio;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    
    
}
