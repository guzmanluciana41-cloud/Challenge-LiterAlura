package com.alura.literalura.model;

import com.alura.literalura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(AutorDTO dto) {
        this.nombre = dto.nombre();
        this.anioNacimiento = dto.anioNacimiento();
        this.anioFallecimiento = dto.anioFallecimiento();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }
    public Integer getAnioFallecimiento() { return anioFallecimiento; }
    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }

    @Override
    public String toString() {
        String fallecimiento = (anioFallecimiento != null) ? String.valueOf(anioFallecimiento) : "Presente";
        StringBuilder sb = new StringBuilder();
        sb.append("┌─────────────────────────────────────────┐\n");
        sb.append(String.format("│ Autor    : %-30s│\n", nombre));
        sb.append(String.format("│ Nacimiento: %-29s│\n", anioNacimiento != null ? anioNacimiento : "Desconocido"));
        sb.append(String.format("│ Fallecimiento: %-26s│\n", fallecimiento));

        List<String> titulos = libros.stream().map(Libro::getTitulo).toList();
        sb.append(String.format("│ Libros   : %-30s│\n", String.join(", ", titulos)));
        sb.append("└─────────────────────────────────────────┘");
        return sb.toString();
    }
}
