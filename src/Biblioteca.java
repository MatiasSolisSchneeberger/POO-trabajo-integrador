import java.util.*; // Calendar, GregorianCalendar, ArrayList, HashMap, Map, Iterator, Set, LinkedHashSet

/**
 * Clase Biblioteca con persistencia SQLite completa.
 */
public class Biblioteca {

    // ================= Atributos
    private String nombre;
    private HashMap<Integer, Socio> socios;
    private ArrayList<Libro> libros;

    // ================= Constructores

    public Biblioteca(String p_nombre) {
        this.setNombre(p_nombre);
        
        // BD: Inicialización y carga de datos
        GestorBD.inicializarTablas();
        System.out.println("--- Iniciando carga de datos ---");
        this.setSocios(GestorBD.cargarSocios());
        this.setLibros(GestorBD.cargarLibros());
        // IMPORTANTE: Cargar préstamos al final para enlazar socios y libros
        GestorBD.cargarPrestamos(this.socios, this.libros);
        
        System.out.println("Biblioteca lista: " + this.socios.size() + " socios, " + 
                           this.libros.size() + " libros cargados.");
    }

    // Constructor alternativo (poco usado con persistencia, pero se mantiene por compatibilidad)
    public Biblioteca(String p_nombre, HashMap<Integer, Socio> p_socios, ArrayList<Libro> p_libros) {
        this.setNombre(p_nombre);
        this.setSocios(p_socios);
        this.setLibros(p_libros);
    }

    // ================= Setters (privados)
    private void setNombre(String p_nombre) { this.nombre = p_nombre; }
    private void setSocios(HashMap<Integer, Socio> p_Socios) { this.socios = p_Socios; }
    private void setLibros(ArrayList<Libro> p_libros) { this.libros = p_libros; }

    // ================= Getters
    public String getNombre() { return this.nombre; }
    public HashMap<Integer, Socio> getSocios() { return this.socios; }
    public ArrayList<Libro> getLibros() { return this.libros; }

    // ================= Métodos de gestión

    public void agregarSocio(Socio p_socio) {
        if (p_socio == null) return;
        if (this.getSocios().containsKey(p_socio.getDniSocio())) {
            System.out.println("El socio ya está registrado.");
        } else {
            this.getSocios().put(p_socio.getDniSocio(), p_socio);
            GestorBD.guardarSocio(p_socio); // BD: Guardar
            System.out.println("Socio registrado correctamente.");
        }
    }

    public void quitarSocio(Socio p_socio) {
        if (p_socio == null) return;
        if (this.getSocios().remove(p_socio.getDniSocio()) != null) {
            GestorBD.eliminarSocio(p_socio.getDniSocio()); // BD: Eliminar
            System.out.println("Se dio de baja al socio: " + p_socio.getNombre());
        } else {
            System.out.println("El socio no existe.");
        }
    }

    // Cambiado a público para facilitar su uso si es necesario
    public void agregarLibro(Libro p_libro) {
        if (p_libro == null) return;
        this.getLibros().add(p_libro);
        GestorBD.guardarLibro(p_libro); // BD: Guardar
        System.out.println("Libro agregado a la biblioteca.");
    }

    public void quitarLibro(Libro libroAEliminar) {
        if (libroAEliminar == null) return;
        boolean encontrado = false;
        Iterator<Libro> it = this.getLibros().iterator();
        while (it.hasNext()) {
            Libro libro = it.next();
            // Comparación por clave compuesta (título, edición, año, editorial)
            if (libro.getTitulo().equals(libroAEliminar.getTitulo()) &&
                libro.getEdicion() == libroAEliminar.getEdicion() &&
                libro.getEditorial().equals(libroAEliminar.getEditorial()) &&
                libro.getAnio() == libroAEliminar.getAnio()) {
                
                if (!libro.prestado()) {
                    it.remove();
                    GestorBD.eliminarLibro(libro); // BD: Eliminar
                    System.out.println("Libro eliminado correctamente.");
                    encontrado = true;
                } else {
                     System.out.println("No se puede eliminar: el libro está prestado.");
                     encontrado = true;
                }
                break;
            }
        }
        if (!encontrado) {
            System.out.println("El libro no se encuentra en la biblioteca.");
        }
    }

    // ================= Préstamos / Devoluciones

    public boolean prestarLibro(Calendar p_FechaRetiro, Socio p_Socio, Libro p_libro) {
        if (p_FechaRetiro == null || p_Socio == null || p_libro == null) return false;

        // Asegurar que usamos las referencias reales de la biblioteca
        Socio socioReal = this.getSocios().get(p_Socio.getDniSocio());
        // Buscar el libro real en la lista
        Libro libroReal = null;
        for(Libro l : this.libros) {
            if(l.getTitulo().equals(p_libro.getTitulo()) && l.getEdicion() == p_libro.getEdicion()) {
                libroReal = l;
                break;
            }
        }

        if (socioReal == null || libroReal == null) {
            System.out.println("Error: Socio o libro no encontrados en el sistema.");
            return false;
        }
        
        if (!socioReal.puedePedir()) {
            System.out.println("El socio no puede pedir más libros (límite alcanzado o deuda).");
            return false;
        }
        if (libroReal.prestado()) {
            System.out.println("El libro ya está prestado.");
            return false;
        }

        // Crear y registrar el préstamo
        Prestamo nuevoPrestamo = new Prestamo(p_FechaRetiro, socioReal, libroReal);
        libroReal.agregarPrestamo(nuevoPrestamo);
        socioReal.agregarPrestamo(nuevoPrestamo);

        GestorBD.guardarPrestamo(nuevoPrestamo); // BD: Guardar préstamo
        System.out.println("Préstamo registrado con éxito.");
        return true;
    }

    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (p_libro == null) return;
        
