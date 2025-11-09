import javax.swing.*;
import java.awt.*; // Necesario para los LayoutManagers (BorderLayout, GridLayout, etc.)

/**
 * VentAgreEstu (Ventana Agregar Estudiante)
 *
 * Esta clase es un JDialog MODAL.
 * 'JDialog' significa que es una ventana secundaria (como un popup).
 * 'Modal' (el 'true' en el constructor) significa que bloquea la
 * VentanaMain hasta que esta se cierre, lo cual es ideal para un formulario.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentAgreEstu extends JDialog {

    // --- Componentes de la UI ---
    private JTextField dniField;
    private JTextField nombreField;
    private JTextField carreraField;
    private JButton agregarButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      El JFrame principal (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentAgreEstu(JFrame owner, Biblioteca biblioteca) {
        // 1. Configuración básica del JDialog
        super(owner, "Agregar Nuevo Estudiante", true); // true = MODAL (Corregido)
        this.miBiblioteca = biblioteca;

        initUI();        // Construye los componentes visuales
        initDialog();    // Configura las propiedades de este JDialog
        initListeners(); // Asigna la lógica a los botones
    }

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     */
    private void initUI() {
        // Creamos un panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(24, 24)); // (hgap, vgap)
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Panel del Formulario (CENTER) ---
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // (filas, cols, hgap, vgap)

        dniField = new JTextField(20);
        nombreField = new JTextField(20);
        carreraField = new JTextField(20);

        fieldsPanel.add(new JLabel("DNI:"));
        fieldsPanel.add(dniField);
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Carrera:"));
        fieldsPanel.add(carreraField);

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

        // Establecemos el panel principal como el contenido del JDialog
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        pack(); // Ajusta el tamaño de la ventana a los componentes
        setLocationRelativeTo(getOwner()); // Centra sobre la ventana principal
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        // Asigna el botón por defecto para la tecla "Enter"
        getRootPane().setDefaultButton(agregarButton);

        // Conecta los botones a los métodos
        agregarButton.addActionListener(e -> onAgregar());
        cancelarButton.addActionListener(e -> onCancelar());
    }

    /**
     * Lógica que se ejecuta al presionar "Agregar".
     * Valida los campos y llama a la biblioteca.
     */
    private void onAgregar() {
        try {
            // 1. Obtener y validar los datos
            String dniStr = dniField.getText();
            String nombre = nombreField.getText();
            String carrera = carreraField.getText();

            if (dniStr.isBlank() || nombre.isBlank() || carrera.isBlank()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dni = Integer.parseInt(dniStr); // Puede lanzar NumberFormatException

            // 2. Validar que sea mayor a 0
            if (dni <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El DNI debe ser un número positivo.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución
            }

            // 3. Llamar a la lógica de negocio
            miBiblioteca.nuevoSocioEstudiante(dni, nombre, carrera);

            // 4. Informar y cerrar
            JOptionPane.showMessageDialog(this, "Estudiante agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE); // Corregido
            dispose(); // Cierra esta ventana de diálogo

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Captura cualquier otro error (ej. DNI duplicado)
            JOptionPane.showMessageDialog(this, "Error al agregar socio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica que se ejecuta al presionar "Cancelar".
     */
    private void onCancelar() {
        // Simplemente cierra el diálogo
        dispose();
    }
}