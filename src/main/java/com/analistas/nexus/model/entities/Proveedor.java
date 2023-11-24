package com.analistas.nexus.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La Razon Social es Obligatoria...")
    @Size(max = 50)
    private String razon_soc;

    @NotBlank(message = "El cuil es Obligatorio...")
    @Size(max = 11)
    private String cuil;

    @NotBlank(message = "La direccion es Obligatoria...")
    @Size(max = 50)
    private String direccion;

    @Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$", message = "Escribe nuevamente tu email")
    private String email;

    @NotBlank(message = "La localidad es Obligatoria...")
    @Size(max = 50)
    private String localidad;

    @NotNull(message = "el telefono es obligatorio...")
    @Size(max = 10)
    private String telefono;

    @Column(name = "activo", columnDefinition = "boolean default 1")
    private boolean activo;

    public Proveedor() {
        activo = true;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazon_soc() {
        return razon_soc;
    }

    public void setRazon_soc(String razon_soc) {
        this.razon_soc = razon_soc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return  razon_soc;
    }

}
