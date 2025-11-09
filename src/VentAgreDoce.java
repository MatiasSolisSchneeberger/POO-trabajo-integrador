import javax.swing.*;
import java.awt.*; // Importamos 'awt' para los LayoutManagers (BorderLayout, GridLayout) y Colores

/**
 * VentAgreDoce (Ventana Agregar Docente)
 *
 * Esta clase extiende JDialog, que es el tipo de ventana ideal para un
 * formulario emergente modal. Se utiliza para capturar los datos de un
 * nuevo socio de tipo Docente.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentAgreDoce extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField dniField;
    private JTextField nombreField;
    private JTextField areaField;
    private JButton agregarButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor de la ventana (diálogo)
     *
     * @param owner      Es la ventana "padre" (nuestra VentanaMain).
     * @param biblioteca Es la instancia de la lógica de negocio.
     */
    public VentAgreDoce(JFrame owner, Biblioteca biblioteca) {
        // 1. Llamamos al constructor de JDialog (la clase padre)
        // El 'true' al final lo hace "MODAL", bloqueando la ventana principal.
        super(owner, "Agregar Nuevo Docente", true);

        this.miBiblioteca = biblioteca;

        initUI();        // Construye los componentes visuales
        initDialog();    // Configura las propiedades de este JDialog
        initListeners(); // Asigna la lógica a los botones
    }

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     */
    private void initUI() {
        // Usamos BorderLayout (Centro para el form, Sur para botones)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // (hgap, vgap)
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Panel del Formulario (CENTRO) ---
        // GridLayout (0, 2) -> N filas, 2 columnas
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        dniField = new JTextField(20);
        nombreField = new JTextField(20);
        areaField = new JTextField(20);

        // Agregamos etiquetas y campos
        fieldsPanel.add(new JLabel("DNI:"));
        fieldsPanel.add(dniField);
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Área:")); // El campo específico de Docente
        fieldsPanel.add(areaField);

        // --- Panel de Botones (SUR) ---
        // FlowLayout.RIGHT alinea los botones a la derecha
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

        // Le decimos al JDialog que 'mainPanel' es su contenido
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        // pack() ajusta el tamaño de la ventana a los componentes
        pack();
        // Centra la ventana sobre su 'owner' (VentanaMain)
        setLocationRelativeTo(getOwner());
        // DISPOSE_ON_CLOSE solo destruye esta ventana, no el programa
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
     * Lógica de validación y acción al presionar "Agregar".
     * Llama a la biblioteca si los datos son válidos.
     */
    private void onAgregar() {
        try {
            // 1. Obtenemos los datos de los campos
            String dniStr = dniField.getText();
            String nombre = nombreField.getText();
            String area = areaField.getText();

            // 2. Validamos que no estén vacíos
            if (dniStr.isBlank() || nombre.isBlank() || area.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución
            }

            // 3. Convertimos y validamos DNI
            int dni = Integer.parseInt(dniStr); // Puede lanzar NumberFormatException
            if (dni <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El DNI debe ser un número positivo.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos
            }

            // 4. Llamamos a la lógica de negocio
            miBiblioteca.nuevoSocioDocente(dni, nombre, area);

            // 5. Informamos éxito y cerramos
            JOptionPane.showMessageDialog(this,
                    "Docente agregado con éxito.", // Corregido de "BibliotecaDocente"
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose(); // Cierra esta ventana de diálogo

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe ser un número válido.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Captura genérica (ej: DNI duplicado, si la lógica lo lanzara)
            JOptionPane.showMessageDialog(this,
                    "Error al agregar socio: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica al presionar "Cancelar".
     */
    private void onCancelar() {
        // dispose() simplemente cierra esta ventana (JDialog)
        dispose();
    }
}