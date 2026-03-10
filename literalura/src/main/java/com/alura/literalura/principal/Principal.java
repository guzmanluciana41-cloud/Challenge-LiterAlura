package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private LibroService libroService;

    private final Scanner scanner = new Scanner(System.in);

    private static final String RESET  = "\033[0m";
    private static final String CYAN   = "\033[1;36m";
    private static final String GREEN  = "\033[1;32m";
    private static final String YELLOW = "\033[1;33m";
    private static final String RED    = "\033[1;31m";
    private static final String PURPLE = "\033[1;35m";

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarBanner();
            System.out.println(CYAN + "╔════════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN + "║           MENÚ PRINCIPAL                  ║" + RESET);
            System.out.println(CYAN + "╠════════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + "║" + RESET + "  1 - Buscar libro por título               " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  2 - Listar libros registrados             " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  3 - Listar autores registrados            " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  4 - Listar autores vivos en un año        " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  5 - Listar libros por idioma              " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  6 - Estadísticas de libros por idioma     " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  7 - Top 10 libros más descargados         " + CYAN + "║" + RESET);
            System.out.println(CYAN + "║" + RESET + "  0 - Salir                                 " + CYAN + "║" + RESET);
            System.out.println(CYAN + "╚════════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "  Selecciona una opción: " + RESET);

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED + "\n  ⚠ Opción inválida. Ingresa un número.\n" + RESET);
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> estadisticasPorIdioma();
                case 7 -> top10Libros();
                case 0 -> System.out.println(GREEN + "\n  ¡Hasta luego! 📚\n" + RESET);
                default -> System.out.println(RED + "\n  ⚠ Opción no válida. Intenta de nuevo.\n" + RESET);
            }
        }
    }

    private void mostrarBanner() {
        System.out.println();
        System.out.println(PURPLE + "  ██╗     ██╗████████╗███████╗██████╗  █████╗ ██╗     ██╗   ██╗██████╗  █████╗ " + RESET);
        System.out.println(PURPLE + "  ██║     ██║╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     ██║   ██║██╔══██╗██╔══██╗" + RESET);
        System.out.println(PURPLE + "  ██║     ██║   ██║   █████╗  ██████╔╝███████║██║     ██║   ██║██████╔╝███████║" + RESET);
        System.out.println(PURPLE + "  ██║     ██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     ██║   ██║██╔══██╗██╔══██║" + RESET);
        System.out.println(PURPLE + "  ███████╗██║   ██║   ███████╗██║  ██║██║  ██║███████╗╚██████╔╝██║  ██║██║  ██║" + RESET);
        System.out.println(PURPLE + "  ╚══════╝╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝" + RESET);
        System.out.println(CYAN + "              📚 Catálogo de Libros - Oracle ONE Challenge 📚" + RESET);
        System.out.println();
    }

    private void buscarLibro() {
        System.out.println(GREEN + "\n  ── BUSCAR LIBRO POR TÍTULO ──" + RESET);
        System.out.print("  Ingresa el título: ");
        String titulo = scanner.nextLine().trim();

        if (titulo.isBlank()) {
            System.out.println(RED + "  ⚠ El título no puede estar vacío.\n" + RESET);
            return;
        }

        try {
            Libro libro = libroService.buscarYGuardarLibro(titulo);
            System.out.println(GREEN + "\n  ✔ Libro encontrado y registrado:" + RESET);
            System.out.println(libro);
        } catch (IllegalStateException e) {
            System.out.println(YELLOW + "\n  ⚠ " + e.getMessage() + "\n" + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "\n  ✘ Libro no encontrado.\n" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "\n  ✘ Error: " + e.getMessage() + "\n" + RESET);
        }
    }

    private void listarLibros() {
        System.out.println(GREEN + "\n  ── LIBROS REGISTRADOS ──" + RESET);
        List<Libro> libros = libroService.listarTodosLosLibros();
        if (libros.isEmpty()) {
            System.out.println(YELLOW + "  No hay libros registrados aún.\n" + RESET);
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        System.out.println(GREEN + "\n  ── AUTORES REGISTRADOS ──" + RESET);
        List<Autor> autores = libroService.listarTodosLosAutores();
        if (autores.isEmpty()) {
            System.out.println(YELLOW + "  No hay autores registrados aún.\n" + RESET);
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println(GREEN + "\n  ── AUTORES VIVOS EN UN AÑO ──" + RESET);
        System.out.print("  Ingresa el año: ");
        try {
            int anio = Integer.parseInt(scanner.nextLine().trim());
            if (anio < 0 || anio > 2100) {
                System.out.println(RED + "  ⚠ Año inválido.\n" + RESET);
                return;
            }
            List<Autor> autores = libroService.listarAutoresVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println(YELLOW + "  No se encontraron autores vivos en el año " + anio + ".\n" + RESET);
            } else {
                System.out.println(GREEN + "  Autores vivos en " + anio + ":" + RESET);
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "  ⚠ Ingresa un año válido (número).\n" + RESET);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println(GREEN + "\n  ── LISTAR LIBROS POR IDIOMA ──" + RESET);
        System.out.println("  Idiomas disponibles: ES | EN | FR | PT");
        System.out.print("  Ingresa el idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (!List.of("es", "en", "fr", "pt").contains(idioma)) {
            System.out.println(RED + "  ⚠ Idioma no reconocido. Usa: es, en, fr, pt\n" + RESET);
            return;
        }

        List<Libro> libros = libroService.listarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println(YELLOW + "  No hay libros en el idioma: " + idioma.toUpperCase() + "\n" + RESET);
        } else {
            System.out.println(GREEN + "  Libros en " + idioma.toUpperCase() + ":" + RESET);
            libros.forEach(System.out::println);
        }
    }

    private void estadisticasPorIdioma() {
        System.out.println(GREEN + "\n  ── ESTADÍSTICAS POR IDIOMA ──" + RESET);
        String[] idiomas = {"es", "en", "fr", "pt"};
        String[] nombres = {"Español", "Inglés", "Francés", "Portugués"};

        System.out.println(CYAN + "  ┌────────────────────────────────┐" + RESET);
        System.out.println(CYAN + "  │   Idioma       │   Cantidad    │" + RESET);
        System.out.println(CYAN + "  ├────────────────────────────────┤" + RESET);
        for (int i = 0; i < idiomas.length; i++) {
            long cantidad = libroService.contarLibrosPorIdioma(idiomas[i]);
            System.out.printf(CYAN + "  │" + RESET + " %-14s │ %-13d " + CYAN + "│\n" + RESET, nombres[i], cantidad);
        }
        System.out.println(CYAN + "  └────────────────────────────────┘" + RESET);

        // Estadísticas adicionales con Streams
        List<Libro> todosLosLibros = libroService.listarTodosLosLibros();
        if (!todosLosLibros.isEmpty()) {
            DoubleSummaryStatistics stats = todosLosLibros.stream()
                    .filter(l -> l.getNumDescargas() != null)
                    .mapToDouble(Libro::getNumDescargas)
                    .summaryStatistics();

            System.out.println(GREEN + "\n  📊 Estadísticas de descargas:" + RESET);
            System.out.printf("     Total libros  : %d%n", stats.getCount());
            System.out.printf("     Máx. descargas: %.0f%n", stats.getMax());
            System.out.printf("     Mín. descargas: %.0f%n", stats.getMin());
            System.out.printf("     Promedio      : %.2f%n%n", stats.getAverage());
        }
    }

    private void top10Libros() {
        System.out.println(GREEN + "\n  ── TOP 10 LIBROS MÁS DESCARGADOS ──" + RESET);
        List<Libro> top = libroService.listarTop10Libros();
        if (top.isEmpty()) {
            System.out.println(YELLOW + "  No hay libros registrados aún.\n" + RESET);
        } else {
            for (int i = 0; i < top.size(); i++) {
                System.out.printf(YELLOW + "\n  #%d " + RESET, i + 1);
                System.out.println(top.get(i));
            }
        }
    }
}
