package org.example.probagrupoa.controller;

import org.example.probagrupoa.entity.Usuario;
import org.example.probagrupoa.entity.dto.UsuarioRequestDTO;
import org.example.probagrupoa.repository.IUsuarioRepository;
import org.example.probagrupoa.service.IUsuarioService;
import org.example.probagrupoa.service.OdooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odoo")
public class OdooController {

    @Autowired
    private OdooService odooService;

    @Autowired
    private IUsuarioService service;

    private String partnerId;

    @PostMapping("/create")
    public String createPartner(@RequestBody UsuarioRequestDTO u) {

        Usuario usuario = service.getUsuarioByEmail(u.getEmail());
        System.out.println("-------------------" + usuario.getEmail() + "------------------" + usuario.getName());
        if (usuario != null) {

            usuario.setAdmin(true);
            service.modificarUser(usuario);

        }

        try {

            partnerId = odooService.createPartner(usuario.getName(), usuario.getEmail());
            return "Nuevo partner creado con ID: " + partnerId;

        } catch (Exception e) {
            return "Error al crear el partner: " + e.getMessage();
        }

    }

    @GetMapping
    public ResponseEntity<String> returnString() {
        System.out.println("El partner: " + partnerId);
        return ResponseEntity.ok(partnerId);
    }
}