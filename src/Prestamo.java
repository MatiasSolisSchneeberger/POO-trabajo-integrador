import java.util.Calendar;
import java.io.Serializable;
/**
 * Representa un préstamo de un libro a un socio de la biblioteca.
 * Contiene información sobre el socio, el libro, la fecha en que se realizó
 * el préstamo y la fecha en que se devolvió (si ya se ha devuelto).
 *
 * @author Matia Solis Schneeberger
 * @version 1.0.0
 */
public class Prestamo implements java.io.Serializable{
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;

    //Constructor

    /**
     * Crea un nuevo objeto Prestamo.
     * La fecha de devolución se inicializa automáticamente como null.
     *
     * @param p_fechaRetiro La fecha (Calendar) en que se retira el libro.
     * @param p_socio       El objeto Socio que retira el libro.
     * @param p_libro       El objeto Libro que es prestado.
     */
    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(null);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }

    // Accesores

    /**
     * Obtiene la fecha de retiro del libro.
     *
     * @return La fecha (Calendar) de retiro.
     */
    public Calendar getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * Establece la fecha de retiro (método privado).
     *
     * @param p_fechaRetiro La fecha de retiro.
     */
    private void setFechaRetiro(Calendar p_fechaRetiro) {
        this.fechaRetiro = p_fechaRetiro;
    }

    /**
     * Obtiene la fecha de devolución del libro.
     *
     * @return La fecha (Calendar) de devolución, o {@code null} si aún no se ha devuelto.
     */
    public Calendar getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Establece la fecha de devolución (método privado).
     * Usar {@link #registrarFechaDevolucion(Calendar)} para acceso público.
     *
     * @param p_fechaDevolucion La fecha de devolución.
     */
    private void setFechaDevolucion(Calendar p_fechaDevolucion) {
        this.fechaDevolucion = p_fechaDevolucion;
    }

    /**
     * Obtiene el socio que realizó el préstamo.
     *
     * @return El objeto Socio.
     */
    public Socio getSocio() {
        return socio;
    }

    /**
     * Establece el socio del préstamo (método privado).
     *
     * @param p_socio El socio.
     */
    private void setSocio(Socio p_socio) {
        this.socio = p_socio;
    }

    /**
     * Obtiene el libro prestado.
     *
     * @return El objeto Libro.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Establece el libro del préstamo (método privado).
     *
     * @param p_libro El libro.
     */
    private void setLibro(Libro p_libro) {
        this.libro = p_libro;
    }

    // Metodos


    // TODO: Creo que es así
    /**
     * Registra la fecha en que el libro fue devuelto.
     * Este metodo actualiza el atributo {@code fechaDevolucion}.
     *
     * @param p_fecha La fecha (Calendar) de devolución.
     */
    public void registrarFechaDevolucion(Calendar p_fecha) {
        this.setFechaDevolucion(p_fecha);
    }

    // TODO: no sé como hacer esto. Creo que es así
    public boolean vencido(Calendar p_fecha) {
        int dias = this.getSocio().getDiasPrestamo();
        // Se clona la fecha de retiro para no modificar el estado original del objeto Prestamo
        Calendar retiro = (Calendar) this.getFechaRetiro().clone();
        retiro.add(Calendar.DATE, dias); // Se calcula la fecha límite de devolución
        return p_fecha.after(retiro); // Se comprueba si la fecha actual es posterior a la fecha límite
    }

    /**
     * Devuelve una representación en cadena (String) del préstamo.
     *
     * @return Un String con los detalles del préstamo.
     */
    @Override
    public String toString() {
        return "Retiro: " + this.getFechaRetiro() + " - Devolución" + this.getFechaDevolucion() + "\n" +
                "Libro: " + this.getLibro() + "\n" +
                "Socio: " + this.getSocio().getNombre();
    }
}
