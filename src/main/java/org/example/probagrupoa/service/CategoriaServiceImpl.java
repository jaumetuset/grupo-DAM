package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Categoria;
import org.example.probagrupoa.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

    @Autowired
    private ICategoriaRepository repo;


    @Override
    public List<Categoria> listar() {
            return repo.findAll();
    }

    @Override
    public Categoria insertar(Categoria categoria) {
        return repo.save(categoria);
    }

    @Override
    public Categoria seleccionarCategoria(String name) {
        return repo.seleccionarCategoria(name);
    }

    @Override
    public Optional<Categoria> findById(Integer id) {
        Optional<Categoria> categoria = repo.findById(id);
        return categoria;
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

}
