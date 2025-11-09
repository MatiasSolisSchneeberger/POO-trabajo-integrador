import javax.swing.*;
import java.awt.*;

/**
 * VentListSoci (Ventana Listar Socios - Genérica)
 *
 * Esta clase es un JDialog genérico diseñado para mostrar
 * un bloque de texto largo (como un reporte o listado).
 * Es modal, por lo que bloquea la ventana principal.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentListSoci extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextArea textArea;
    private JButton cerrarButton;

    // --- Constantes ---
    private static final Color COLOR_ROJO = new Color(220, 53, 69);

    /**
     * Constructor del diálogo
     *
     * @param owner     La ventana "padre" (VentanaMain)
     * @param titulo    El título que queremos ponerle a la ventana
     * @param contenido El String (posiblemente muy largo) que queremos mostrar
     */
    public VentListSoci(JFrame owner, String titulo, String contenido) {
        // 1. Configuración básica del JDialog
        super(owner, titulo, true); // true = MODAL

        initUI(contenido); // Pasamos el contenido para la creación de la UI
        initDialog();
        initListeners();
    }

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     * @param contenido El texto a mostrar en el JTextArea.
     */
    private void initUI(String contenido) {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Área de Texto (CENTER) ---
        // 20 filas y 50 columnas son un tamaño "sugerido"
        textArea = new JTextArea(20, 50);
        textArea.setText(contenido); // ¡Clave 1! Asignamos el contenido
        textArea.setEditable(false); // ¡Clave 2! No editable

        // Usamos una fuente SANS_SERIF estándar
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        // ¡Clave 3! "Envolvemos" el textArea en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);

        // --- Panel de Botones (SOUTH) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");
        cerrarButton.setForeground(COLOR_ROJO); // Aplicamos color de texto

        buttonPanel.add(cerrarButton);

        // --- Ensamblar la ventana ---
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        pack(); // Ajusta el tamaño al JTextArea (20x50)
        setLocationRelativeTo(getOwner()); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        // Asigna el botón por defecto para la tecla "Enter"
        getRootPane().setDefaultButton(cerrarButton);

        cerrarButton.addActionListener(e -> dispose()); // dispose() cierra el JDialog
    }
}