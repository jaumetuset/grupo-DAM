package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Producto;
import org.example.probagrupoa.entity.dto.ProductoRequestDto;

import java.util.List;

public interface IProductoService {
    List<Producto> listar();
    List<Producto> filtrarPorParametros(String name, Float price, String nomCategoria);
    void detele(Integer id);
    Producto registrar(ProductoRequestDto producto);
    Producto modificar(Producto producto);
}
