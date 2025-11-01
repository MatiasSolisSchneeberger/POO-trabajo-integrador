import java.util.Calendar;

public class Prestamo {
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;

    public Prestamo(Calendar p_fechaRetiro, Calendar p_fechaDevolucion,
                    Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(p_fechaDevolucion);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }

    public Calendar getFechaRetiro() {
        return fechaRetiro;
    }

    private void setFechaRetiro(Calendar p_fechaRetiro) {
        this.fechaRetiro = p_fechaRetiro;
    }

    public Calendar getFechaDevolucion() {
        return fechaDevolucion;
    }

    private void setFechaDevolucion(Calendar p_fechaDevolucion) {
        this.fechaDevolucion = p_fechaDevolucion;
    }

    public Socio getSocio() {
        return socio;
    }

    private void setSocio(Socio p_socio) {
        this.socio = p_socio;
    }

    public Libro getLibro() {
        return libro;
    }

    private void setLibro(Libro p_libro) {
        this.libro = p_libro;
    }
}
