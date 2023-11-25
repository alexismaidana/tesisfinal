package com.analistas.nexus.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analistas.nexus.model.entities.LineaVenta;

public interface ILineaVentaRepository extends JpaRepository<LineaVenta, Long> {
    @Query("select lv from LineaVenta lv where lv.activo = true")
    List<LineaVenta> buscarPor(@Param("criterio") String criterio);

    @Query("select lv from LineaVenta lv where lv.activo = true")
    List<LineaVenta> buscarSoloHabilitados();

}
