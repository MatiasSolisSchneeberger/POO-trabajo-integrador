import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Clase ejecutable para probar las funcionalidades de la clase Biblioteca.
 * Simula un sistema de gestión de biblioteca a través de un menú interactivo.
 *
 * @author ...
 * @version 04/11/2025
 */
public class TestBiblioteca {

    // Scanner para la entrada de datos del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // Instancia de la biblioteca para operar
    //private static final Biblioteca miBiblioteca = new Biblioteca("Biblioteca Central UNL");
    private static Biblioteca miBiblioteca;

    public static void main(String[] args) {
        // Inicialización de datos de prueba
        miBiblioteca = GestorPersistencia.cargar();
        //inicializarDatos();
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Ingrese una opción (0 para salir): ");

            try {
                switch (opcion) {
                    case 1:
                        menuGestionSocios();
                        break;
                    case 2:
                        menuGestionLibros();
                        break;
                    case 3:
                        menuGestionPrestamos();
                        break;
                    case 4:
                        menuConsultas();
                        break;
                    case 0:
                        System.out.println("👋 Saliendo del sistema de la biblioteca. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("❌ Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error en la operación: " + e.getMessage());
            }

            if (opcion != 0) {
                pausar();
            }

        } while (opcion != 0);
        GestorPersistencia.guardar(miBiblioteca);
        scanner.close();
    }

    /**
     * Inicializa algunos datos de ejemplo (socios y libros) para facilitar las pruebas.
     
    private static void inicializarDatos() {
        System.out.println("🛠️ Inicializando datos de prueba...");

        // Socios Estudiantes (días de préstamo por defecto: 20, según ctor de Estudiante)
        miBiblioteca.nuevoSocioEstudiante(12345678, "Ana Garcia", "Ingeniería");
        miBiblioteca.nuevoSocioEstudiante(23456789, "Juan Perez", "Derecho");
        miBiblioteca.nuevoSocioEstudiante(34567890, "Maria Lopez", "Medicina"); // Para prueba de límite (3+1)

        // Socios Docentes (días de préstamo por defecto: 5, según ctor de Docente)
        miBiblioteca.nuevoSocioDocente(45678901, "Dr. Carlos Ruiz", "Matemáticas");
        miBiblioteca.nuevoSocioDocente(56789012, "Lic. Laura Torres", "Historia");

        // Libros
        miBiblioteca.nuevoLibro("Cien años de soledad", 1, "Sudamericana", 1967);
        miBiblioteca.nuevoLibro("El señor de los anillos", 2, "Minotauro", 1954);
        miBiblioteca.nuevoLibro("Física I", 5, "Pearson", 2018);
        miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010);
        miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010); // Duplicado para prueba

        System.out.println("✅ Datos de prueba cargados.\n");
    } */
    
    /**
     * Inicializa datos de ejemplo SOLO si la biblioteca estaba vacía (recién creada).
     */
    private static void inicializarDatos() {
        // Verificar si la biblioteca está vacía para cargar los datos de prueba
        if (miBiblioteca.getSocios().isEmpty() && miBiblioteca.getLibros().isEmpty()) {
            System.out.println("🛠️ Inicializando datos de prueba...");
            
            // Socios Estudiantes
            //miBiblioteca.nuevoSocioEstudiante(12345678, "Ana Garcia", "Ingeniería");
            // ... (el resto de la inicialización sigue igual)
            
            System.out.println("✅ Datos de prueba cargados.\n");
        } else {
            System.out.println("ℹ️ Datos persistentes encontrados y cargados. Saltando inicialización.");
        }
    }
    // --- Menús y Lógica de Interacción ---

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== 📚 " + miBiblioteca.getNombre() + " - Menú Principal =====");
        System.out.println("1. 👤 Gestión de Socios");
        System.out.println("2. 📖 Gestión de Libros");
        System.out.println("3. 🔄 Préstamos y Devoluciones");
        System.out.println("4. 🔍 Consultas e Informes");
        System.out.println("0. 🚪 Salir");
        System.out.println("----------------------------------------------");
    }

    // --- Submenú Gestión de Socios (Opción 1) ---

