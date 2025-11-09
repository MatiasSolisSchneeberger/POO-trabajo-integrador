import javax.swing.*;
import java.awt.*; // Para BorderLayout, FlowLayout, Font

/**
 * VentListarSocios (Ventana Listar Socios)
 *
 * Esta clase es un JDialog genérico diseñado para mostrar
 * un bloque de texto largo (como un reporte o listado).
 * Es modal, por lo que bloquea la ventana principal.
 */
public class VentListSoci extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextArea textArea; // El componente estrella para mostrar texto
    private JScrollPane scrollPane; // El contenedor que le da barras de scroll
    private JButton cerrarButton;

    /**
     * Constructor del diálogo
     *
     * @param owner   La ventana "padre" (VentanaMain)
     * @param titulo  El título que queremos ponerle a la ventana
     * @param contenido El String (posiblemente muy largo) que queremos mostrar
     */
    public VentListSoci(JFrame owner, String titulo, String contenido) {

        // 1. Configuración básica del JDialog
        super(owner, titulo, true); // true = MODAL

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Área de Texto (CENTER) ---

        // Creamos el JTextArea. 20 filas y 50 columnas son un tamaño
        // "sugerido" para que 'pack()' sepa qué tan grande hacerlo.
        textArea = new JTextArea(20, 50);

        // ¡Clave 1! Le pasamos el String completo que nos llegó
        textArea.setText(contenido);

        // ¡Clave 2! No queremos que el usuario pueda escribir en el listado
        textArea.setEditable(false);

        // ¡Pro-Tip! Usamos una fuente "Monospaced" (como Consolas o Courier).
        // Esto hace que el texto se vea como en la consola, y si tienes
        // columnas de texto, se alinearán perfectamente.
        textArea.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 12));

        // ¡Clave 3! Creamos un JScrollPane y "envolvemos" el textArea con él.
        // Si el 'contenido' tiene más de 20 filas, aparecerán las barras
        // de scroll automáticamente.
        scrollPane = new JScrollPane(textArea);

        // --- Panel de Botones (SOUTH) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");

        // Recuerda que la solución del UIManager ya quita el foco,
        // pero lo ponemos aquí por si acaso.
        cerrarButton.setFocusPainted(false);

        buttonPanel.add(cerrarButton);

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(scrollPane, BorderLayout.CENTER); // El scroll (con texto) al centro
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // El botón abajo
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---
        pack(); // Ajusta el tamaño al JTextArea (20x50)
        setLocationRelativeTo(owner); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        // --- 5. Action Listeners ---
        cerrarButton.addActionListener(e -> dispose()); // dispose() cierra el JDialog
    }
}