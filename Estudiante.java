import java.util.Calendar;
/**
 * class Estudiante.
 * 
 * @author (Escobar Lucas) 
 * @version (01/11/2025)
 */
public class Estudiante extends Socio{
    private String carrera;
    
    // Constructor 
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setCarrera(p_carrera);
    }
    
    // Setters y Getters
    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }

    public String getCarrera(){
        return this.carrera;
    }
    
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
    }
    
    @Override
    public String soyDeLaClase(){
        return "Estudiante";
    }
}
