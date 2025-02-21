package org.example.probagrupoa.entity.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.probagrupoa.entity.Categoria;
import org.example.probagrupoa.entity.Foto;
import org.example.probagrupoa.entity.Usuario;

public class ProductoRequestDto {

    @NotBlank
    private String name;

    private String description;

    private String antiquity;

    //private Label etiqueta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL)
    private Foto foto;

    @Min(0)
    private float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAntiquity() {
        return antiquity;
    }

    public void setAntiquity(String antiquity) {
        this.antiquity = antiquity;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductoRequestDto(String name, String description, String antiquity, Categoria categoria, Usuario usuario, Foto foto, float price) {
        this.name = name;
        this.description = description;
        this.antiquity = antiquity;
        this.categoria = categoria;
        this.usuario = usuario;
        this.foto = foto;
        this.price = price;
    }
}
