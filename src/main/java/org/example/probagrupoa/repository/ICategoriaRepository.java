package org.example.probagrupoa.repository;

import org.example.probagrupoa.entity.Categoria;
import org.example.probagrupoa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository  extends JpaRepository<Categoria,Integer> {
    @Query("SELECT c FROM Categoria c WHERE c.name = :name")
    Categoria seleccionarCategoria(@Param("name") String name);

}