    private static void menuGestionSocios() {
        int opcion;
        do {
            System.out.println("\n===== 👤 Gestión de Socios =====");
            System.out.println("1. Agregar Estudiante");
            System.out.println("2. Agregar Docente");
            System.out.println("3. Quitar Socio (por DNI)");
            System.out.println("4. Listar Socios");
            System.out.println("5. Cambiar Días de Préstamo (Docente)");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("---------------------------------");

            opcion = leerEntero("Ingrese una opción (0 para volver): ");

            switch (opcion) {
                case 1:
                    agregarSocioEstudiante();
                    break;
                case 2:
                    agregarSocioDocente();
                    break;
                case 3:
                    quitarSocio();
                    break;
                case 4:
                    System.out.println("\n" + miBiblioteca.listaDeSocios());
                    break;
                case 5:
                    cambiarDiasDocente();
                    break;
                case 0:
                    System.out.println("↩️ Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
            if (opcion != 0) pausar();
        } while (opcion != 0);
    }

    private static void agregarSocioEstudiante() {
        int dni = leerEntero("DNI del Estudiante: ");
        String nombre = leerCadena("Nombre del Estudiante: ");
        String carrera = leerCadena("Carrera: ");
        miBiblioteca.nuevoSocioEstudiante(dni, nombre, carrera);
    }

    private static void agregarSocioDocente() {
        int dni = leerEntero("DNI del Docente: ");
        String nombre = leerCadena("Nombre del Docente: ");
        String area = leerCadena("Área de Especialización: ");
        miBiblioteca.nuevoSocioDocente(dni, nombre, area);
    }

    private static void quitarSocio() {
        int dni = leerEntero("DNI del socio a quitar: ");
        Socio socio = miBiblioteca.buscarSocio(dni);
        if (socio != null) {
            miBiblioteca.quitarSocio(socio);
        } else {
            System.out.println("Socio no encontrado.");
        }
    }

    private static void cambiarDiasDocente() {
        int dni = leerEntero("DNI del Docente a modificar: ");
        Socio socio = miBiblioteca.buscarSocio(dni);

        if (socio instanceof Docente docente) {
            if (docente.esResponsable()) {
                int dias = leerEntero("Días a sumar/restar (ej: 3, -2): ");
                docente.cambiarDiasDePrestamo(dias);
                System.out.println("✅ Días de préstamo cambiados. Nuevo límite: " + docente.getDiasPrestamo() + " días.");
            } else {
                System.out.println("🚫 El docente no es responsable. No se puede cambiar el límite de días.");
            }
        } else if (socio != null) {
            System.out.println("🚫 El socio con DNI " + dni + " no es Docente.");
        } else {
            System.out.println("🚫 Socio no encontrado.");
        }
    }

    // --- Submenú Gestión de Libros (Opción 2) ---

    private static void menuGestionLibros() {
        int opcion;
        do {
            System.out.println("\n===== 📖 Gestión de Libros =====");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Quitar Libro");
            System.out.println("3. Listar Libros (con estado)");
            System.out.println("4. Listar Títulos Únicos");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("---------------------------------");

            opcion = leerEntero("Ingrese una opción (0 para volver): ");

            switch (opcion) {
                case 1:
                    agregarLibro();
                    break;
                case 2:
                    quitarLibro();
                    break;
                case 3:
                    System.out.println("\n--- Lista de Libros ---");
                    System.out.println(miBiblioteca.listaDeLibros());
                    break;
                case 4:
                    System.out.println("\n--- Lista de Títulos Únicos ---");
                    System.out.println(miBiblioteca.listaDeTitulos());
                    break;
                case 0:
                    System.out.println("↩️ Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
            if (opcion != 0) pausar();
        } while (opcion != 0);
    }

    private static void agregarLibro() {
        String titulo = leerCadena("Título: ");
        int edicion = leerEntero("Edición: ");
        String editorial = leerCadena("Editorial: ");
        int anio = leerEntero("Año de publicación: ");
        miBiblioteca.nuevoLibro(titulo, edicion, editorial, anio);
    }

    private static void quitarLibro() {
        String titulo = leerCadena("Título del libro a quitar: ");
        int edicion = leerEntero("Edición del libro a quitar: ");
        String editorial = leerCadena("Editorial del libro a quitar: ");
        int anio = leerEntero("Año de publicación del libro a quitar: ");

        // Se crea un objeto 'dummy' para usarlo en la búsqueda
        Libro libroAEliminar = new Libro(titulo, edicion, editorial, anio, new ArrayList<>()); // no sé si va o no el new ArrayList
        miBiblioteca.quitarLibro(libroAEliminar);
    }

    // --- Submenú Préstamos y Devoluciones (Opción 3) ---

    private static void menuGestionPrestamos() {
        int opcion;
        do {
            System.out.println("\n===== 🔄 Préstamos y Devoluciones =====");
            System.out.println("1. Realizar Préstamo");
            System.out.println("2. Devolver Libro");
            System.out.println("3. Verificar Habilitación de Socio");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("-----------------------------------------");

            opcion = leerEntero("Ingrese una opción (0 para volver): ");

            switch (opcion) {
                case 1:
                    realizarPrestamo();
                    break;
                case 2:
                    devolverLibro();
                    break;
                case 3:
                    verificarHabilitacionSocio();
                    break;
                case 0:
                    System.out.println("↩️ Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
            if (opcion != 0) pausar();
        } while (opcion != 0);
    }

    private static void realizarPrestamo() {
        int dni = leerEntero("DNI del Socio: ");
        String titulo = leerCadena("Título del Libro a prestar: ");
        int edicion = leerEntero("Edición del Libro: ");
        String editorial = leerCadena("Editorial del Libro: ");
        int anio = leerEntero("Año del Libro: ");

        Socio socio = miBiblioteca.buscarSocio(dni);
        // Se busca el libro en la biblioteca (solo el primero que coincida)
        Libro libroAPrestar = buscarLibroEnLista(titulo, edicion, editorial, anio);

        if (socio == null) {
            System.out.println("🚫 Socio con DNI " + dni + " no encontrado.");
            return;
        }
        if (libroAPrestar == null) {
            System.out.println("🚫 Libro no encontrado en la biblioteca.");
            return;
        }

        // Se usa la fecha actual para el retiro
        Calendar fechaRetiro = new GregorianCalendar();

        if (miBiblioteca.prestarLibro(fechaRetiro, socio, libroAPrestar)) {
            System.out.println("✅ Préstamo realizado con éxito.");
            System.out.println("Socio: " + socio.getNombre() + " | Libro: " + libroAPrestar.getTitulo());
            System.out.println("Días límite de préstamo: " + socio.getDiasPrestamo() + " días.");
        } else {
            System.out.println("❌ Préstamo **NO** realizado.");
            if (!socio.puedePedir()) {
                System.out.println("   Razón: El socio no está habilitado para pedir.");
            }
            if (libroAPrestar.prestado()) {
                System.out.println("   Razón: El libro ya está prestado.");
            }
        }
    }

    private static void devolverLibro() {
        String titulo = leerCadena("Título del Libro a devolver: ");
        int edicion = leerEntero("Edición del Libro: ");
        String editorial = leerCadena("Editorial del Libro: ");
        int anio = leerEntero("Año del Libro: ");

        // Se busca el libro en la biblioteca (solo el primero que coincida)
        Libro libroADevolver = buscarLibroEnLista(titulo, edicion, editorial, anio);

        if (libroADevolver == null) {
            System.out.println("🚫 Libro no encontrado en la biblioteca.");
            return;
        }

        try {
            miBiblioteca.devolverLibro(libroADevolver);
            System.out.println("✅ Devolución de \"" + libroADevolver.getTitulo() + "\" registrada con éxito.");
        } catch (LibroNoPrestadoException e) {
            System.out.println("❌ Error en la devolución: " + e.getMessage());
        }
    }

    private static void verificarHabilitacionSocio() {
        int dni = leerEntero("DNI del Socio a verificar: ");
        Socio socio = miBiblioteca.buscarSocio(dni);

        if (socio == null) {
            System.out.println("🚫 Socio no encontrado.");
            return;
        }

        System.out.println("\n--- Estado de Habilitación de " + socio.getNombre() + " (" + socio.soyDeLaClase() + ") ---");

        if (socio.puedePedir()) {
            System.out.println("✅ ¡El socio está **habilitado** para pedir un nuevo libro!");
        } else {
            System.out.println("🚫 El socio **NO** está habilitado para pedir un nuevo libro.");

            if (socio instanceof Estudiante estudiante) {
                // Lógica de deshabilitación específica de Estudiante (3+ libros o vencido con 20 días)
                if (estudiante.cantLibrosPrestados() > 3) {
                    System.out.println("   Razón: Excede el límite de 3 libros prestados (" + estudiante.cantLibrosPrestados() + " actualmente).");
                }
                // Si el estudiante no está habilitado, podría ser por vencimiento (aunque ya lo chequea `puedePedir()`)
                // Para probar el vencimiento de estudiante, se necesita simular una fecha.
            } else {
                // Lógica de Docente/Socio base (vencido según sus días de préstamo)
                System.out.println("   Razón: Tiene al menos un préstamo vencido (comparado con sus " + socio.getDiasPrestamo() + " días límite).");
            }
        }
        System.out.println("Libros prestados actualmente: " + socio.cantLibrosPrestados());
    }

    // --- Submenú Consultas e Informes (Opción 4) ---

    private static void menuConsultas() {
        int opcion;
        do {
            System.out.println("\n===== 🔍 Consultas e Informes =====");
            System.out.println("1. Listar Préstamos Vencidos");
            System.out.println("2. Listar Docentes Responsables");
            System.out.println("3. Cantidad de Socios por Tipo");
            System.out.println("4. ¿Quién tiene un libro específico?");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("-------------------------------------");

            opcion = leerEntero("Ingrese una opción (0 para volver): ");

            switch (opcion) {
                case 1:
                    listarPrestamosVencidos();
                    break;
                case 2:
                    System.out.println("\n--- Docentes Responsables ---");
                    System.out.println(miBiblioteca.listaDeDocentesResponsables());
                    break;
                case 3:
                    contarSociosPorTipo();
                    break;
                case 4:
                    quienTieneLibro();
                    break;
                case 0:
                    System.out.println("↩️ Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
            if (opcion != 0) pausar();
        } while (opcion != 0);
    }

    private static void listarPrestamosVencidos() {
        System.out.println("\n--- Préstamos Vencidos (al día de hoy) ---");
        // Esta línea ahora funciona gracias al 'import java.util.ArrayList;'
        ArrayList<Prestamo> vencidos = miBiblioteca.prestamosVencidos();

        if (vencidos.isEmpty()) {
            System.out.println("🎉 ¡No hay préstamos vencidos! 🎉");
            return;
        }

        for (int i = 0; i < vencidos.size(); i++) {
            Prestamo p = vencidos.get(i);
            System.out.println((i + 1) + ". " + p.getLibro().getTitulo() + " | Socio: " + p.getSocio().getNombre() + " | Retiro: " + formatoFecha(p.getFechaRetiro()));
        }
    }

    private static void contarSociosPorTipo() {
        System.out.println("\n--- Cantidad de Socios por Tipo ---");
        int estudiantes = miBiblioteca.cantidadDeSociosPorTipo("Estudiante");
        int docentes = miBiblioteca.cantidadDeSociosPorTipo("Docente");
        System.out.println("Estudiantes: " + estudiantes);
        System.out.println("Docentes: " + docentes);
    }

    private static void quienTieneLibro() {
        String titulo = leerCadena("Título del Libro a consultar: ");
        int edicion = leerEntero("Edición del Libro: ");
        String editorial = leerCadena("Editorial del Libro: ");
        int anio = leerEntero("Año del Libro: ");

        // Se busca el libro en la lista de la biblioteca
        Libro libroBuscado = buscarLibroEnLista(titulo, edicion, editorial, anio);

        if (libroBuscado == null) {
            System.out.println("🚫 Libro no encontrado en la biblioteca.");
            return;
        }

        try {
            String resultado = miBiblioteca.quienTieneElLibro(libroBuscado);
            // El método de la clase Biblioteca tiene un error en el retorno y añade el título dos veces.
            // Para fines de la prueba, se muestra lo que devuelve (ej: "El libro está en posesión de: [nombre][título]")
            System.out.println("Resultado de la consulta: ");
            System.out.println("  " + resultado);

            // Si el libro está prestado, su último préstamo tiene un socio asociado.
            if (libroBuscado.prestado()) {
                System.out.println("  Mejor respuesta: " + libroBuscado.ultimoPrestamo().getSocio().getNombre());
            }

        } catch (LibroNoPrestadoException e) {
            System.out.println("ℹ️  " + e.getMessage());
        }
    }

    // --- Métodos Auxiliares de Lectura y Búsqueda ---

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("❌ Entrada no válida. Ingrese un número entero.");
            scanner.next(); // consumir la entrada inválida
            System.out.print(mensaje);
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // consumir el '\n' restante
        return valor;
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static void pausar() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Busca el primer libro en la lista de la biblioteca que coincide con los datos.
     */
    private static Libro buscarLibroEnLista(String titulo, int edicion, String editorial, int anio) {
        for (Libro libro : miBiblioteca.getLibros()) {
            if (libro.getTitulo().equalsIgnoreCase(titulo) &&
                    libro.getEdicion() == edicion &&
                    libro.getEditorial().equalsIgnoreCase(editorial) &&
                    libro.getAnio() == anio) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Devuelve la fecha en formato simple (solo día/mes/año).
     */
    private static String formatoFecha(Calendar p_fecha) {
        if (p_fecha == null) return "N/A";
        // Nota: Los meses en Calendar van de 0 (Enero) a 11 (Diciembre).
        return p_fecha.get(Calendar.DAY_OF_MONTH) + "/" +
                (p_fecha.get(Calendar.MONTH) + 1) + "/" +
                p_fecha.get(Calendar.YEAR);
    }
}
