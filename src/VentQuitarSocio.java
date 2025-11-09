import javax.swing.*;
import java.awt.*;

/**
 * VentQuitarSocio (Ventana Quitar Socio)
 *
 * Este JDialog muestra el listado de socios y permite
 * realizar la acción de quitar uno por DNI en la misma pantalla.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentQuitarSocio extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextArea textAreaSocios;
    private JTextField dniField;
    private JButton quitarButton;
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
    public VentQuitarSocio(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Quitar Socio", true); // Corregido de "BibliotecaSocio"
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

        // --- Panel del Listado (CENTER) ---
        textAreaSocios = new JTextArea(20, 60);
        textAreaSocios.setEditable(false);
        textAreaSocios.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12)); // Fuente estándar
        JScrollPane scrollPane = new JScrollPane(textAreaSocios);

        // --- Panel de Acción (SOUTH) ---
        JPanel southPanel = new JPanel(new BorderLayout());

        // Lado izquierdo: Input y botón de Quitar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.add(new JLabel("DNI del Socio a Quitar:")); // Corregido
        dniField = new JTextField(10);
        actionPanel.add(dniField);
        quitarButton = new JButton("Quitar");
        quitarButton.setForeground(COLOR_ROJO); // Acción destructiva
        actionPanel.add(quitarButton);

        // Lado derecho: Botón de Cerrar
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");
        cerrarButton.setForeground(COLOR_ROJO); // Botón de salida
        closePanel.add(cerrarButton);

        southPanel.add(actionPanel, BorderLayout.WEST);
        southPanel.add(closePanel, BorderLayout.EAST);

        // --- Ensamblar ---
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        cargarListaSocios(); // Cargar la lista al abrir
        pack(); // Ajusta el tamaño
        setLocationRelativeTo(getOwner()); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        quitarButton.addActionListener(e -> onQuitar());
        cerrarButton.addActionListener(e -> dispose());

        // "Enter" activa el botón de Quitar
        getRootPane().setDefaultButton(quitarButton);
        dniField.addActionListener(e -> onQuitar());
    }


    /**
     * Método privado para cargar o recargar el texto de la lista de socios.
     * Se llama al abrir y después de eliminar un socio.
     */
    private void cargarListaSocios() {
        String listado = miBiblioteca.listaDeSocios();
        textAreaSocios.setText(listado);
        textAreaSocios.setCaretPosition(0); // Scroll al inicio
    }

    /**
     * Lógica que se ejecuta al presionar "Quitar".
     */
    private void onQuitar() {
        try {
            // 1. Obtener y validar el DNI
            String dniStr = dniField.getText().trim();
            if (dniStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dni = Integer.parseInt(dniStr);
            if (dni <= 0) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Buscar al socio
            Socio socio = miBiblioteca.buscarSocio(dni);

            // 3. Verificar si existe
            if (socio == null) {
                JOptionPane.showMessageDialog(this, "No se encontró ningún socio con el DNI: " + dni, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. ¡Confirmación!
            String mensaje = "¿Está seguro que desea eliminar al socio?\n\n"
                    + "Nombre: " + socio.getNombre() + "\n"
                    + "DNI: " + socio.getDniSocio();

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    mensaje,
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // 5. Si el usuario confirma
            if (respuesta == JOptionPane.YES_OPTION) {
                miBiblioteca.quitarSocio(socio);
                JOptionPane.showMessageDialog(this, "Socio eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE); // Corregido

                // ¡Actualizamos la UI!
                cargarListaSocios();
                dniField.setText("");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}