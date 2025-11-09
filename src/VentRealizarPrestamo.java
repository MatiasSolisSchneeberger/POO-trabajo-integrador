import javax.swing.*;
import java.awt.*;
import java.util.ArrayList; // Para buscar en la lista de libros
import java.util.Calendar;  // Para la fecha de retiro
import java.util.GregorianCalendar; // Para la fecha de retiro

/**
 * VentRealizarPrestamo (Ventana Realizar Préstamo)
 * <p>
 * JDialog modal para gestionar un nuevo préstamo.
 * Simplifica la búsqueda pidiendo solo el DNI del socio y el TÍTULO del libro.
 */
public class VentRealizarPrestamo extends JDialog {

    // --- Componentes de la Interfaz ---
    private JTextField dniField;
    private JTextField tituloField;
    private JButton prestarButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    /**
     * Constructor del diálogo
     *
     * @param owner      La ventana "padre" (VentanaMain)
     * @param biblioteca La instancia de la lógica de negocio
     */
    public VentRealizarPrestamo(JFrame owner, Biblioteca biblioteca) {

        // 1. Configuración básica del JDialog
        super(owner, "Realizar Préstamo", true); // true = MODAL
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel del Formulario (CENTER) ---
        // GridLayout (0, 2) -> N filas, 2 columnas
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

        // --- Colores de Botones ---
        // (Usando el estilo de "Quitar", con fondo y texto)
        prestarButton.setForeground(new Color(40, 167, 69));

        cancelarButton.setForeground(new Color(108, 117, 125));

        buttonPanel.add(cancelarButton);
        buttonPanel.add(prestarButton);

        // --- 3. Ensamblar la ventana ---
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---
        pack(); // Ajusta el tamaño
        setLocationRelativeTo(owner); // Centra
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        // --- 5. Funcionalidad "Enter" ---
        getRootPane().setDefaultButton(prestarButton);

        // --- 6. Action Listeners ---
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
            if (dniStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un DNI.", "Error", JOptionPane.ERROR_MESSAGE);
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

            // 4. Lógica de búsqueda de libro (como pediste)
            // Buscamos la *primera copia disponible* que coincida con el título.
            Libro libroAPrestar = null;
            boolean tituloExiste = false;

            for (Libro libro : miBiblioteca.getLibros()) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    tituloExiste = true; // Marcamos que el título sí existe
                    if (!libro.prestado()) {
                        libroAPrestar = libro; // ¡Encontramos una copia disponible!
                        break; // Dejamos de buscar
                    }
                }
            }

            // 5. Validar resultado de la búsqueda
            if (libroAPrestar == null) {
                // Si no encontramos un libro disponible...
                if (tituloExiste) {
                    // ...es porque el título existe, pero todas las copias están prestadas.
                    JOptionPane.showMessageDialog(this,
                            "Todas las copias de '" + titulo + "' se encuentran prestadas.",
                            "Libro No Disponible", JOptionPane.WARNING_MESSAGE);
                } else {
                    // ...o el título directamente no existe en la biblioteca.
                    JOptionPane.showMessageDialog(this,
                            "No se encontró ningún libro con el título: '" + titulo + "'",
                            "Libro No Encontrado", JOptionPane.ERROR_MESSAGE);
                }
                return; // Cortamos la ejecución
            }

            // 6. ¡Tenemos Socio y Libro! Intentamos el préstamo.
            // Usamos la fecha actual, como en TestBiblioteca 
            Calendar fechaRetiro = new GregorianCalendar();

            // El método prestarLibro() ya valida si el socio puede pedir
            if (miBiblioteca.prestarLibro(fechaRetiro, socio, libroAPrestar)) {
                JOptionPane.showMessageDialog(this,
                        "Préstamo realizado con éxito:\n\n" +
                                "Socio: " + socio.getNombre() + "\n" +
                                "Libro: " + libroAPrestar.getTitulo() + " (Ed. " + libroAPrestar.getEdicion() + ")\n" +
                                "Días para devolver: " + socio.getDiasPrestamo(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cerramos la ventana
            } else {
                // El préstamo falló (normalmente porque el socio no está habilitado)
                JOptionPane.showMessageDialog(this,
                        "Préstamo NO realizado. \n" +
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