package org.example.probagrupoa.repository;

import org.example.probagrupoa.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFotoRepository extends JpaRepository<Foto,Integer> {
    List<Foto> findByProductoId(Integer productoId);

}
