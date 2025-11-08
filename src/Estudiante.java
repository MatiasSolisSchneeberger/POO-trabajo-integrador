import java.util.Calendar;

/**
 * Representa a un **Estudiante** en el sistema de la biblioteca.
 * Un estudiante es un tipo de {@link Socio} con restricciones y privilegios específicos
 * relacionados con la cantidad y el tiempo de préstamo de libros.
 *
 * @author Escobar Lucas
 * @version 01/11/2025
 */
public class Estudiante extends Socio{
    private String carrera;
    
    /**
     * Constructor para la clase Estudiante.
     * @param p_dniSocio El DNI o número de socio del estudiante.
     * @param p_nombre El nombre completo del estudiante.
     * @param p_diasPrestamo La cantidad de días por defecto que el estudiante tiene para un préstamo.
     * @param p_carrera La carrera que está cursando el estudiante.
     */
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setCarrera(p_carrera);
    }
    
    /**
     * Establece la carrera que cursa el estudiante.
     * @param p_carrera La nueva carrera a asignar.
     */
    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }

    /**
     * Obtiene la carrera que cursa el estudiante.
     * @return La carrera del estudiante.
     */
    public String getCarrera(){
        return this.carrera;
    }
    
    /**
     * Determina si el estudiante está habilitado para solicitar un nuevo préstamo.
     * Un estudiante **no** puede pedir un nuevo libro si:
     * <ul>
     * <li>Tiene más de **tres** libros prestados actualmente.</li>
     * <li>Tiene algún libro cuyo préstamo haya **vencido** (más de 20 días desde la fecha de retiro).</li>
     * </ul>
     * @return {@code true} si el estudiante puede pedir un libro, {@code false} en caso contrario.
     * @see Socio#cantLibrosPrestados()
     * @see Prestamo#getFechaRetiro()
     */
    @Override
    public boolean puedePedir(){
        // No puede pedir si tiene mas de 3 libros 
        if (this.cantLibrosPrestados() > 3) {
            return false;
        }
        
        // tampoco si tiene algun libro vencido(mas de 20 dias)
        Calendar hoy = Calendar.getInstance();
        
        for (Prestamo p : this.getPrestamos()) {
            //comprueba si hay prestamos o si hay una fecha de retiro (evita NullPointerException)
            if (p == null || p.getFechaRetiro() == null) continue;
            
            // Calculamos la fecha de vencimiento (regla de 20 días)
            Calendar fechaVencimientoEstudiante = (Calendar) p.getFechaRetiro().clone();
            fechaVencimientoEstudiante.add(Calendar.DAY_OF_YEAR, 20);
            
            // Comprobamos si la fecha de HOY es posterior a la fecha de vencimiento
            if (hoy.after(fechaVencimientoEstudiante)) {
                return false; // Está vencido
            }
        }
        //si no se cumple ninguna de las 2 condiciones Si puede pedir
        return true;
        // SUGERENCIA: solo llamar a puedePedir de la superClase
        // return super.puedePedir();
    }
    /**
     * Devuelve la descripción de la clase del socio.
     * *@return Una cadena con el nombre de la clase, que es "Estudiante".
     */
    @Override
    public String soyDeLaClase(){
        return "Estudiante";
    }
}
