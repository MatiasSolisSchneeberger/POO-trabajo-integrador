import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * VentBuscarLibro (Ventana Buscar Libro Específico)
 *
 * JDialog modal que permite al usuario ingresar un TÍTULO y ver
 * el estado de TODAS las copias de ese libro en la biblioteca.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentBuscarLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField tituloField;
    private JButton buscarButton;
    private JTextArea resultadoArea;
    private JButton cerrarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    // --- Constantes ---
    private static final Color COLOR_ROJO = new Color(220, 53, 69);

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentBuscarLibro(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Buscar Libro Específico por Título", true); // true = MODAL
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
        inputPanel.add(new JLabel("Título del Libro:"));
        tituloField = new JTextField(25);
        buscarButton = new JButton("Buscar");
        inputPanel.add(tituloField);
        inputPanel.add(buscarButton);

        // --- Panel de Resultados (CENTRO) ---
        resultadoArea = new JTextArea(15, 60);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setText("Ingrese un título y presione 'Buscar'...");

        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // --- Panel de Botones (SUR) ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");
        cerrarButton.setForeground(COLOR_ROJO); // Rojo

        southPanel.add(cerrarButton);

        // --- Ensamblar la ventana ---
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
        // "Enter" activa el botón "Buscar"
        getRootPane().setDefaultButton(buscarButton);
        tituloField.addActionListener(e -> onBuscar());

        buscarButton.addActionListener(e -> onBuscar());
        cerrarButton.addActionListener(e -> dispose()); // Cierra la ventana
    }


    /**
     * Lógica que se ejecuta al presionar "Buscar".
     * Muestra el resultado en el JTextArea 'resultadoArea'.
     */
    private void onBuscar() {
        try {
            // 1. Obtener y validar Título
            String titulo = tituloField.getText().trim();
            if (titulo.isBlank()) {
                resultadoArea.setForeground(Color.RED);
                resultadoArea.setText("Error: Debe ingresar un título.");
                return;
            }

            // 2. Lógica de búsqueda (Manual)
            ArrayList<Libro> copiasEncontradas = new ArrayList<>();
            for (Libro libro : miBiblioteca.getLibros()) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    copiasEncontradas.add(libro);
                }
            }

            // 3. Si no se encuentra nada
            if (copiasEncontradas.isEmpty()) {
                resultadoArea.setForeground(Color.BLACK);
                resultadoArea.setText("No se encontraron copias del libro con el título: '" + titulo + "'");
                return;
            }

            // 4. Si se encuentran libros, construir el reporte
            StringBuilder sb = new StringBuilder();
            sb.append("--- Se encontraron ").append(copiasEncontradas.size()).append(" copia(s) de '").append(titulo).append("' ---\n\n");

            int copiaNum = 1;
            for (Libro libro : copiasEncontradas) {
                sb.append("Copia ").append(copiaNum++).append(":\n");
                sb.append("  Edición: ").append(libro.getEdicion()).append("\n");
                sb.append("  Editorial: ").append(libro.getEditorial()).append("\n");
                sb.append("  Año: ").append(libro.getAnio()).append("\n");

                // 5. Verificar estado
                if (libro.prestado()) {
                    Socio socio = libro.ultimoPrestamo().getSocio();
                    sb.append("  Estado: PRESTADO\n");
                    sb.append("  Socio: ").append(socio.getNombre()).append(" (DNI: ").append(socio.getDniSocio()).append(")\n");
                } else {
                    sb.append("  Estado: DISPONIBLE (en biblioteca)\n");
                }
                sb.append("\n");
            }

            // 6. Mostrar el reporte
            resultadoArea.setForeground(Color.BLACK);
            resultadoArea.setText(sb.toString());

        } catch (Exception ex) {
            resultadoArea.setForeground(Color.RED);
            resultadoArea.setText("Ocurrió un error inesperado al procesar la búsqueda: " + ex.getMessage());
        }
    }
}