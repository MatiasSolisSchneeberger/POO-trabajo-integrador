import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Ventana para la gestión de préstamos de la biblioteca.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaGestionPrestamos {

    private Biblioteca biblioteca;
    private Ventana ventanaPrincipal;

    public VentanaGestionPrestamos(Biblioteca biblioteca, Ventana ventanaPrincipal) {
        this.biblioteca = biblioteca;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void mostrarMenu() {
        String[] opciones = {
            "Realizar Préstamo",
            "Devolver Libro",
            "Verificar Habilitación de Socio",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(ventanaPrincipal,
            "Préstamos y Devoluciones - Seleccione una opción:",
            "Préstamos y Devoluciones",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> realizarPrestamo();
            case 1 -> devolverLibro();
            case 2 -> verificarHabilitacionSocio();
        }
    }

    private void realizarPrestamo() {
        VentanaFormularioPrestamo formulario = new VentanaFormularioPrestamo(biblioteca);
        formulario.mostrar();
    }

    private void devolverLibro() {
        VentanaFormularioDevolucion formulario = new VentanaFormularioDevolucion(biblioteca);
        formulario.mostrar();
    }

    private void verificarHabilitacionSocio() {
        VentanaVerificarHabilitacion ventana = new VentanaVerificarHabilitacion(biblioteca);
        ventana.mostrar();
    }
}
