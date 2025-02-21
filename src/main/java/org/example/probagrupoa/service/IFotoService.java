package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Foto;
import java.util.List;
import java.util.Optional;

public interface IFotoService {
    Foto guardar(Foto foto); // Guarda una nueva foto en la base de datos
    Optional<Foto> buscarPorId(Integer id); // Busca una foto por su ID
    List<Foto> listarFotosPorProducto(Integer productoId); // Lista todas las fotos de un producto
    void eliminar(Integer id); // Elimina una foto
}
