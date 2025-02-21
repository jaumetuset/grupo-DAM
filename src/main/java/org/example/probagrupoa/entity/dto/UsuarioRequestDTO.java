package org.example.probagrupoa.entity.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UsuarioRequestDTO {

    @Email
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String name;

    @Pattern(regexp = "^[A-Za-z\\d]{8,}$")
    private String password;

    private boolean admin = false;

    private String poblacion;

    public UsuarioRequestDTO(String email, String name, String password, boolean admin, String poblacion) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.poblacion = poblacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
}
