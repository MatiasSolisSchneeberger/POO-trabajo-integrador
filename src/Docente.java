import java.util.Calendar;
import java.io.Serializable;
/**
 * Representa a un **Docente** en el sistema de la biblioteca.
 * <p>
 * Un docente es un {@link Socio} con un área de especialización que comienza con 5 días
 * de préstamo, pero puede recibir días adicionales como premio a su responsabilidad.
 *
 * @author Escobar Lucas
 * @version 01/11/2025
 */
public class Docente extends Socio implements java.io.Serializable{
    private String area;

    /**
     * Constructor para crear un nuevo Docente.
     * Se asignan automáticamente 5 días de préstamo base.
     *
     * @param p_dniSocio El DNI del docente.
     * @param p_nombre   El nombre completo del docente.
     * @param p_area     El área de especialización (ej. "Matemáticas", "Historia").
     */
    public Docente(int p_dniSocio, String p_nombre, String p_area) {
        super(p_dniSocio, p_nombre, 5);
        this.setArea(p_area);
    }

    /**
     * Establece el área de especialización.
     *
     * @param p_area El nombre del área.
     */
    private void setArea(String p_area) {
        this.area = p_area;
    }

    /**
     * Obtiene el área de especialización del docente.
     *
     * @return El área del docente.
     */
    public String getArea() {
        return this.area;
    }

    /**
     * Verifica el historial de responsabilidad del docente.
     * <p>
     * Un docente se considera responsable si <b>nunca</b> ha tenido un préstamo vencido.
     * Se verifica tanto el historial de libros ya devueltos como los préstamos activos actualmente.
     *
     * @return {@code true} si nunca tuvo retrasos, {@code false} si registra al menos un vencimiento.
     * @see Prestamo#vencido(Calendar)
     */
    public boolean esResponsable() {
        Calendar hoy = Calendar.getInstance();

        for (Prestamo prestamo : super.getPrestamos()) {
            // si el libro ya fue devuelto verificamos si se devolvio tarde
            if (prestamo.getFechaDevolucion() != null) {
                if (prestamo.vencido(prestamo.getFechaDevolucion())) {
                    return false;
                }
            }
            // si el prestamo sigue activo verificamos si está vencido al día de hoy
            else {
                if (prestamo.vencido(hoy)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adiciona días al plazo de préstamo del docente, solo si es considerado responsable.
     *
     * @param p_dias La cantidad de días a sumar al plazo actual.
     * @see #esResponsable()
     */
    public void cambiarDiasDePrestamo(int p_dias) {
        if (this.esResponsable()) {
            this.setDiasPrestamo(this.getDiasPrestamo() + p_dias);
        } else {
            System.out.println("No se pueden agregar días: el docente no es responsable.");
        }
    }

    /**
     * Verifica si el docente puede pedir un nuevo préstamo.
     * Actualmente, delega completamente la validación a la clase padre {@link Socio}.
     *
     * @return {@code true} si no tiene préstamos vencidos activos.
     */
    @Override
    public boolean puedePedir() {
        return super.puedePedir();
    }

    /**
     * Devuelve el identificador del tipo de socio.
     *
     * @return La cadena literal "Docente".
     */
    @Override
    public String soyDeLaClase() {
        return "Docente";
    }
}
