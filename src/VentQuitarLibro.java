import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * VentQuitarLibro (Ventana Quitar Libro)
 *
 * JDialog que muestra la lista de libros (numerada) y permite
 * eliminar uno ingresando su número de la lista.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentQuitarLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextArea textAreaLibros;
    private JTextField numeroField;
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
    public VentQuitarLibro(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Quitar Libro", true); // true = MODAL
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
        textAreaLibros = new JTextArea(20, 70);
        textAreaLibros.setEditable(false);
        textAreaLibros.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textAreaLibros);

        // --- Panel de Acción (SOUTH) ---
        JPanel southPanel = new JPanel(new BorderLayout());

        // Lado izquierdo: Input y botón de Quitar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.add(new JLabel("N° del Libro a Quitar:"));
        numeroField = new JTextField(5);
        actionPanel.add(numeroField);
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
        cargarListaLibros(); // Cargar la lista al abrir
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
        numeroField.addActionListener(e -> onQuitar());
    }

    /**
     * Carga o recarga la lista de libros en el JTextArea.
     */
    private void cargarListaLibros() {
        String listado = miBiblioteca.listaDeLibros();
        textAreaLibros.setText(listado);
        textAreaLibros.setCaretPosition(0); // Scroll al inicio
    }

    /**
     * Lógica que se ejecuta al presionar "Quitar".
     */
    private void onQuitar() {
        try {
            // 1. Obtener y validar el Número
            String numStr = numeroField.getText().trim();
            if (numStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numero = Integer.parseInt(numStr);
            if (numero <= 0) {
                JOptionPane.showMessageDialog(this, "El número debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Obtener la lista y verificar el rango
            ArrayList<Libro> libros = miBiblioteca.getLibros();
            if (numero > libros.size()) {
                JOptionPane.showMessageDialog(this, "Número fuera de rango. Solo hay " + libros.size() + " libros.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Obtener el libro (Ajustamos N° 1 a índice 0)
            Libro libro = libros.get(numero - 1);

            // 4. (Validación extra) Verificar que no esté prestado
            if (libro.prestado()) {
                JOptionPane.showMessageDialog(this, "El libro '" + libro.getTitulo() + "' no se puede quitar porque está PRESTADO.", "Acción Bloqueada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5. Confirmación
            String mensaje = "¿Está seguro que desea eliminar el libro?\n\n"
                    + "N°: " + numero + "\n"
                    + "Título: " + libro.getTitulo();

            int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            // 6. Si el usuario confirma
            if (respuesta == JOptionPane.YES_OPTION) {
                miBiblioteca.quitarLibro(libro);
                JOptionPane.showMessageDialog(this, "Libro eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // ¡Actualizamos la UI!
                cargarListaLibros();
                numeroField.setText("");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El N° debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}