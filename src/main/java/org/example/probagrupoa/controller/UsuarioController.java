package org.example.probagrupoa.controller;

import jakarta.validation.Valid;
import org.example.probagrupoa.entity.Usuario;
import org.example.probagrupoa.entity.dto.UsuarioRequestDTO;
import org.example.probagrupoa.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return new ResponseEntity<>(service.listaUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> findById(@PathVariable("id") Integer id) {
        Optional<Usuario> u = service.getUsuarioById(id);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/getId/{email}")
    public ResponseEntity<Integer> findByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(service.getIdUsuarioByEmail(email), HttpStatus.OK);
    }

    /*=== LOGIN ===*/
    @GetMapping("/login")
    public ResponseEntity<Usuario> login(@Valid @RequestParam("email") String email, @Valid @RequestParam("password") String password) {
        System.out.println("Estan probant a loggearse");
        Usuario user = service.loginUsuario(email,password);
        System.out.println(user.getName() + " " + user.getPassword() + " " + user.getEmail());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }


    /*=== POST ===*/

    // PORROOOOOOOOOOOOOOOOOOOOOOOOOOOOO
    @PostMapping
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody UsuarioRequestDTO usuario) {
        System.out.println("Estan intentant registrarse");
        Usuario u = service.getUsuarioByEmail(usuario.getEmail());

        if (u == null) {
            System.out.println("Estan intentant registrarse <<<FALLAT>>>");
            Usuario user = service.insertUser(usuario);

            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            System.out.println("Estan intentant registrarse <<<CORRECTA>>>");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    /*=== PUT ===*/
    @PutMapping
    public ResponseEntity<Usuario> modificar(@Valid @RequestBody Usuario usuario) {
        Usuario user = service.modificarUser(usuario);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /*=== DELETE ===*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id){
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
