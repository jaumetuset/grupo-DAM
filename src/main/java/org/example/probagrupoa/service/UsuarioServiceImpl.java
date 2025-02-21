package org.example.probagrupoa.service;

import jakarta.transaction.Transactional;
import org.example.probagrupoa.entity.Usuario;
import org.example.probagrupoa.entity.dto.UsuarioRequestDTO;
import org.example.probagrupoa.repository.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    private IUsuarioRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public Usuario insertUser(UsuarioRequestDTO usuario) {
        try {
            System.out.println("CANCERRR ->>>>>>>>>>>>>>>>>><<"+usuario.getName());
            Usuario u = mapper.map(usuario, Usuario.class);
            return repo.save(u);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Error de concurrencia al insertar el usuario", e);
        }
    }

    @Override
    public Usuario modificarUser(Usuario usuario) {
        System.out.println("Usuario encontrado: " + usuario.getName() + " " + usuario.getEmail());
        return repo.save(usuario);
    }

    @Override
    public void deleteUser(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Usuario loginUsuario(String email, String password) {

            System.out.println("==================================================================");
            Usuario user = repo.loginUsuario(email, password);
            if (user != null) {
                System.out.println("Usuario encontrado: " + user.getName());
            } else {
                System.out.println("Usuario no encontrado o credenciales incorrectas.");
            }
            return user;
        }

    @Override
    public List<Usuario> listaUsuarios() {
        return repo.findAll();
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        Usuario u = repo.filtarUsuariosByEmail(email);
        return u;
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public int getIdUsuarioByEmail(String email) {
        Usuario u = repo.filtarUsuariosByEmail(email);
        return u.getId();
    }


}
