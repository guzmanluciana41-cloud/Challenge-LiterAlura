package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);

    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idioma = :idioma")
    Long countByIdioma(String idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.numDescargas DESC")
    List<Libro> findAllOrderByDescargasDesc();
}
