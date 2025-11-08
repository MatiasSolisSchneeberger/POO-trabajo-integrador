import javax.swing.*;
import java.awt.*;

/**
 * Ventana para la gestión de socios de la biblioteca.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaGestionSocios {

    private Biblioteca biblioteca;
    private Ventana ventanaPrincipal;

    public VentanaGestionSocios(Biblioteca biblioteca, Ventana ventanaPrincipal) {
        this.biblioteca = biblioteca;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void mostrarMenu() {
        String[] opciones = {
            "Agregar Estudiante",
            "Agregar Docente",
            "Quitar Socio",
            "Listar Socios",
            "Cambiar Días de Préstamo (Docente)",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(ventanaPrincipal,
            "Gestión de Socios - Seleccione una opción:",
            "Gestión de Socios",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> agregarSocioEstudiante();
            case 1 -> agregarSocioDocente();
            case 2 -> quitarSocio();
            case 3 -> listarSocios();
            case 4 -> cambiarDiasDocente();
        }
    }

    private void agregarSocioEstudiante() {
        VentanaFormularioEstudiante formulario = new VentanaFormularioEstudiante(biblioteca);
        formulario.mostrar();
    }

    private void agregarSocioDocente() {
        VentanaFormularioDocente formulario = new VentanaFormularioDocente(biblioteca);
        formulario.mostrar();
    }

    private void quitarSocio() {
        VentanaQuitarSocio ventana = new VentanaQuitarSocio(biblioteca);
        ventana.mostrar();
    }

    private void listarSocios() {
        String lista = biblioteca.listaDeSocios();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(ventanaPrincipal, scrollPane, "Lista de Socios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarDiasDocente() {
        VentanaCambiarDiasDocente ventana = new VentanaCambiarDiasDocente(biblioteca);
        ventana.mostrar();
    }
}
