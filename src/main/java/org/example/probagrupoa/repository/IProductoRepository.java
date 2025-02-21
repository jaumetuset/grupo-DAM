package org.example.probagrupoa.repository;

import org.example.probagrupoa.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto,Integer> {

    @Query("FROM Producto p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:price IS NULL OR p.price <= :price) " +
            "AND (:nomCategoria IS NULL OR p.categoria.name = :nomCategoria)")
    List<Producto> filtrarPorParametros(
            @Param("name") String name,
            @Param("price") Float price,
            @Param("nomCategoria") String nomCategoria);
}
