import javax.swing.*;
import java.awt.*; // Necesario para los LayoutManagers (BorderLayout, GridLayout, etc.)

/**
 * VentAgreEstu (Ventana Agregar BibliotecaEstudiante)
 * <p>
 * Esta clase es un JDialog MODAL.
 * 'JDialog' significa que es una ventana secundaria (como un popup).
 * 'Modal' (el 'true' en el constructor) significa que bloquea la
 * VentanaMain hasta que esta se cierre, lo cual es ideal para un formulario.
 */
public class VentAgreEstu extends JDialog { // Sigue siendo un JDialog

    // --- Componentes de la UI ---
    private JTextField dniField;
    private JTextField nombreField;
    private JTextField carreraField;
    private JButton agregarButton;
    private JButton cancelarButton;

    // --- Lógica ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      El JFrame principal (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentAgreEstu(JFrame owner, Biblioteca biblioteca) {
        // 1. Configuración básica del JDialog
        super(owner, "Agregar Nuevo BibliotecaEstudiante", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Creamos un panel principal con BorderLayout
        // (Esto nos da un 'CENTER' para el formulario y un 'SOUTH' para los botones)
        JPanel mainPanel = new JPanel(new BorderLayout(24, 24)); // (hgap, vgap)
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Panel del Formulario (CENTER) ---
        // Usamos GridLayout (0 filas, 2 columnas) para un formato de etiqueta-campo
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // (filas, cols, hgap, vgap)

        // Inicializamos los componentes
        dniField = new JTextField(20); // 20 es un ancho sugerido
        nombreField = new JTextField(20);
        carreraField = new JTextField(20);

        // Agregamos etiquetas y campos al panel del formulario
        fieldsPanel.add(new JLabel("DNI:"));
        fieldsPanel.add(dniField);
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Carrera:"));
        fieldsPanel.add(carreraField);

        // --- Panel de Botones (SOUTH) ---
        // Usamos FlowLayout (alineado a la derecha) para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        agregarButton = new JButton("Agregar");
        cancelarButton = new JButton("Cancelar");

        // Botón Agregar (Verde)
        // Usamos colores RGB para un verde más agradable que Color.GREEN
        agregarButton.setForeground(new Color(40, 167, 69));

        // Botón Cancelar (Rojo)
        // Usamos colores RGB para un rojo más agradable que Color.RED
        cancelarButton.setForeground(new Color(220, 53, 69));

        buttonPanel.add(cancelarButton);
        buttonPanel.add(agregarButton);

        // --- 3. Ensamblar la ventana ---
        // Agregamos los sub-paneles al panel principal
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Establecemos el panel principal como el contenido del JDialog
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---
        pack(); // Ajusta el tamaño de la ventana a los componentes
        setLocationRelativeTo(owner); // Centra sobre la ventana principal
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        getRootPane().setDefaultButton(agregarButton);
        // --- 5. Action Listeners ---
        // (La lógica de los botones es idéntica a la versión .form)
        agregarButton.addActionListener(e -> onAgregar());
        cancelarButton.addActionListener(e -> onCancelar());
    }

    /**
     * Lógica que se ejecuta al presionar "Agregar"
     */
    private void onAgregar() {
        try {
            // 1. Obtener y validar los datos
            String dniStr = dniField.getText();
            String nombre = nombreField.getText();
            String carrera = carreraField.getText();
            // Validar todos los campos
            if (dniStr.isBlank() || nombre.isBlank() || carrera.isBlank()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            int dni = Integer.parseInt(dniStr);

            // Validar que sea mayor a 0
            if (dni <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El DNI debe ser un número positivo.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución
            }
            // 2. Llamar a la lógica de negocio
            miBiblioteca.nuevoSocioEstudiante(dni, nombre, carrera);

            // 3. Informar y cerrar
            JOptionPane.showMessageDialog(this, "BibliotecaEstudiante agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cierra esta ventana de diálogo

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Captura cualquier otro error que la biblioteca pueda lanzar (ej. DNI duplicado)
            JOptionPane.showMessageDialog(this, "Error al agregar socio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica que se ejecuta al presionar "Cancelar"
     */
    private void onCancelar() {
        // Simplemente cierra el diálogo
        dispose();
    }
}