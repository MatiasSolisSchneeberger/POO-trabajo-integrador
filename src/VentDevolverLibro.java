import javax.swing.*;
import java.awt.*; // Para Layouts, Color, etc.

/**
 * VentDevolverLibro (Ventana Devolver Libro)
 * <p>
 * JDialog modal para registrar la devolución de un libro.
 * Pide solamente el Título y busca la primera copia prestada que coincida.
 */
public class VentDevolverLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField tituloField;
    private JButton devolverButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentDevolverLibro(JFrame owner, Biblioteca biblioteca) {

        // 1. Configuración básica del JDialog
        super(owner, "Registrar Devolución de Libro", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel del Formulario (CENTER) ---
        // Usamos un GridLayout de 0 filas (auto) y 2 columnas
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        tituloField = new JTextField(20);

        fieldsPanel.add(new JLabel("Título del Libro a Devolver:"));
        fieldsPanel.add(tituloField);

        // --- Panel de Botones (SOUTH) ---
        // FlowLayout alineado a la derecha para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        devolverButton = new JButton("Registrar Devolución");
        cancelarButton = new JButton("Cancelar");

        // --- Estilo de Botones (Solo color de texto) ---
        // Como pediste, solo modificamos el 'Foreground' (color del texto)
        // El botón 'devolverButton' queda con el estilo estándar de Swing.
        cancelarButton.setForeground(new Color(220, 53, 69)); // Rojo

        // Agregamos los botones al panel
        buttonPanel.add(cancelarButton);
        buttonPanel.add(devolverButton);

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(owner); // Centra sobre VentanaMain
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        // --- 5. Funcionalidad "Enter" ---
        // Hacemos que "Devolver" sea el botón por defecto al presionar Enter
        getRootPane().setDefaultButton(devolverButton);

        // --- 6. Action Listeners ---
        devolverButton.addActionListener(e -> onDevolver());
        cancelarButton.addActionListener(e -> dispose()); // Cierra la ventana
    }

    /**
     * Lógica que se ejecuta al presionar "Registrar Devolución".
     */
    private void onDevolver() {
        try {
            // 1. Obtener y validar Título
            String titulo = tituloField.getText().trim();
            if (titulo.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un Título.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Lógica de búsqueda (simplificada)
            // Buscamos la *primera copia* de ese Título que
            // figure como PRESTADA.
            Libro libroADevolver = null;
            boolean tituloExisteEnBiblioteca = false;

            for (Libro libro : miBiblioteca.getLibros()) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    tituloExisteEnBiblioteca = true; // El título existe...
                    if (libro.prestado()) {
                        libroADevolver = libro; // ...y esta copia está prestada. ¡La encontramos!
                        break; // Dejamos de buscar
                    }
                }
            }

            // 3. Validar resultado de la búsqueda
            if (libroADevolver == null) {
                // Si no encontramos un libro prestado con ese título...
                if (tituloExisteEnBiblioteca) {
                    // ...es porque el título existe, pero todas las copias ya están en la biblioteca.
                    JOptionPane.showMessageDialog(this,
                            "Ese libro ya figura como devuelto (ninguna copia está prestada).",
                            "Libro No Prestado", JOptionPane.WARNING_MESSAGE);
                } else {
                    // ...o el título directamente no existe.
                    JOptionPane.showMessageDialog(this,
                            "No se encontró ningún libro con el título: '" + titulo + "'",
                            "Libro No Encontrado", JOptionPane.ERROR_MESSAGE);
                }
                return; // Cortamos la ejecución
            }

            // 4. ¡Tenemos un libro para devolver!
            // El método 'devolverLibro' de la biblioteca se encarga de la lógica.
            miBiblioteca.devolverLibro(libroADevolver);

            // 5. Informar éxito y cerrar
            JOptionPane.showMessageDialog(this,
                    "Devolución registrada con éxito para:\n" + libroADevolver.getTitulo(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerramos la ventana

        } catch (LibroNoPrestadoException e) {
            // Esta es una segunda capa de seguridad (nuestra búsqueda ya lo previene,
            // pero el método de la biblioteca también lo valida).
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error de Lógica", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Captura genérica para otros problemas
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}