        // Buscar la referencia real del libro en la biblioteca
        Libro libroReal = null;
        for(Libro l : this.libros) {
             if(l.getTitulo().equals(p_libro.getTitulo()) && l.getEdicion() == p_libro.getEdicion()) {
                libroReal = l;
                break;
            }
        }
        
        if (libroReal == null || !libroReal.prestado()) {
            throw new LibroNoPrestadoException("El libro " + p_libro.getTitulo() + " no está prestado actualmente.");
        }

        Calendar fechaActual = Calendar.getInstance();
        Prestamo prestamoActual = libroReal.ultimoPrestamo();
        prestamoActual.registrarFechaDevolucion(fechaActual);

        GestorBD.actualizarDevolucion(prestamoActual); // BD: Actualizar devolución
        System.out.println("Devolución registrada correctamente.");
    }

    // ================= Consultas (Sin cambios mayores, usan la memoria RAM)

    public int cantidadDeSociosPorTipo(String p_Objeto) {
        if (p_Objeto == null) return 0;
        int contador = 0;
        for (Socio s : this.getSocios().values()) {
            if (s.soyDeLaClase().equalsIgnoreCase(p_Objeto)) contador++;
        }
        return contador;
    }

    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<>();
        Calendar hoy = new GregorianCalendar();
        for (Socio s : this.getSocios().values()) {
            for (Prestamo p : s.getPrestamos()) {
                if (p.getFechaDevolucion() == null && p.vencido(hoy)) {
                    vencidos.add(p);
                }
            }
        }
        return vencidos;
    }

    public ArrayList<Docente> docentesResponsables() {
        ArrayList<Docente> responsables = new ArrayList<>();
        for (Socio s : this.getSocios().values()) {
            if (s instanceof Docente && ((Docente) s).esResponsable()) {
                responsables.add((Docente) s);
            }
        }
        return responsables;
    }

    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException {
        for (Libro l : this.getLibros()) {
            if (l.getTitulo().equalsIgnoreCase(p_libro.getTitulo()) && l.getEdicion() == p_libro.getEdicion()) {
                if (l.prestado()) {
                    return "El libro lo tiene: " + l.ultimoPrestamo().getSocio().getNombre();
                } else {
                    throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
                }
            }
        }
        throw new LibroNoPrestadoException("El libro no está registrado en la biblioteca.");
    }

    public Socio buscarSocio(int p_dni) {
        return this.getSocios().get(p_dni);
    }

    // ================= Listados

    public String listaDeSocios() {
        if (this.getSocios().isEmpty()) return "No hay socios registrados.\n";
        StringBuilder sb = new StringBuilder("--- Lista de Socios ---\n");
        for (Socio s : this.getSocios().values()) {
            sb.append(s.toString()).append("\n");
        }
        sb.append("******\n")
          .append("Estudiantes: ").append(this.cantidadDeSociosPorTipo("Estudiante")).append("\n")
          .append("Docentes: ").append(this.cantidadDeSociosPorTipo("Docente")).append("\n")
          .append("******");
        return sb.toString();
    }

    public String listaDeLibros() {
        if (this.getLibros().isEmpty()) return "No hay libros registrados.\n";
        StringBuilder sb = new StringBuilder("--- Lista de Libros ---\n");
        int i = 1;
        for (Libro l : this.getLibros()) {
            sb.append(i++).append(") ").append(l.getTitulo())
              .append(" [Ed.").append(l.getEdicion()).append("] ")
              .append("|| Prestado: ").append(l.prestado() ? "SI" : "NO").append("\n");
        }
        return sb.toString();
    }

    public String listaDeTitulos() {
        Set<String> titulos = new LinkedHashSet<>();
        for (Libro l : this.getLibros()) titulos.add(l.getTitulo());
        StringBuilder sb = new StringBuilder("--- Títulos únicos ---\n");
        for (String t : titulos) sb.append(t).append("\n");
        return sb.toString();
    }

    public String listaDeDocentesResponsables() {
        StringBuilder sb = new StringBuilder("--- Docentes Responsables ---\n");
        for (Docente d : this.docentesResponsables()) {
            sb.append(d.getNombre()).append(" || Préstamos activos: ").append(d.cantLibrosPrestados()).append("\n");
        }
        return sb.toString();
    }

    // ================= Altas rápidas

    public void nuevoLibro(String titulo, int edicion, String editorial, int anio) {
        this.agregarLibro(new Libro(titulo, edicion, editorial, anio, new ArrayList<>()));
    }

    public void nuevoSocioEstudiante(int dni, String nombre, String carrera) {
        this.agregarSocio(new Estudiante(dni, nombre, carrera));
    }

    public void nuevoSocioDocente(int dni, String nombre, String area) {
        this.agregarSocio(new Docente(dni, nombre, area));
    }
}
