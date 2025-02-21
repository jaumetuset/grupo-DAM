package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> listar();
    Categoria insertar(Categoria categoria);
    Categoria seleccionarCategoria(String name);
    Optional<Categoria> findById(Integer id);
    void eliminar(Integer id);
}
