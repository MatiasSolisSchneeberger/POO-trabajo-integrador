import javax.swing.*;
import java.awt.*; // Para BorderLayout, FlowLayout, Font, Color
import java.util.ArrayList; // Para acceder a la lista de libros

/**
 * VentQuitarLibro (Ventana Quitar Libro)
 *
 * JDialog que muestra la lista de libros (numerada) y permite
 * eliminar uno ingresando su número de la lista.
 */
public class VentQuitarLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextArea textAreaLibros;     // Para mostrar el listado
    private JScrollPane scrollPane;       // Para dar scroll al listado
    private JTextField numeroField;       // Para escribir el N° del libro
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
    public VentQuitarLibro(JFrame owner, Biblioteca biblioteca) {

        // 1. Configuración básica del JDialog
        super(owner, "Quitar Libro", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel del Listado (CENTER) ---

        // Creamos el JTextArea (20 filas, 70 columnas de "ancho sugerido")
        textAreaLibros = new JTextArea(20, 70);
        textAreaLibros.setEditable(false); // No editable
        // Fuente Monospaced para que se alinee bien (como en la consola)
        textAreaLibros.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // "Envolvemos" el JTextArea en un JScrollPane
        scrollPane = new JScrollPane(textAreaLibros);

        // --- Panel de Acción (SOUTH) ---
        // (Tendrá el input a la izquierda y el botón de cerrar a la derecha)
        JPanel southPanel = new JPanel(new BorderLayout());

        // Panel para la entrada del N° y botón de Quitar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.add(new JLabel("N° del Libro a Quitar:"));
        numeroField = new JTextField(5); // 5 caracteres (ej: 99999)
        actionPanel.add(numeroField);
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
        // (Como pediste en "Quitar Socio", con fondo y texto)
        quitarButton.setForeground(new Color(220, 53, 69));

        cerrarButton.setForeground(new Color(108, 117, 125));

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(scrollPane, BorderLayout.CENTER); // El listado en el centro
        mainPanel.add(southPanel, BorderLayout.SOUTH); // La barra de acción abajo
        setContentPane(mainPanel);

        // --- 4. Cargar datos y configurar JDialog ---

        // Llamamos a un método propio para (re)cargar la lista
        cargarListaLibros();

        pack(); // Ajusta el tamaño
        setLocationRelativeTo(owner); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        // --- 5. Action Listeners ---
        quitarButton.addActionListener(e -> onQuitar());
        cerrarButton.addActionListener(e -> dispose()); // dispose() cierra la ventana

        // Hacemos que "Enter" active el botón de Quitar
        getRootPane().setDefaultButton(quitarButton);
    }

    /**
     * Carga o recarga la lista de libros en el JTextArea.
     */
    private void cargarListaLibros() {
        String listado = miBiblioteca.listaDeLibros();
        textAreaLibros.setText(listado);
        // Llevamos el scroll al principio
        textAreaLibros.setCaretPosition(0);
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

            int numero = Integer.parseInt(numStr); // N° 1, 2, 3...

            if (numero <= 0) {
                JOptionPane.showMessageDialog(this, "El número debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Obtener la lista de libros y verificar el rango
            ArrayList<Libro> libros = miBiblioteca.getLibros();

            if (numero > libros.size()) {
                JOptionPane.showMessageDialog(this, "Número fuera de rango. Solo hay " + libros.size() + " libros.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Obtener el libro (Ajustamos por índice 0. El N° 1 es el índice 0)
            Libro libro = libros.get(numero - 1);

            // 4. (Validación extra) Verificar que no esté prestado
            // (Aunque tu método 'quitarLibro' ya lo hace, es bueno
            //  avisar al usuario aquí con un mensaje claro).
            if (libro.prestado()) {
                JOptionPane.showMessageDialog(this,
                        "El libro '" + libro.getTitulo() + "' no se puede quitar porque está PRESTADO.",
                        "Acción Bloqueada",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5. Confirmación
            String mensaje = "¿Está seguro que desea eliminar el libro?\n\n"
                    + "N°: " + numero + "\n"
                    + "Título: " + libro.getTitulo();

            int respuesta = JOptionPane.showConfirmDialog(
                    this, mensaje, "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
            );

            // 6. Si el usuario confirma...
            if (respuesta == JOptionPane.YES_OPTION) {
                // ...llamamos a la lógica de negocio.
                // Tu método 'quitarLibro' busca por Título, Edición, etc.
                // pero como le pasamos el objeto exacto, lo encontrará al instante.
                miBiblioteca.quitarLibro(libro);

                JOptionPane.showMessageDialog(this, "Libro eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // ¡Actualizamos la lista!
                cargarListaLibros();
                numeroField.setText(""); // Limpiamos el campo
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El N° debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}