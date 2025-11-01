import java.util.Calendar;
/**
 * Write a description of class Docente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Docente extends Socio{
    private String area;
    
    // Constructor
    public Docente(int p_dniSocio, String p_nombre, String p_area){
        super(p_dniSocio, p_nombre, 5);
        this.setArea(p_area);
    }
    
    // Setters y Getters
    private void setArea(String p_area){
        this.area = p_area;
    }

    public String getArea(){
        return this.area;
    }
    
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
    
    public void cambiarDiasDePrestamo(int p_dias){
        if (this.esResponsable()) {
        // 1. Calcula los nuevos días
        int nuevosDias = this.getDiasPrestamo() + p_dias;
        } else {
        System.out.println("No se pueden agregar días: el docente no es responsable.");
        }
    }
    
    @Override
    public boolean puedePedir(){
        return super.puedePedir();
    }
    
    @Override
    public String soyDeLaClase(){
        return "Docente";
    }
}