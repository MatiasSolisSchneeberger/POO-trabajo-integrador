import javax.swing.*;
import java.awt.*; // Para Layouts, Color, etc.

/**
 * VentAgreLibro (Ventana Agregar Libro)
 * <p>
 * Esta clase es un JDialog modal para ingresar los datos de un nuevo libro.
 * Se construye por código, sin un .form, para mayor claridad.
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

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout (zonas Centro y Sur)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        // Padding (borde vacío)
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel del Formulario (CENTER) ---
        // GridLayout (0, 2) -> N filas, 2 columnas
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // (filas, cols, hgap, vgap)

        // Inicializamos los componentes de texto
        tituloField = new JTextField(20);
        edicionField = new JTextField(5); // Edición es un número corto
        editorialField = new JTextField(20);
        anioField = new JTextField(5); // Año es un número corto

        // Agregamos etiquetas y campos al panel del formulario
        fieldsPanel.add(new JLabel("Título:"));
        fieldsPanel.add(tituloField);
        fieldsPanel.add(new JLabel("Edición:"));
        fieldsPanel.add(edicionField);
        fieldsPanel.add(new JLabel("Editorial:"));
        fieldsPanel.add(editorialField);
        fieldsPanel.add(new JLabel("Año de Publicación:"));
        fieldsPanel.add(anioField);

        // --- Panel de Botones (SOUTH) ---
        // FlowLayout alineado a la derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        agregarButton = new JButton("Agregar");
        cancelarButton = new JButton("Cancelar");

        // --- Aplicamos los colores a los botones (SOLO TEXTO) ---
        // Como pediste, solo modificamos el 'Foreground' (color del texto)

        // Botón Agregar (Verde)
        agregarButton.setForeground(new Color(40, 167, 69)); // Verde

        // Botón Cancelar (Rojo)
        cancelarButton.setForeground(new Color(220, 53, 69)); // Rojo

        // Agregamos los botones al panel
        buttonPanel.add(cancelarButton);
        buttonPanel.add(agregarButton);

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(owner); // Centra sobre VentanaMain
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        // --- 5. Funcionalidad "Enter" ---
        // Hacemos que "Agregar" sea el botón por defecto al presionar Enter
        getRootPane().setDefaultButton(agregarButton);

        // --- 6. Action Listeners ---
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
            int edicion = Integer.parseInt(edicionStr);
            int anio = Integer.parseInt(anioStr);

            // 4. Validar que los números sean positivos
            // (Asumimos que no manejamos libros A.C. o ediciones "0")
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
            // Se activa si parseInt() falla
            JOptionPane.showMessageDialog(this,
                    "La 'Edición' y el 'Año' deben ser números válidos.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Captura genérica para otros problemas
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