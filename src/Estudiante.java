import java.util.Calendar;

/**
 * Representa a un **Estudiante** en el sistema de la biblioteca.
 * <p>
 * Un estudiante es un tipo de {@link Socio} que tiene restricciones específicas:
 * <ul>
 * <li>Tiene un límite fijo de días de préstamo (20 días).</li>
 * <li>No puede tener más de 3 libros en su poder simultáneamente.</li>
 * </ul>
 *
 * @author Escobar Lucas
 * @version 01/11/2025
 */
public class Estudiante extends Socio {
    private String carrera;

    /**
     * Constructor para crear un nuevo Estudiante.
     * Se asignan automáticamente 20 días de préstamo base.
     *
     * @param p_dniSocio El DNI del estudiante.
     * @param p_nombre   El nombre completo del estudiante.
     * @param p_carrera  La carrera que está cursando.
     */
    public Estudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        super(p_dniSocio, p_nombre, 20);
        this.setCarrera(p_carrera);
    }

    /**
     * Establece la carrera del estudiante.
     *
     * @param p_carrera El nombre de la carrera.
     */
    private void setCarrera(String p_carrera) {
        this.carrera = p_carrera;
    }

    /**
     * Obtiene la carrera que está cursando el estudiante.
     *
     * @return El nombre de la carrera.
     */
    public String getCarrera() {
        return this.carrera;
    }

    /**
     * Verifica si el estudiante está habilitado para realizar un nuevo pedido de libro.
     * <p>
     * Un estudiante <b>no</b> puede pedir un libro sí:
     * <ul>
     * <li>Ya tiene 3 o más libros en su poder.</li>
     * <li>Tiene algún préstamo vencido (verificado por la clase padre).</li>
     * </ul>
     *
     * @return {@code true} si cumple todas las condiciones para pedir, {@code false} en caso contrario.
     * @see Socio#puedePedir()
     */
    @Override
    public boolean puedePedir() {
        // verifica límite de cantidad de libros (máximo 3)
        if (this.cantLibrosPrestados() >= 3) {
            return false;
        }
        // verifica vencimientos usando la lógica heredada de Socio
        return super.puedePedir();
    }

    /**
     * Devuelve el identificador del tipo de socio.
     *
     * @return La cadena literal "Estudiante".
     */
    @Override
    public String soyDeLaClase() {
        return "Estudiante";
    }
}
