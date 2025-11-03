import java.util.ArrayList;
import java.util.Calendar;

/**
 * Clase que representa una biblioteca que gestiona libros, socios y prestamos.
 */
public class Biblioteca {
    private String nombre;
    private ArrayList<Prestamo> prestamos;
    private ArrayList<Libro> libros;
    private ArrayList<Socio> socios;
    private ArrayList<Socio> documentosSocios;
    private ArrayList<Socio> docentesResponsables;

    /**
     * Constructor de la clase Biblioteca.
     * @param nombre Nombre de la biblioteca
     */
    public Biblioteca(String nombre) {
        this.nombre = nombre;
        this.prestamos = new ArrayList<>();
        this.libros = new ArrayList<>();
        this.socios = new ArrayList<>();
        this.documentosSocios = new ArrayList<>();
        this.docentesResponsables = new ArrayList<>();
    }

    // **** Getters y Setters
    /**
     * Obtiene el nombre de la biblioteca.
     * @return Nombre de la biblioteca
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la biblioteca.
     * @param nombre Nuevo nombre de la biblioteca
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de prestamos.
     * @return Lista de prestamos
     */
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    /**
     * Establece la lista de prestamos.
     * @param prestamos Nueva lista de prestamos
     */
    public void setPrestamos(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    /**
     * Obtiene la lista de libros.
     * @return Lista de libros
     */
    public ArrayList<Libro> getLibros() {
        return libros;
    }

    /**
     * Establece la lista de libros.
     * @param libros Nueva lista de libros
     */
    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

    /**
     * Obtiene la lista de socios.
     * @return Lista de socios
     */
    public ArrayList<Socio> getSocios() {
        return socios;
    }

    /**
     * Establece la lista de socios.
     * @param socios Nueva lista de socios
     */
    public void setSocios(ArrayList<Socio> socios) {
        this.socios = socios;
    }

    /**
     * Obtiene la lista de documentos de socios.
     * @return Lista de documentos de socios
     */
    public ArrayList<Socio> getDocumentosSocios() {
        return documentosSocios;
    }

    /**
     * Establece la lista de documentos de socios.
     * @param documentosSocios Nueva lista de documentos de socios
     */
    public void setDocumentosSocios(ArrayList<Socio> documentosSocios) {
        this.documentosSocios = documentosSocios;
    }

    /**
     * Obtiene la lista de docentes responsables.
     * @return Lista de docentes responsables
     */
    public ArrayList<Socio> getDocentesResponsables() {
        return docentesResponsables;
    }

    /**
     * Establece la lista de docentes responsables.
     * @param docentesResponsables Nueva lista de docentes responsables
     */
    public void setDocentesResponsables(ArrayList<Socio> docentesResponsables) {
        this.docentesResponsables = docentesResponsables;
    }

    /**
     * Crea un nuevo libro y lo agrega a la biblioteca.
     * @param titulo Titulo del libro
     * @param edicion Numero de edicion
     * @param editorial Editorial del libro
     * @param anio Año de publicacion
     */
    public void nuevoLibro(String titulo, int edicion, String editorial, int anio) {
        Libro libro = new Libro(titulo, edicion, editorial, anio);
        libros.add(libro);
    }

    /**
     * Crea un nuevo socio estudiante y lo agrega a la lista de socios.
     * @param dniSocio DNI del estudiante
     * @param nombre Nombre del estudiante
     * @param carrera Carrera que cursa el estudiante
     */
    public void nuevoSocioEstudiante(int dniSocio, String nombre, String carrera) {
        Estudiante estudiante = new Estudiante(dniSocio, nombre, carrera);
        socios.add(estudiante);
    }

    /**
     * Crea un nuevo socio docente y lo agrega a la lista de socios.
     * @param dniSocio DNI del docente
     * @param nombre Nombre del docente
     * @param area Area en la que trabaja el docente
     */
    public void nuevoSocioDocente(int dniSocio, String nombre, String area) {
        Docente docente = new Docente(dniSocio, nombre, area);
        socios.add(docente);
    }

    /**
     * Registra un prestamo de un libro a un socio.
     * @param fechaRetiro Fecha en que se retira el libro
     * @param socio Socio que solicita el prestamo
     * @param libro Libro a prestar
     * @return true si el prestamo se realizo exitosamente, false en caso contrario
     */
    public boolean prestarLibro(Calendar fechaRetiro, Socio socio, Libro libro) {
        if (!socio.puedePedir() || libro.prestado()) {
            return false;
        }

        Prestamo prestamo = new Prestamo(fechaRetiro);
        prestamo.registrarFechaDevolucion(fechaRetiro);

        libro.ultimoPrestamo(prestamo);
        prestamos.add(prestamo);

        return true;
    }

    /**
     * Registra la devolucion de un libro.
     * @param libro Libro a devolver
     */
    public void devolverLibro(Libro libro) {
        if (libro.prestado()) {
            Prestamo prestamo = libro.ultimoPrestamo();
            prestamo.devolucion(Calendar.getInstance());
        }
    }

    /**
     * Cuenta la cantidad de socios por tipo.
     * @param objeto Tipo de socio ("Estudiante" o "Docente")
     * @return Cantidad de socios del tipo especificado
     */
    public int cantidadDeSociosPorTipo(String objeto) {
        int count = 0;
        for (Socio socio : socios) {
            if (objeto.equals("Estudiante") && socio instanceof Estudiante) {
                count++;
            } else if (objeto.equals("Docente") && socio instanceof Docente) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtiene la lista de prestamos vencidos.
     * @return Lista de prestamos vencidos
     */
    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            if (prestamo.vencido(Calendar.getInstance())) {
                vencidos.add(prestamo);
            }
        }
        return vencidos;
    }

    /**
     * Busca un socio por su DNI.
     * @param dni DNI del socio a buscar
     * @return Socio encontrado o null si no existe
     */
    public Socio buscarSocio(int dni) {
        for (Socio socio : socios) {
            if (socio.getDniSocio() == dni) {
                return socio;
            }
        }
        return null;
    }

    /**
     * Genera una lista con los titulos de todos los libros.
     * @return String con los titulos de los libros
     */
    public String listaDeTitulos() {
        StringBuilder titulos = new StringBuilder();
        for (Libro libro : libros) {
            titulos.append(libro.toString()).append("\n");
        }
        return titulos.toString();
    }

    /**
     * Genera una lista con todos los libros de la biblioteca.
     * @return String con la informacion de todos los libros
     */
    public String listaDeLibros() {
        StringBuilder lista = new StringBuilder();
        for (Libro libro : libros) {
            lista.append(libro.toString()).append("\n");
        }
        return lista.toString();
    }

    /**
     * Genera una lista con los docentes responsables.
     * @return String con la informacion de los docentes responsables
     */
    public String listaDeDocentesResponsables() {
        StringBuilder lista = new StringBuilder();
        for (Socio socio : docentesResponsables) {
            if (socio instanceof Docente) {
                lista.append(socio.toString()).append("\n");
            }
        }
        return lista.toString();
    }

    /**
     * Determina quien tiene prestado un libro.
     * @param libro Libro a consultar
     * @return String con la informacion del socio que tiene el libro o mensaje de error
     */
    public String quienTieneElLibro(Libro libro) {
        if (!libro.prestado()) {
            return "El libro no esta prestado";
        }

        for (Socio socio : socios) {
            Prestamo ultimoPrestamo = libro.ultimoPrestamo();
            if (ultimoPrestamo != null && !ultimoPrestamo.vencido(Calendar.getInstance())) {
                return socio.toString();
            }
        }
        return "No se encontra el socio";
    }
}
