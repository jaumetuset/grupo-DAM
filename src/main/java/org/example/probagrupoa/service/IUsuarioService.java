package org.example.probagrupoa.service;

import org.example.probagrupoa.entity.Usuario;
import org.example.probagrupoa.entity.dto.UsuarioRequestDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Usuario insertUser(UsuarioRequestDTO usuarioDto);
    Usuario modificarUser(Usuario usuario);
    void deleteUser(Integer id);
    Usuario loginUsuario(String email,  String password);
    List<Usuario> listaUsuarios();
    Usuario getUsuarioByEmail(String email);
    Optional<Usuario> getUsuarioById(Integer id);
    int getIdUsuarioByEmail(String email);
}
