import javax.swing.*;
import java.awt.*; // Para Layouts, Color, etc.

/**
 * VentVerificarSocio (Ventana Verificar Habilitación de Socio)
 *
 * JDialog modal que permite al usuario ingresar un DNI y ver
 * el estado de habilitación de ese socio en un área de texto,
 * sin cerrar la ventana.
 */
public class VentVerificarSocio extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField dniField;
    private JButton verificarButton;
    private JTextArea resultadoArea; // Área para mostrar el resultado
    private JButton cerrarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentVerificarSocio(JFrame owner, Biblioteca biblioteca) {

        // 1. Configuración básica del JDialog
        super(owner, "Verificar Habilitación de Socio", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel de Input (NORTE) ---
        // Usamos un FlowLayout alineado a la izquierda
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("DNI del Socio:"));
        dniField = new JTextField(15);
        verificarButton = new JButton("Verificar");
        inputPanel.add(dniField);
        inputPanel.add(verificarButton);

        // --- Panel de Resultados (CENTRO) ---
        // Un área de texto con barras de scroll
        resultadoArea = new JTextArea(10, 50); // 10 filas, 50 columnas
        resultadoArea.setEditable(false); // No se puede escribir
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Letra tipo consola
        resultadoArea.setText("Ingrese un DNI y presione 'Verificar'...");

        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // --- Panel de Botones (SUR) ---
        // FlowLayout alineado a la derecha
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");

        // --- Estilo de Botones (Solo color de texto) ---
        // Como pediste, solo 'Cerrar' tendrá color de texto rojo
        cerrarButton.setForeground(new Color(220, 53, 69)); // Rojo

        southPanel.add(cerrarButton);

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(inputPanel, BorderLayout.NORTH); // Input arriba
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Resultado en el medio
        mainPanel.add(southPanel, BorderLayout.SOUTH); // Botón de cierre abajo
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(owner); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // --- 5. Funcionalidad "Enter" ---
        // "Enter" en el campo de texto o en la ventana activará el botón "Verificar"
        getRootPane().setDefaultButton(verificarButton);
        dniField.addActionListener(e -> onVerificar());

        // --- 6. Action Listeners ---
        verificarButton.addActionListener(e -> onVerificar());
        cerrarButton.addActionListener(e -> dispose()); // Cierra la ventana
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
                resultadoArea.setForeground(Color.RED);
                resultadoArea.setText("Error: Debe ingresar un DNI.");
                return;
            }

            int dni = Integer.parseInt(dniStr);
            if (dni <= 0) {
                resultadoArea.setForeground(Color.RED);
                resultadoArea.setText("Error: El DNI debe ser un número positivo.");
                return;
            }

            // 2. Buscar al Socio
            Socio socio = miBiblioteca.buscarSocio(dni);

            // 3. Si no existe, informar
            if (socio == null) {
                resultadoArea.setForeground(Color.RED);
                resultadoArea.setText("Socio no encontrado con DNI: " + dni);
                return;
            }

            // 4. Si existe, construir el reporte
            // Usamos StringBuilder para construir el texto eficientemente
            StringBuilder sb = new StringBuilder();
            sb.append("--- Resultado para: ").append(socio.getNombre()).append(" ---\n");
            sb.append("DNI: ").append(socio.getDniSocio()).append("\n");
            sb.append("Tipo de Socio: ").append(socio.soyDeLaClase()).append("\n");
            sb.append("Libros prestados actualmente: ").append(socio.cantLibrosPrestados()).append("\n\n");

            // 5. Verificar Habilitación (la lógica central)
            if (socio.puedePedir()) {
                // HABILITADO
                resultadoArea.setForeground(new Color(0, 128, 0)); // Verde oscuro
                sb.append("¡SOCIO HABILITADO!\n");
                sb.append("El socio puede pedir nuevos libros.");
            } else {
                // NO HABILITADO
                resultadoArea.setForeground(Color.RED);
                sb.append("SOCIO NO HABILITADO.\n");
                sb.append("El socio NO puede pedir nuevos libros.\n\n");
                sb.append("Causa(s) probable(s):\n");

                // Replicamos la lógica de TestBiblioteca para dar más detalle
                if (socio instanceof Estudiante) {
                    if (socio.cantLibrosPrestados() > 3) {
                        sb.append("  - Excede el límite de 3 libros (tiene ").append(socio.cantLibrosPrestados()).append(").\n");
                    }
                }

                // Esta es la causa más común para todos los tipos de socios
                sb.append("  - Tiene al menos un préstamo vencido.\n");
            }

            // 6. Mostrar el resultado
            resultadoArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            resultadoArea.setForeground(Color.RED);
            resultadoArea.setText("Error: El DNI debe ser un número válido.");
        } catch (Exception ex) {
            resultadoArea.setForeground(Color.RED);
            resultadoArea.setText("Ocurrió un error inesperado: " + ex.getMessage());
        }

        // Limpiamos el campo de DNI para la próxima consulta
        dniField.setText("");
    }
}