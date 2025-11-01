import java.util.Calendar;

public class Prestamo {
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;

    //Constructor

    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(null);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }

    // Accesores

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

    // Metodos
    // TODO: no sé si es así
    public void registrarFechaDevolucion(Calendar p_fecha) {
        this.setFechaDevolucion(p_fecha);
    }


    // TODO: preguntar como **** se hace esto...
/*    public boolean vencido(Calendar p_fecha) {
        return p_fecha.after(this.getFechaDevolucion());
    }*/

    // Gemini me dijo que es así
    public boolean vencido(Calendar p_fecha) {

        // 1. Obtenemos los días de préstamo del socio de este préstamo.
        int diasDePrestamo = this.getSocio().getDiasPrestamo();

        // 2. ¡IMPORTANTE! Clonamos la fecha de retiro.
        // Si no la clonas, el .add() modificará la fecha original del préstamo.
        Calendar fechaVencimiento = (Calendar) this.getFechaRetiro().clone();

        // 3. Le sumamos los días de préstamo a la fecha clonada.
        // Esto nos da la fecha LÍMITE de devolución.
        fechaVencimiento.add(Calendar.DAY_OF_YEAR, diasDePrestamo);

        // 4. Comparamos si la fecha de hoy (p_fecha) es posterior a la fecha límite.
        // p_fecha.after(fechaVencimiento) se traduce como: "¿Hoy es después del vencimiento?"
        return p_fecha.after(fechaVencimiento);
    }

    public String toString() {
        return "Retiro: " + this.getFechaRetiro() + " - Devolución" + this.getFechaDevolucion() + "\n" +
                "Libro: " + this.getLibro() + "\n" +
                "Socio: " + this.getSocio().getNombre();
    }
}
