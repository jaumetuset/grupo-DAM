package org.example.probagrupoa.repository;

import org.example.probagrupoa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password")
    Usuario loginUsuario(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario filtarUsuariosByEmail(@Param("email") String email);
}
