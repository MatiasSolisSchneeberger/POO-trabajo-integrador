import java.util.ArrayList;
import java.util.Calendar;

public class Libro {
    
    // ------------------ Atributos ------------------
    private String titulo;
    private int edicion;
    private String editorial;
    private int anio;
    private ArrayList<Prestamo> prestamos;

    // ------------------ Constructor ------------------
    
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        this.titulo = p_titulo;
        this.edicion = p_edicion;
        this.editorial = p_editorial;
        this.anio = p_anio;
        this.prestamos = new ArrayList<Prestamo>();
    }

    // ------------------ Getters ------------------
    
    public String getTitulo() {
        return this.titulo;
    }

    public int getEdicion() {
        return this.edicion;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public int getAnio() {
        return this.anio;
    }

    public ArrayList<Prestamo> getPrestamos() {
        return this.prestamos;
    }
    
        // ------------------ Setters ------------------
    public void setTitulo(String p_titulo) {
        this.titulo = p_titulo;
    }

    public void setEdicion(int p_edicion) {
        this.edicion = p_edicion;
    }

    public void setEditorial(String p_editorial) {
        this.editorial = p_editorial;
    }

    public void setAnio(int p_anio) {
        this.anio = p_anio;
    }

    public void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }
    // ------------------ Métodos principales ------------------
    /**
     * Quitar prestamo de la lista de prestamos
     */
    public void quitarPrestamo(Prestamo p_prestamo) {
        this.prestamos.remove(p_prestamo);
    }

    /**
     * Devuelve true si el libro está actualmente prestado
     */
    public boolean prestado() {
        if (this.prestamos.isEmpty()) {
            return false;
        }
        Prestamo ultimo = this.ultimoPrestamo();
        return (ultimo != null && !ultimo.vencido(Calendar.getInstance()) && ultimo.getFechaDevolucion() == null);
    }

    /**
     * Devuelve el último préstamo registrado del libro.
     * Si no tiene préstamos, retorna null.
     */
    public Prestamo ultimoPrestamo() {
        if (this.prestamos.isEmpty()) {
            return null;
        }
        return this.prestamos.get(this.prestamos.size() - 1);
    }

    /**
     * Registra un nuevo préstamo del libro si está disponible.
     * Retorna true si se pudo prestar, false si ya estaba ocupado.
     */
    public boolean prestar(Prestamo p_prestamo) {
        if (this.estaDisponible()) {
            this.prestamos.add(p_prestamo);
            return true;
        }
        return false;
    }

    /**
     * Marca el libro como devuelto, registrando la fecha en su último préstamo.
     */
    public void devolver(java.util.Calendar p_fechaDevolucion) {
        Prestamo ultimo = this.ultimoPrestamo();
        if (ultimo != null && ultimo.getFechaDevolucion() == null) {
            ultimo.registrarFechaDevolucion(p_fechaDevolucion);
        }
    }

    /**
     * Retorna true si el libro no está prestado actualmente.
     */
    public boolean estaDisponible() {
        return !this.prestado();
    }

    /**
     * Devuelve una descripción del libro.
     */
    public String toString() {
        return this.titulo + " (" + this.edicion + "ª edición, " +
               this.editorial + ", " + this.anio + ")";
    }
}
