package org.example.probagrupoa.controller;

import jakarta.validation.Valid;
import org.example.probagrupoa.entity.Categoria;
import org.example.probagrupoa.entity.Producto;
import org.example.probagrupoa.entity.dto.ProductoRequestDto;
import org.example.probagrupoa.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService service;

    /*=== GET ===*/

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> filtrarProductos(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float price,
            @RequestParam(required = false) String nomCategoria) {

        return new ResponseEntity<>(service.filtrarPorParametros(name, price, nomCategoria),HttpStatus.OK);
    }

    /*=== POST ===*/
    @PostMapping
    public ResponseEntity<Producto> registrar(@Valid @RequestBody ProductoRequestDto producto) {
        Producto p = service.registrar(producto);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*=== PUT ===*/
    @PutMapping
    public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto producto) {
        Producto p = service.modificar(producto);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*=== DELETE ===*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id){
        service.detele(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
