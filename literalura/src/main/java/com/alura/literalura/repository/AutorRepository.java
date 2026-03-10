package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreContainingIgnoreCase(String nombre);

    // Derived query: autores vivos en un año dado
    // Nacido antes o en ese año Y (no tiene fecha de muerte O murió después de ese año)
    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    @Query("SELECT a FROM Autor a ORDER BY a.nombre")
    List<Autor> findAllOrderByNombre();
}
