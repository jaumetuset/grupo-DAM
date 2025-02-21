package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Producto;
import org.example.probagrupoa.entity.dto.ProductoRequestDto;
import org.example.probagrupoa.repository.IProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Producto> listar() {
        return repo.findAll();
    }

    @Override
    public List<Producto> filtrarPorParametros(String name, Float price, String nomCategoria) {
        // Llamamos al repositorio con los filtros proporcionados
        return repo.filtrarPorParametros(name, price, nomCategoria);
    }

    @Override
    public void detele(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Producto registrar(ProductoRequestDto producto) {
        Producto p = mapper.map(producto, Producto.class);
        return repo.save(p);
    }

    @Override
    public Producto modificar(Producto producto) {
        return repo.save(producto);
    }
}
