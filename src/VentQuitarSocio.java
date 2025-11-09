import javax.swing.*;
import java.awt.*; // Para BorderLayout, FlowLayout, Font, Color

/**
 * VentQuitarSocio (Ventana Quitar Socio)
 * <p>
 * Este JDialog es un ejemplo de una interfaz más compleja.
 * Muestra información (el listado) y permite realizar acciones (quitar)
 * en la misma pantalla, lo cual es muy cómodo para el usuario.
 */
public class VentQuitarSocio extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextArea textAreaSocios;   // Para mostrar el listado
    private JScrollPane scrollPane;     // Para dar scroll al listado
    private JTextField dniField;        // Para escribir el DNI a quitar
    private JButton quitarButton;
    private JButton cerrarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentQuitarSocio(JFrame owner, Biblioteca biblioteca) {

        // 1. Configuración básica del JDialog
        super(owner, "Quitar Socio", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout. Tendrá el listado
        // en 'CENTER' y la barra de acción en 'SOUTH'.
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel del Listado (CENTER) ---

        // Creamos el JTextArea (20 filas, 60 columnas de "ancho sugerido")
        textAreaSocios = new JTextArea(20, 60);
        textAreaSocios.setEditable(false); // El usuario no puede modificar el listado
        // Usamos fuente Monospaced para que se alinee bien, como en la consola
        textAreaSocios.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        // "Envolvemos" el JTextArea en un JScrollPane
        scrollPane = new JScrollPane(textAreaSocios);

        // --- Panel de Acción (SOUTH) ---

        // Este panel usará un BorderLayout para organizar sus dos partes:
        // 1. (West) El campo de DNI y el botón Quitar
        // 2. (East) El botón de Cerrar
        JPanel southPanel = new JPanel(new BorderLayout());

        // Panel para la entrada de DNI y botón de Quitar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.add(new JLabel("DNI del Socio a Quitar:"));
        dniField = new JTextField(10); // 10 caracteres para el DNI
        actionPanel.add(dniField);
        quitarButton = new JButton("Quitar");
        actionPanel.add(quitarButton);

        // Panel para el botón de Cerrar
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cerrarButton = new JButton("Cerrar");
        closePanel.add(cerrarButton);

        // Agregamos los sub-paneles al panel 'southPanel'
        southPanel.add(actionPanel, BorderLayout.WEST);
        southPanel.add(closePanel, BorderLayout.EAST);

        // --- Colores de Botones ---
        quitarButton.setForeground(new Color(220, 53, 69));

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(scrollPane, BorderLayout.CENTER); // El listado en el centro
        mainPanel.add(southPanel, BorderLayout.SOUTH); // La barra de acción abajo
        setContentPane(mainPanel);

        // --- 4. Cargar datos y configurar JDialog ---

        // Llamamos a un método propio para cargar (y recargar) la lista
        cargarListaSocios();

        pack(); // Ajusta el tamaño
        setLocationRelativeTo(owner); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        // --- 5. Action Listeners ---
        quitarButton.addActionListener(e -> onQuitar());
        cerrarButton.addActionListener(e -> dispose()); // dispose() cierra la ventana

        // Hacemos que "Enter" en el campo de texto active el botón de Quitar
        dniField.addActionListener(e -> onQuitar());

        // También podemos hacer que 'Enter' en general active el botón
        getRootPane().setDefaultButton(quitarButton);
    }

    /**
     * Método privado para cargar o recargar el texto de la lista de socios.
     * Lo hacemos un método separado para poder llamarlo después de eliminar
     * un socio y así ¡actualizar la lista al instante!
     */
    private void cargarListaSocios() {
        String listado = miBiblioteca.listaDeSocios();
        textAreaSocios.setText(listado);
        // Llevamos el scroll al principio
        textAreaSocios.setCaretPosition(0);
    }

    /**
     * Lógica que se ejecuta al presionar "Quitar".
     */
    private void onQuitar() {
        try {
            // 1. Obtener y validar el DNI
            String dniStr = dniField.getText().trim(); // .trim() quita espacios

            if (dniStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dni = Integer.parseInt(dniStr);

            if (dni <= 0) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Buscar al socio en la biblioteca
            Socio socio = miBiblioteca.buscarSocio(dni);

            // 3. Verificar si el socio existe
            if (socio == null) {
                JOptionPane.showMessageDialog(this, "No se encontró ningún socio con el DNI: " + dni, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. ¡Confirmación! Esto es MUY importante antes de borrar.
            String mensaje = "¿Está seguro que desea eliminar al socio?\n\n"
                    + "Nombre: " + socio.getNombre() + "\n"
                    + "DNI: " + socio.getDniSocio();

            // Mostramos un diálogo de SÍ/NO
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    mensaje,
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE // Ícono de advertencia
            );

            // 5. Si el usuario confirma...
            if (respuesta == JOptionPane.YES_OPTION) {
                // ...llamamos a la lógica de negocio
                miBiblioteca.quitarSocio(socio);

                // Damos feedback de éxito
                JOptionPane.showMessageDialog(this, "Socio eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // --- ¡La parte clave! ---
                cargarListaSocios();  // Recargamos la lista para que se vea el cambio
                dniField.setText(""); // Limpiamos el campo de DNI
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}