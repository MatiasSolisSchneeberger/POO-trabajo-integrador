/**
 * Clase que crea la Exception de tipo "LibroNoPrestadoException" con mensaje descriptivo "El libro se encuentra en biblioteca".
 * @author Fernandez Gabriel Elías
 * @version 04/11/2025
 */
public class LibroNoPrestadoException extends Exception{
    public LibroNoPrestadoException(String mensaje){
        super(mensaje);
    }

}
