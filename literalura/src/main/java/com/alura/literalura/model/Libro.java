package com.alura.literalura.model;

import com.alura.literalura.dto.LibroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;
    private Double numDescargas;

    public Libro() {}

    public Libro(LibroDTO dto, Autor autor) {
        this.titulo = dto.titulo();
        this.autor = autor;
        this.idioma = (dto.idiomas() != null && !dto.idiomas().isEmpty()) ? dto.idiomas().get(0) : "Desconocido";
        this.numDescargas = dto.numDescargas();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Double getNumDescargas() { return numDescargas; }
    public void setNumDescargas(Double numDescargas) { this.numDescargas = numDescargas; }

    @Override
    public String toString() {
        return "┌─────────────────────────────────────────┐\n" +
                String.format("│ Título   : %-30s│\n", titulo.length() > 30 ? titulo.substring(0, 27) + "..." : titulo) +
                String.format("│ Autor    : %-30s│\n", autor != null ? (autor.getNombre().length() > 30 ? autor.getNombre().substring(0, 27) + "..." : autor.getNombre()) : "Desconocido") +
                String.format("│ Idioma   : %-30s│\n", idioma) +
                String.format("│ Descargas: %-30s│\n", numDescargas != null ? String.format("%.0f", numDescargas) : "N/A") +
                "└─────────────────────────────────────────┘";
    }
}
