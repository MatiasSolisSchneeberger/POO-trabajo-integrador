import java.util.ArrayList;
import java.util.Calendar;

/**
 * Clase abstracta que representa a un socio de la biblioteca
 * Define el comportamiento y los atributos básicos comunes a todos los tipos de socios, como DNI, nombre y la lista de préstamos que tiene actualmente
 * @author Matias Solis Schneeberger
 * @version 1.0.0
 */
public abstract class Socio {
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;

    // constructor

    /**
     * Constructor de la clase abstracta Socio
     * Inicializa los datos del socio y crea una lista vacía para sus préstamos
     *
     * @param p_dniSocio     El DNI del socio
     * @param p_nombre       El nombre del socio
     * @param p_diasPrestamo El número de días de préstamo permitidos para este tipo de socio
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<>());
    }

    // accesores

    /**
     * Obtiene el DNI del socio
     *
     * @return El número de DNI
     */
    public int getDniSocio() {
        return dniSocio;
    }

    /**
     * Establece el DNI del socio
     *
     * @param p_dniSocio El número de DNI
     */
    private void setDniSocio(int p_dniSocio) {
        this.dniSocio = p_dniSocio;
    }

    /**
     * Obtiene el nombre del socio
     *
     * @return El nombre del socio
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del socio
     *
     * @param p_nombre El nombre del socio
     */
    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    /**
     * Obtiene la cantidad de días de préstamo permitidos para este socio
     *
     * @return El número de días
     */
    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    /**
     * Establece la cantidad de días de préstamo
     *
     * @param p_diasPrestamo El número de días
     */
    private void setDiasPrestamo(int p_diasPrestamo) {
        this.diasPrestamo = p_diasPrestamo;
    }

    /**
     * Obtiene la lista de préstamos activos del socio
     *
     * @return Un {@code ArrayList<Prestamo>} con los préstamos
     */
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    /**
     * Establece la lista de préstamos
     *
     * @param p_prestamos La lista de préstamos
     */
    private void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }

    /**
     * Añade un nuevo préstamo a la lista de préstamos del socio
     *
     * @param p_prestamo El objeto Prestamo a agregar
     * @return {@code true} si la lista fue modificada
     */
    public boolean agregarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().add(p_prestamo);
    }

    /**
     * Elimina un préstamo de la lista del socio
     *
     * @param p_prestamo El objeto Prestamo a eliminar
     * @return {@code true} si la lista contenía el préstamo especificado
     */
    public boolean quitarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().remove(p_prestamo);
    }

    // metodos

    /**
     * Calcula y devuelve la cantidad de libros que el socio tiene prestados actualmente
     *
     * @return El número de préstamos en la lista
     */
    public int cantLibrosPrestados() {
        return this.getPrestamos().size();
    }

    /**
     * Devuelve una representación en cadena (String) del socio
     * Utiliza el método abstracto {@link #soyDeLaClase()} para identificar el tipo de socio
     *
     * @return Un String con DNI, nombre, tipo de socio y cantidad de libros prestados
     */
    @Override
    public String toString() {
        return "D.N.I.: " + this.getDniSocio() +
                "||" + this.getNombre() +
                " (" + this.soyDeLaClase() + ")" +
                "|| Libros Prestados: " + this.cantLibrosPrestados();
    }

    /**
     * Verifica si el socio está habilitado para pedir un nuevo libro
     * <p>
     * Comprueba la lista de préstamos del socio y, si alguno está vencido
     * (según el método {@link Prestamo#vencido(Calendar)} comparado con la fecha actual),
     * el socio no puede pedir más
     *
     * @return {@code true} si el socio no tiene ningún préstamo vencido,
     * {@code false} si tiene al menos un préstamo vencido
     */
    public boolean puedePedir() {
        Calendar hoy = Calendar.getInstance();

        for (Prestamo prestamo : this.getPrestamos()) {
            if (prestamo.vencido(hoy)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Debe devolver un String que identifique el tipo específico de socio
     *
     * @return Un String que representa el tipo de la clase hija
     */
    public abstract String soyDeLaClase();
}