package org.example.probagrupoa.controller;

import org.example.probagrupoa.entity.Categoria;
import org.example.probagrupoa.entity.Producto;
import org.example.probagrupoa.service.ICategoriaService;
import org.example.probagrupoa.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private ICategoriaService service;

    /*=== GET ===*/

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Categoria>insertar(@RequestBody Categoria categoria){
        return new ResponseEntity<>(service.insertar(categoria),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filtrarUnaCategoria/{name}")
    public ResponseEntity<Categoria> listarUnaCategorias(@PathVariable("name") String name) {
        System.out.println("Estic en el controller -> " + name);
        Categoria categoria = service.seleccionarCategoria(name);
        if (categoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Categoria>> obtenerCategoria(@PathVariable("id") Integer id){
        Optional<Categoria> categoria = service.findById(id);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
}
