import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * VentRealizarPrestamo (Ventana Realizar Préstamo)
 *
 * JDialog modal para gestionar un nuevo préstamo.
 * Simplifica la búsqueda pidiendo solo el DNI del socio y el TÍTULO del libro.
 *
 * @author Matias Solis Schneeberger
 * @version 1.1.0
 */
public class VentRealizarPrestamo extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField dniField;
    private JTextField tituloField;
    private JButton prestarButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    // --- Constantes ---
    private static final Color COLOR_ROJO = new Color(220, 53, 69);
    private static final Color COLOR_VERDE = new Color(40, 167, 69);

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentRealizarPrestamo(JFrame owner, Biblioteca biblioteca) {
        super(owner, "Realizar Préstamo", true); // true = MODAL
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

        dniField = new JTextField(20);
        tituloField = new JTextField(20);

        fieldsPanel.add(new JLabel("DNI del Socio:"));
        fieldsPanel.add(dniField);
        fieldsPanel.add(new JLabel("Título del Libro:"));
        fieldsPanel.add(tituloField);

        // --- Panel de Botones (SOUTH) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prestarButton = new JButton("Realizar Préstamo");
        cancelarButton = new JButton("Cancelar");

        // --- Colores de Texto ---
        prestarButton.setForeground(COLOR_VERDE);
        cancelarButton.setForeground(COLOR_ROJO); // Corregido de gris a rojo

        buttonPanel.add(cancelarButton);
        buttonPanel.add(prestarButton);

        // --- Ensamblar ---
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Configura las propiedades finales de este JDialog.
     */
    private void initDialog() {
        pack(); // Ajusta el tamaño
        setLocationRelativeTo(getOwner()); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
    }

    /**
     * Asigna todos los ActionListeners a los componentes.
     */
    private void initListeners() {
        getRootPane().setDefaultButton(prestarButton); // Botón por defecto para "Enter"
        prestarButton.addActionListener(e -> onPrestar());
        cancelarButton.addActionListener(e -> dispose());
    }

    /**
     * Lógica que se ejecuta al presionar "Realizar Préstamo".
     */
    private void onPrestar() {
        try {
            // 1. Obtener y validar DNI
            String dniStr = dniField.getText().trim();
            if (dniStr.isBlank() || dniStr.length() > 8) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un DNI válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int dni = Integer.parseInt(dniStr);
            if (dni <= 0) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Obtener y validar Título
            String titulo = tituloField.getText().trim();
            if (titulo.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un Título.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Buscar al Socio
            Socio socio = miBiblioteca.buscarSocio(dni);
            if (socio == null) {
                JOptionPane.showMessageDialog(this, "Socio no encontrado con DNI: " + dni, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Lógica de búsqueda de libro
            Libro libroAPrestar = null;
            boolean tituloExiste = false;

            for (Libro libro : miBiblioteca.getLibros()) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    tituloExiste = true;
                    if (!libro.prestado()) {
                        libroAPrestar = libro; // ¡Encontramos una copia disponible!
                        break;
                    }
                }
            }

            // 5. Validar resultado de la búsqueda
            if (libroAPrestar == null) {
                if (tituloExiste) {
                    JOptionPane.showMessageDialog(this, "Todas las copias de '" + titulo + "' se encuentran prestadas.", "Libro No Disponible", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún libro con el título: '" + titulo + "'", "Libro No Encontrado", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }

            // 6. Intentar el préstamo
            Calendar fechaRetiro = new GregorianCalendar();
            if (miBiblioteca.prestarLibro(fechaRetiro, socio, libroAPrestar)) {
                JOptionPane.showMessageDialog(this,
                        "Préstamo realizado con éxito:\n\n" +
                                "Socio: " + socio.getNombre() + "\n" +
                                "Libro: " + libroAPrestar.getTitulo() + " (Ed. " + libroAPrestar.getEdicion() + ")\n" +
                                "Días para devolver: " + socio.getDiasPrestamo(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Préstamo NO realizado.\n\n" +
                                "Causa probable: El socio no está habilitado para pedir (tiene préstamos vencidos o excedió su límite).",
                        "Préstamo Rechazado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}