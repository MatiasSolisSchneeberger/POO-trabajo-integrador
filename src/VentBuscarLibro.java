import javax.swing.*;
import java.awt.*; // Para Layouts, Color, etc.
import java.util.ArrayList; // Para la lista de libros

/**
 * VentBuscarLibro (Ventana Buscar Libro Específico)
 *
 * JDialog modal que permite al usuario ingresar un TÍTULO y ver
 * el estado de TODAS las copias de ese libro en la biblioteca.
 */
public class VentBuscarLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField tituloField;
    private JButton buscarButton;
    private JTextArea resultadoArea; // Área para mostrar el reporte
    private JButton cerrarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentBuscarLibro(JFrame owner, Biblioteca biblioteca) {

        // 1. Configuración básica del JDialog
        super(owner, "Buscar Libro Específico por Título", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel de Input (NORTE) ---
        // (FlowLayout alineado a la izquierda)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Título del Libro:"));
        tituloField = new JTextField(25); // Un poco más ancho para títulos
        buscarButton = new JButton("Buscar");
        inputPanel.add(tituloField);
        inputPanel.add(buscarButton);

        // --- Panel de Resultados (CENTRO) ---
        // Un área de texto con barras de scroll
        resultadoArea = new JTextArea(15, 60); // 15 filas, 60 columnas
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setText("Ingrese un título y presione 'Buscar'...");

        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // --- Panel de Botones (SUR) ---
        // (FlowLayout alineado a la derecha)
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");

        // --- Estilo de Botones (Solo color de texto) ---
        // Como pediste, 'buscarButton' es estándar
        // y 'cerrarButton' solo tiene el texto en rojo.
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
        // "Enter" activa el botón "Buscar"
        getRootPane().setDefaultButton(buscarButton);
        tituloField.addActionListener(e -> onBuscar());

        // --- 6. Action Listeners ---
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
            // Buscamos TODAS las copias que coincidan con el título.
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

                // 5. Verificar estado (esta es la lógica de "quién lo tiene")
                if (libro.prestado()) {
                    // Si está prestado, buscamos el socio
                    Socio socio = libro.ultimoPrestamo().getSocio();
                    sb.append("  Estado: PRESTADO\n");
                    sb.append("  Socio: ").append(socio.getNombre()).append(" (DNI: ").append(socio.getDniSocio()).append(")\n");
                } else {
                    // Si no está prestado, está en la biblioteca
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