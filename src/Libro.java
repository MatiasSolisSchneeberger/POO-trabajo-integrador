import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;

/**
 * Representa a un libro, con sus datos bibliográficos (título, edición, editorial, año)
 * y gestiona su historial de préstamos.
 *
 * @author ...
 * @version 1.0
 */
public class Libro implements java.io.Serializable{
    private String titulo;
    private int edicion;
    private String editorial;
    private int anio;
    private ArrayList<Prestamo> prestamos;

    /**
     * Constructor para inicializar un objeto Libro con todos sus atributos.
     *
     * @param p_titulo Título del libro.
     * @param p_edicion Número de edición.
     * @param p_editorial Editorial del libro.
     * @param p_anio Año de publicación.
     * @param p_prestamos Lista (posiblemente vacía) de préstamos históricos.
     */
    Libro(String p_titulo,
          int p_edicion,
          String p_editorial,
          int p_anio,
          ArrayList<Prestamo> p_prestamos) {
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(p_prestamos);
    }

    /**
     * Obtiene el título del libro.
     *
     * @return El título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo El nuevo título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el número de edición del libro.
     *
     * @return El número de edición.
     */
    public int getEdicion() {
        return edicion;
    }

    /**
     * Establece el número de edición del libro.
     *
     * @param edicion El nuevo número de edición.
     */
    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    /**
     * Obtiene la editorial del libro.
     *
     * @return La editorial del libro.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Establece la editorial del libro.
     *
     * @param editorial La nueva editorial.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Obtiene el año de publicación del libro.
     *
     * @return El año de publicación.
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Establece el año de publicación del libro.
     *
     * @param anio El nuevo año de publicación.
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Obtiene la lista de préstamos históricos del libro.
     *
     * @return Un ArrayList de objetos Prestamo.
     */
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    /**
     * Establece la lista de préstamos del libro.
     *
     * @param prestamos La nueva lista de préstamos.
     */
    public void setPrestamos(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    /**
     * Añade un nuevo préstamo al historial del libro.
     *
     * @param p_prestamo El préstamo a agregar.
     * @return true si el préstamo fue agregado exitosamente.
     */
    public boolean agregarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().add(p_prestamo);
    }

    /**
     * Elimina un préstamo específico del historial.
     *
     * @param p_prestamo El préstamo a eliminar.
     * @return true si el préstamo fue encontrado y eliminado.
     */
    public boolean quitarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().remove(p_prestamo);
    }

    /**
     * Devuelve el último préstamo registrado para este libro.
     * <p>
     * <b>Advertencia:</b> La lógica original estaba invertida. Esta implementación
     * asume la lógica corregida: devuelve el último préstamo si la lista no
     * está vacía, o null en caso contrario.
     *
     * @return El último préstamo en la lista, o null si no hay préstamos.
     */
    public Prestamo ultimoPrestamo() {
        return this.getPrestamos().isEmpty() ?
                null :
                this.getPrestamos().getLast();

    }

    /**
     * Verifica si el libro está actualmente prestado.
     * <p>
     * Un libro se considera prestado si su último préstamo existe (no es nulo),
     * no está vencido (según la fecha actual) y aún no ha sido devuelto
     * (la fecha de devolución es nula).
     *
     * @return true si el libro está actualmente prestado, false en caso contrario.
     */
    public boolean prestado() {
        if (this.getPrestamos().isEmpty()) {
            return false;
        } else {
            Prestamo ultimo = this.ultimoPrestamo();

            return (ultimo != null &&
                    !ultimo.vencido(Calendar.getInstance()) &&
                    ultimo.getFechaDevolucion() == null);
        }
    }

    /**
     * Devuelve una representación en cadena de texto del libro,
     * mostrando su título.
     *
     * @return Una cadena con el formato "Titulo [El Título]".
     */
    @Override
    public String toString() {
        return "Titulo " + this.getTitulo();
    }
}
