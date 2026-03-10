package com.alura.literalura.service;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LibroDTO;
import com.alura.literalura.dto.RespuestaApiDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private static final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();

    public Libro buscarYGuardarLibro(String titulo) {
        // Verificar si ya existe en base de datos
        Optional<Libro> libroExistente = libroRepository.findByTituloContainingIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            throw new IllegalStateException("¡El libro ya está registrado en la base de datos!");
        }

        // Consumir API
        String url = URL_BASE + "?search=" + titulo.replace(" ", "+");
        String json = consumoAPI.obtenerDatos(url);
        RespuestaApiDTO respuesta = convierteDatos.obtenerDatos(json, RespuestaApiDTO.class);

        if (respuesta.resultados() == null || respuesta.resultados().isEmpty()) {
            throw new IllegalArgumentException("Libro no encontrado en la API.");
        }

        LibroDTO libroDTO = respuesta.resultados().get(0);

        // Autor
        Autor autor;
        if (libroDTO.autores() != null && !libroDTO.autores().isEmpty()) {
            AutorDTO autorDTO = libroDTO.autores().get(0);
            Optional<Autor> autorExistente = autorRepository.findByNombreContainingIgnoreCase(autorDTO.nombre());
            autor = autorExistente.orElseGet(() -> autorRepository.save(new Autor(autorDTO)));
        } else {
            autor = new Autor(new AutorDTO("Desconocido", null, null));
            autor = autorRepository.save(autor);
        }

        Libro libro = new Libro(libroDTO, autor);
        return libroRepository.save(libro);
    }

    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAllOrderByDescargasDesc();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma.toLowerCase());
    }

    public Long contarLibrosPorIdioma(String idioma) {
        return libroRepository.countByIdioma(idioma.toLowerCase());
    }

    public List<Autor> listarTodosLosAutores() {
        return autorRepository.findAllOrderByNombre();
    }

    public List<Autor> listarAutoresVivosEnAnio(Integer anio) {
        return autorRepository.findAutoresVivosEnAnio(anio);
    }

    public List<Libro> listarTop10Libros() {
        return libroRepository.findAllOrderByDescargasDesc().stream().limit(10).toList();
    }
}
