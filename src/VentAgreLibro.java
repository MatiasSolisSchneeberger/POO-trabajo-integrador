import javax.swing.*;
import java.awt.*; // Para Layouts, Color, etc.

/**
 * VentAgreLibro (Ventana Agregar Libro)
 *
 * Esta clase es un JDialog modal para ingresar los datos de un nuevo libro.
 * Se construye por código, sin un .form, para mayor claridad y mantenimiento.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentAgreLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField tituloField;
    private JTextField edicionField;
    private JTextField editorialField;
    private JTextField anioField;
    private JButton agregarButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentAgreLibro(JFrame owner, Biblioteca biblioteca) {
        // 1. Configuración básica del JDialog
        super(owner, "Agregar Nuevo Libro", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        initUI();        // Construye los componentes visuales
        initDialog();    // Configura las propiedades de este JDialog
        initListeners(); // Asigna la lógica a los botones
    }

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     */
    private void initUI() {
        // Panel principal con BorderLayout (zonas Centro y Sur)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Panel del Formulario (CENTER) ---
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // N filas, 2 columnas

        tituloField = new JTextField(20);
        edicionField = new JTextField(5); // Edición es un número corto
        editorialField = new JTextField(20);
        anioField = new JTextField(5); // Año es un número corto

        fieldsPanel.add(new JLabel("Título:"));
        fieldsPanel.add(tituloField);
        fieldsPanel.add(new JLabel("Edición:"));
        fieldsPanel.add(edicionField);
        fieldsPanel.add(new JLabel("Editorial:"));
        fieldsPanel.add(editorialField);
        fieldsPanel.add(new JLabel("Año de Publicación:"));
        fieldsPanel.add(anioField);

        // --- Panel de Botones (SOUTH) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        agregarButton = new JButton("Agregar");
        cancelarButton = new JButton("Cancelar");

        // --- Estilo de Botones (Solo color de texto) ---
        agregarButton.setForeground(new Color(40, 167, 69)); // Verde
        cancelarButton.setForeground(new Color(220, 53, 69)); // Rojo

        buttonPanel.add(cancelarButton);
        buttonPanel.add(agregarButton);

        // --- Ensamblar la ventana ---
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(getOwner()); // Centra sobre VentanaMain
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        // Hacemos que "Agregar" sea el botón por defecto al presionar Enter
        getRootPane().setDefaultButton(agregarButton);

        agregarButton.addActionListener(e -> onAgregar());
        cancelarButton.addActionListener(e -> onCancelar());
    }

    /**
     * Lógica que se ejecuta al presionar "Agregar".
     * Incluye validación de datos.
     */
    private void onAgregar() {
        try {
            // 1. Obtener los datos de los campos de texto
            String titulo = tituloField.getText();
            String edicionStr = edicionField.getText();
            String editorial = editorialField.getText();
            String anioStr = anioField.getText();

            // 2. Validar que no estén vacíos
            if (titulo.isBlank() || edicionStr.isBlank() || editorial.isBlank() || anioStr.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución
            }

            // 3. Convertir los números
            int edicion = Integer.parseInt(edicionStr); // Puede lanzar NumberFormatException
            int anio = Integer.parseInt(anioStr); // Puede lanzar NumberFormatException

            // 4. Validar que los números sean positivos
            if (edicion <= 0 || anio <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El año y la edición deben ser números positivos.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución
            }

            // 5. Llamar a la lógica de negocio (Biblioteca)
            miBiblioteca.nuevoLibro(titulo, edicion, editorial, anio);

            // 6. Informar éxito y cerrar
            JOptionPane.showMessageDialog(this,
                    "Libro agregado con éxito.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cierra esta ventana

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La 'Edición' y el 'Año' deben ser números válidos.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al agregar el libro: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica que se ejecuta al presionar "Cancelar".
     */
    private void onCancelar() {
        // dispose() simplemente cierra esta ventana (JDialog)
        dispose();
    }
}