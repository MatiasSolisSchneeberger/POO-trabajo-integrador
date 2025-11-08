import javax.swing.*;
import java.awt.*;

/**
 * Clase que encapsula la configuración y gestión de una ventana JFrame.
 * Proporciona métodos para configurar la ventana y gestionar su contenido.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class Ventana extends JFrame {

    /**
     * Constructor que inicializa la ventana con configuración básica.
     */
    public Ventana() {
        // Constructor vacío, la configuración se hace mediante métodos
    }

    /**
     * Configura los parámetros básicos de la ventana.
     *
     * @param titulo Título de la ventana
     * @param ancho Ancho de la ventana en píxeles
     * @param alto Alto de la ventana en píxeles
     */
    public void configurar(String titulo, int ancho, int alto) {
        setTitle(titulo);
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Establece el contenido de la ventana.
     *
     * @param panel Panel a mostrar en la ventana
     */
    public void establecerContenido(JPanel panel) {
        setContentPane(panel);
    }

    /**
     * Muestra la ventana.
     */
    public void mostrar() {
        setVisible(true);
    }

    /**
     * Crea un botón con estilo personalizado.
     *
     * @param texto Texto del botón
     * @return Botón creado con estilo
     */
    public JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(250, 40));
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(0, 123, 255));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 2));
        return boton;
    }
}
