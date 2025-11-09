import javax.swing.*;
import java.awt.*;

/**
 * VentDevolverLibro (Ventana Devolver Libro)
 *
 * JDialog modal para registrar la devolución de un libro.
 * Pide solamente el Título y busca la primera copia prestada que coincida.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentDevolverLibro extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField tituloField;
    private JButton devolverButton;
    private JButton cancelarButton;

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
    public VentDevolverLibro(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Registrar Devolución de Libro", true); // true = MODAL
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

        // --- Panel del Formulario (CENTER) ---
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        tituloField = new JTextField(20);
        fieldsPanel.add(new JLabel("Título del Libro a Devolver:"));
        fieldsPanel.add(tituloField);

        // --- Panel de Botones (SOUTH) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        devolverButton = new JButton("Registrar Devolución");
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setForeground(COLOR_ROJO); // Rojo

        buttonPanel.add(cancelarButton);
        buttonPanel.add(devolverButton);

        // --- Ensamblar la ventana ---
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(getOwner()); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        // Hacemos que "Devolver" sea el botón por defecto (para "Enter")
        getRootPane().setDefaultButton(devolverButton);
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
            Libro libroADevolver = null;
            boolean tituloExisteEnBiblioteca = false;

            for (Libro libro : miBiblioteca.getLibros()) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    tituloExisteEnBiblioteca = true; // El título existe...
                    if (libro.prestado()) {
                        libroADevolver = libro; // ...y esta copia está prestada.
                        break;
                    }
                }
            }

            // 3. Validar resultado de la búsqueda
            if (libroADevolver == null) {
                if (tituloExisteEnBiblioteca) {
                    JOptionPane.showMessageDialog(this, "Ese libro ya figura como devuelto.", "Libro No Prestado", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún libro con el título: '" + titulo + "'", "Libro No Encontrado", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }

            // 4. Llamar a la lógica de negocio
            miBiblioteca.devolverLibro(libroADevolver);

            // 5. Informar éxito y cerrar
            JOptionPane.showMessageDialog(this, "Devolución registrada con éxito para:\n" + libroADevolver.getTitulo(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerramos la ventana

        } catch (LibroNoPrestadoException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error de Lógica", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}