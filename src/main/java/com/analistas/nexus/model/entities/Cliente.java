package com.analistas.nexus.model.entities;

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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30, min = 5)
    //@NotEmpty(message = "El nombre de ususario es requerido...")
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?(( |\\-)[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?)*", message="Escribe nuevamente tu nombre")
    private String nombre;

    @Size(max = 30, min = 5)
    //@NotEmpty(message = "El nombre de ususario es requerido...")
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?(( |\\-)[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?)*", message="Escribe nuevamente tu apellido")
    private String apellido;
    
    @Size(max = 30, min = 5)
    //@NotEmpty(message = "El nombre de ususario es requerido...")
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?(( |\\-)[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?)*", message="Escribe nuevamente tu direccion")
    private String direccion;
    
    @NotBlank(message = "El DNI es obligatorio.")
    @Size(max = 8)
    @Column(name = "dni")
    private String dni;

    @NotBlank(message = "El Email es obligatorio.")
    @Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$", message="Escribe nuevamente tu email")
    private String email;

    @Column(name = "activo", columnDefinition = "boolean default 1")
    private Boolean activo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

       
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public Cliente() {
        activo = true;
    }

  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

      public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

   
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

   
    public String getEmail() {
        return email;
    }

   
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

       
    
}


   