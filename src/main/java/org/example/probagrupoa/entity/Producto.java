package org.example.probagrupoa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public Producto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Producto(Integer id, String name, String description, String antiquity, Categoria categoria, Usuario usuario, Foto foto, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.antiquity = antiquity;
        this.categoria = categoria;
        this.usuario = usuario;
        this.foto = foto;
        this.price = price;
    }
}
