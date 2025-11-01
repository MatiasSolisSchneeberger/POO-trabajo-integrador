import java.util.ArrayList;
import java.util.Calendar;

public abstract class Socio {
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;

    // constructor

    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<>());
    }

    // accesores

    public int getDniSocio() {
        return dniSocio;
    }

    private void setDniSocio(int p_dniSocio) {
        this.dniSocio = p_dniSocio;
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    private void setDiasPrestamo(int p_diasPrestamo) {
        this.diasPrestamo = p_diasPrestamo;
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    private void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }

    public boolean agregarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().add(p_prestamo);
    }

    public boolean quitarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().remove(p_prestamo);
    }

    // metodos

    public int cantLibrosPrestados() {
        return this.getPrestamos().size();
    }

    public String toString() {
        return "D.N.I.: " + this.getDniSocio() +
                "||" + this.getNombre() +
                " (" + this.soyDeLaClase() + ")" +
                "|| Libros Prestados: " + this.cantLibrosPrestados();
    }

    public boolean puedePedir() {
        Calendar hoy = Calendar.getInstance();

        for (Prestamo prestamo : this.getPrestamos()) {

            if (prestamo.vencido(hoy)) {
                return false;
            }
        }

        return true;
    }

    public abstract String soyDeLaClase();
}
