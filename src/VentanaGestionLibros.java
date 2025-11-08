import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Ventana para la gestión de libros de la biblioteca.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaGestionLibros {

    private Biblioteca biblioteca;
    private Ventana ventanaPrincipal;

    public VentanaGestionLibros(Biblioteca biblioteca, Ventana ventanaPrincipal) {
        this.biblioteca = biblioteca;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void mostrarMenu() {
        String[] opciones = {
            "Agregar Libro",
            "Quitar Libro",
            "Listar Libros (con estado)",
            "Listar Títulos Únicos",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(ventanaPrincipal,
            "Gestión de Libros - Seleccione una opción:",
            "Gestión de Libros",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> agregarLibro();
            case 1 -> quitarLibro();
            case 2 -> listarLibros();
            case 3 -> listarTitulos();
        }
    }

    private void agregarLibro() {
        VentanaFormularioLibro formulario = new VentanaFormularioLibro(biblioteca);
        formulario.mostrar();
    }

    private void quitarLibro() {
        VentanaQuitarLibro ventana = new VentanaQuitarLibro(biblioteca);
        ventana.mostrar();
    }

    private void listarLibros() {
        String lista = biblioteca.listaDeLibros();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(ventanaPrincipal, scrollPane, "Lista de Libros", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarTitulos() {
        String lista = biblioteca.listaDeTitulos();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(ventanaPrincipal, scrollPane, "Lista de Títulos Únicos", JOptionPane.INFORMATION_MESSAGE);
    }
}
