package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Foto;
import org.example.probagrupoa.repository.IFotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FotoServiceImpl implements IFotoService {

    @Autowired
    private IFotoRepository fotoRepository;

    @Override
    public Foto guardar(Foto foto) {
        return fotoRepository.save(foto);
    }

    @Override
    public Optional<Foto> buscarPorId(Integer id) {
        return fotoRepository.findById(id);
    }

    @Override
    public List<Foto> listarFotosPorProducto(Integer productoId) {
        return fotoRepository.findByProductoId(productoId);
    }

    @Override
    public void eliminar(Integer id) {
        fotoRepository.deleteById(id);
    }
}
