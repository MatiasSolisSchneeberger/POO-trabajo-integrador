import javax.swing.*;
import java.awt.*;

/**
 * VentCambiarDias (Ventana Cambiar Días de Préstamo)
 *
 * JDialog modal que permite buscar un socio por DNI,
 * verificar si es un Docente Responsable, y si lo es,
 * modificar sus días de préstamo.
 *
 * @author (Tu Nombre)
 * @version 1.1 (09-Nov-2025)
 */
public class VentCambiarDias extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField dniField;
    private JButton buscarButton;
    private JTextArea infoArea; // Para mostrar el estado del socio
    private JTextField diasField;
    private JButton aplicarButton;
    private JButton cerrarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /** Almacena el docente encontrado para usarlo al "Aplicar" */
    private Docente docenteEncontrado;

    // --- Constantes ---
    private static final Color COLOR_ROJO = new Color(220, 53, 69);
    private static final Color COLOR_VERDE_OSCURO = new Color(0, 128, 0);

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentCambiarDias(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Cambiar Días de Préstamo (Docente)", true); // true = MODAL
        this.miBiblioteca = biblioteca;
        this.docenteEncontrado = null; // Inicialmente no hay nadie

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

        // --- Panel de Búsqueda (NORTE) ---
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(new JLabel("DNI del Docente:"));
        dniField = new JTextField(15);
        buscarButton = new JButton("Buscar");
        northPanel.add(dniField);
        northPanel.add(buscarButton);

        // --- Panel de Información (CENTRO) ---
        infoArea = new JTextArea(5, 40); // 5 filas, 40 columnas
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        infoArea.setText("Ingrese un DNI y presione 'Buscar'...");
        JScrollPane scrollPane = new JScrollPane(infoArea);

        // --- Panel de Acción (SUR) ---
        // Usamos un Boxlayout vertical para apilar la acción y el cierre
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        // Sub-panel para la acción de cambiar días
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.add(new JLabel("Días a sumar/restar (ej: 3, -2):"));
        diasField = new JTextField(5);
        aplicarButton = new JButton("Aplicar Cambio");
        actionPanel.add(diasField);
        actionPanel.add(aplicarButton);

        // Sub-panel para el botón de cerrar
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");
        cerrarButton.setForeground(COLOR_ROJO);
        closePanel.add(cerrarButton);

        southPanel.add(actionPanel);
        southPanel.add(closePanel);

        // --- Ensamblar ---
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        // Estado inicial: solo la búsqueda está habilitada
        habilitarCamposAccion(false);

        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        // "Enter" activa el botón "Buscar" (por defecto)
        getRootPane().setDefaultButton(buscarButton);
        dniField.addActionListener(e -> onBuscar());

        buscarButton.addActionListener(e -> onBuscar());
        aplicarButton.addActionListener(e -> onAplicar());
        cerrarButton.addActionListener(e -> dispose());
    }

    /**
     * Lógica que se ejecuta al presionar "Buscar".
     * Valida el DNI y habilita la acción si es un docente responsable.
     */
    private void onBuscar() {
        // Al buscar, siempre reseteamos
        habilitarCamposAccion(false);
        this.docenteEncontrado = null;

        try {
            // 1. Validar DNI
            String dniStr = dniField.getText().trim();
            if (dniStr.isBlank()) {
                mostrarError("Error: Debe ingresar un DNI.");
                return;
            }
            int dni = Integer.parseInt(dniStr);

            // 2. Buscar Socio
            Socio socio = miBiblioteca.buscarSocio(dni);

            // 3. Validar tipo de Socio
            if (socio == null) {
                mostrarError("Socio no encontrado con DNI: " + dni);
            } else if (socio instanceof Docente) {
                Docente docente = (Docente) socio;
                // 4. Validar si es Responsable
                if (docente.esResponsable()) {
                    // ¡Éxito! Habilitamos la acción
                    this.docenteEncontrado = docente;
                    mostrarInfo("✅ Docente encontrado: " + docente.getNombre() +
                            "\n   Estado: Responsable" +
                            "\n   Límite actual: " + docente.getDiasPrestamo() + " días.");
                    habilitarCamposAccion(true);
                } else {
                    mostrarError("ℹ️ El socio es un Docente, pero NO es Responsable.\n   No se pueden modificar sus días de préstamo.");
                }
            } else {
                mostrarError("ℹ️ El socio encontrado no es un Docente, es un " + socio.soyDeLaClase() + ".");
            }

        } catch (NumberFormatException ex) {
            mostrarError("Error: El DNI debe ser un número válido.");
        }
    }

    /**
     * Lógica que se ejecuta al presionar "Aplicar Cambio".
     */
    private void onAplicar() {
        if (this.docenteEncontrado == null) {
            mostrarError("Error: No hay un docente válido seleccionado. Vuelva a buscar.");
            habilitarCamposAccion(false);
            return;
        }

        try {
            // 1. Validar Días
            String diasStr = diasField.getText().trim();
            if (diasStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número de días.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int dias = Integer.parseInt(diasStr); // Acepta "3", "-2", "+5"

            // 2. Aplicar lógica de negocio
            docenteEncontrado.cambiarDiasDePrestamo(dias);

            // 3. Informar éxito y resetear
            JOptionPane.showMessageDialog(this,
                    "¡Días de préstamo actualizados!\n\n" +
                            "Docente: " + docenteEncontrado.getNombre() + "\n" +
                            "Nuevo Límite: " + docenteEncontrado.getDiasPrestamo() + " días.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Reseteamos la UI para una nueva búsqueda
            resetUI();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad de días debe ser un número (ej: 5 o -2).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Métodos Ayudantes (Helpers) ---

    /** Habilita o deshabilita la parte de "Aplicar Cambio" */
    private void habilitarCamposAccion(boolean habilitar) {
        diasField.setEnabled(habilitar);
        aplicarButton.setEnabled(habilitar);
        if (habilitar) {
            diasField.requestFocus();
        }
    }

    /** Muestra un mensaje de error en el JTextArea */
    private void mostrarError(String mensaje) {
        infoArea.setForeground(COLOR_ROJO);
        infoArea.setText(mensaje);
    }

    /** Muestra un mensaje de información en el JTextArea */
    private void mostrarInfo(String mensaje) {
        infoArea.setForeground(COLOR_VERDE_OSCURO);
        infoArea.setText(mensaje);
    }

    /** Resetea la UI a su estado inicial */
    private void resetUI() {
        docenteEncontrado = null;
        dniField.setText("");
        diasField.setText("");
        infoArea.setText("Ingrese un DNI y presione 'Buscar'...");
        infoArea.setForeground(Color.BLACK);
        habilitarCamposAccion(false);
        dniField.requestFocus();
    }
}