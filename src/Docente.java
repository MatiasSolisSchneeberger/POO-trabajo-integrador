import java.util.Calendar;
import java.io.Serializable;
/**
 * Representa a un **Docente** en el sistema de la biblioteca.
 * Un docente es un tipo de {@link Socio} que tiene un área de especialización
 * y puede modificar sus días de préstamo bajo ciertas condiciones.
 *
 * @author Escobar Lucas
 * @version 01/11/2025
 */
public class Docente extends Socio implements java.io.Serializable{
    private String area;
    
    /**
     * Constructor para la clase Docente.
     * Al docente se le asigna por defecto 5 días de préstamo.
     * @param p_dniSocio El DNI o número de socio del docente.
     * @param p_nombre El nombre completo del docente.
     * @param p_area El área de especialización del docente (ej. "Matemáticas", "Historia").
     */
    public Docente(int p_dniSocio, String p_nombre, String p_area){
        super(p_dniSocio, p_nombre, 5); // Por defecto: 5 días de préstamo
        this.setArea(p_area);
    }
    
    /**
     * Establece el área de especialización del docente.
     * @param p_area La nueva área a asignar.
     */
    private void setArea(String p_area){
        this.area = p_area;
    }

    /**
     * Obtiene el área de especialización del docente.
     * @return El área de especialización del docente.
     */
    public String getArea(){
        return this.area;
    }
    
    /**
     * Verifica si el docente es "responsable" en sus préstamos.
     * Un docente **no** es responsable si tiene **algún** préstamo vencido.
     * Para esto, se utiliza el método {@code vencido(Calendar)} de la clase {@link Prestamo}.
     * @return {@code true} si el docente no tiene préstamos vencidos, {@code false} en caso contrario.
     * @see Prestamo#vencido(Calendar)
     * @see Socio#getPrestamos()
     */
    public boolean esResponsable(){
        Calendar hoy = Calendar.getInstance();

        for (Prestamo prestamo : super.getPrestamos()) {
            // Si el préstamo está vencido, deja de ser responsable
            if (prestamo.vencido(hoy)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Permite al docente cambiar (agregar o restar) días de préstamo a su límite actual.
     * La modificación **solo** se aplica si el docente es **responsable** (no tiene préstamos vencidos).
     * @param p_dias La cantidad de días a agregar o restar al límite actual de préstamo.
     * @see #esResponsable()
     * @see Socio#getDiasPrestamo()
     */
    public void cambiarDiasDePrestamo(int p_dias){
        if (this.esResponsable()) {
        int nuevosDias = this.getDiasPrestamo() + p_dias;
        // ¡USA EL SETTER PARA GUARDAR EL VALOR!
        this.setDiasPrestamo(nuevosDias);
        
        } else {
           System.out.println("No se pueden agregar días: el docente no es responsable.");
        }
    }
    
    /**
     * Determina si el docente está habilitado para solicitar un nuevo préstamo.
     * Este método utiliza la lógica de la clase base {@link Socio#puedePedir()}.
     * @return {@code true} si el docente puede pedir un libro, {@code false} en caso contrario.
     */
    @Override
    public boolean puedePedir(){
        return super.puedePedir();
    }
    
    /**
     * Devuelve la descripción de la clase del socio.
     * @return Una cadena con el nombre de la clase, que es "Docente".
     */
    @Override
    public String soyDeLaClase(){
        return "Docente";
    }
}
