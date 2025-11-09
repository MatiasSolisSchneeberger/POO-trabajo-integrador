import javax.swing.*;
import java.awt.*;

/**
 * VentVerificarSocio (Ventana Verificar Habilitación de Socio)
 *
 * JDialog modal que permite al usuario ingresar un DNI y ver
 * el estado de habilitación de ese socio en un área de texto,
 * sin cerrar la ventana.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentVerificarSocio extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField dniField;
    private JButton verificarButton;
    private JTextArea resultadoArea;
    private JButton cerrarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    // --- Constantes ---
    private static final Color COLOR_ROJO = new Color(220, 53, 69);
    private static final Color COLOR_VERDE_OSCURO = new Color(0, 128, 0);

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentVerificarSocio(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Verificar Habilitación de Socio", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        initUI();
        initDialog();
        initListeners();
    }

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     */
    private void initUI() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel de Input (NORTE) ---
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("DNI del Socio:"));
        dniField = new JTextField(15);
        verificarButton = new JButton("Verificar");
        inputPanel.add(dniField);
        inputPanel.add(verificarButton);

        // --- Panel de Resultados (CENTRO) ---
        resultadoArea = new JTextArea(10, 50);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setText("Ingrese un DNI y presione 'Verificar'...");
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // --- Panel de Botones (SUR) ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");
        cerrarButton.setForeground(COLOR_ROJO); // Rojo

        southPanel.add(cerrarButton);

        // --- Ensamblar ---
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(getOwner()); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        // "Enter" activa el botón "Verificar"
        getRootPane().setDefaultButton(verificarButton);
        dniField.addActionListener(e -> onVerificar());

        verificarButton.addActionListener(e -> onVerificar());
        cerrarButton.addActionListener(e -> dispose());
    }

    /**
     * Lógica que se ejecuta al presionar "Verificar".
     * Muestra el resultado en el JTextArea 'resultadoArea'.
     */
    private void onVerificar() {
        try {
            // 1. Obtener y validar DNI
            String dniStr = dniField.getText().trim();
            if (dniStr.isBlank()) {
                mostrarError("Error: Debe ingresar un DNI.");
                return;
            }

            int dni = Integer.parseInt(dniStr);
            if (dni <= 0) {
                mostrarError("Error: El DNI debe ser un número positivo.");
                return;
            }

            // 2. Buscar al Socio
            Socio socio = miBiblioteca.buscarSocio(dni);

            // 3. Si no existe, informar
            if (socio == null) {
                mostrarError("Socio no encontrado con DNI: " + dni);
                return;
            }

            // 4. Si existe, construir el reporte
            StringBuilder sb = new StringBuilder();
            sb.append("--- Resultado para: ").append(socio.getNombre()).append(" ---\n");
            sb.append("DNI: ").append(socio.getDniSocio()).append("\n");
            sb.append("Tipo de Socio: ").append(socio.soyDeLaClase()).append("\n");
            sb.append("Libros prestados actualmente: ").append(socio.cantLibrosPrestados()).append("\n\n");

            // 5. Verificar Habilitación
            if (socio.puedePedir()) {
                resultadoArea.setForeground(COLOR_VERDE_OSCURO);
                sb.append("¡SOCIO HABILITADO!\n");
                sb.append("El socio puede pedir nuevos libros.");
            } else {
                resultadoArea.setForeground(COLOR_ROJO);
                sb.append("🚫 SOCIO NO HABILITADO.\n");
                sb.append("El socio NO puede pedir nuevos libros.\n\n");
                sb.append("Causa(s) probable(s):\n");

                if (socio instanceof Estudiante && socio.cantLibrosPrestados() > 3) {
                    sb.append("  - Excede el límite de 3 libros (tiene ").append(socio.cantLibrosPrestados()).append(").\n");
                }
                sb.append("  - Tiene al menos un préstamo vencido.\n");
            }

            // 6. Mostrar el resultado
            resultadoArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            mostrarError("Error: El DNI debe ser un número válido.");
        } catch (Exception ex) {
            mostrarError("Ocurrió un error inesperado: " + ex.getMessage());
        } finally {
            // Limpiamos el campo de DNI para la próxima consulta
            dniField.setText("");
        }
    }

    /**
     * Método ayudante para mostrar un error en el área de texto.
     * @param mensaje El mensaje de error.
     */
    private void mostrarError(String mensaje) {
        resultadoArea.setForeground(COLOR_ROJO);
        resultadoArea.setText(mensaje);
    }
}