package com.analistas.nexus.model.entities;


import java.math.BigDecimal;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "linea_compras")
public class LineaCompra {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1)
    private int cantidad;

    // @NumberFormat(pattern = "#,##0.00", style = Style.CURRENCY)
    private BigDecimal precioActual;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getPrecioActual() {
        return precioActual;
    }
    public void setPrecioActual(BigDecimal precioActual) {
        this.precioActual = precioActual;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double calcularSubtotal(){

        return cantidad * precioActual.doubleValue();

    }

    
}


