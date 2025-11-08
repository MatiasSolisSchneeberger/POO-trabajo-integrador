import javax.swing.*;
import java.awt.*;

/**
 * Ventana de formulario para devolver un libro.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaFormularioDevolucion extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtTitulo;
    private JTextField txtEdicion;
    private JTextField txtEditorial;
    private JTextField txtAnio;

    public VentanaFormularioDevolucion(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Devolver Libro");
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void crearFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Devolución de Libro");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // Título del libro
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Título:"), gbc);

        txtTitulo = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtTitulo, gbc);

        // Edición
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Edición:"), gbc);

        txtEdicion = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEdicion, gbc);

        // Editorial
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Editorial:"), gbc);

        txtEditorial = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEditorial, gbc);

        // Año
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Año:"), gbc);

        txtAnio = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtAnio, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnDevolver = new JButton("Devolver");
        JButton btnCancelar = new JButton("Cancelar");

        btnDevolver.addActionListener(e -> devolver());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnDevolver);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void devolver() {
        try {
            String titulo = txtTitulo.getText().trim();
            int edicion = Integer.parseInt(txtEdicion.getText().trim());
            String editorial = txtEditorial.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());

            Libro libroADevolver = buscarLibroEnLista(titulo, edicion, editorial, anio);

            if (libroADevolver == null) {
                JOptionPane.showMessageDialog(this, "Libro no encontrado en la biblioteca.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                biblioteca.devolverLibro(libroADevolver);
                JOptionPane.showMessageDialog(this,
                    "Devolución de \"" + libroADevolver.getTitulo() + "\" registrada con éxito.");
                dispose();
            } catch (LibroNoPrestadoException e) {
                JOptionPane.showMessageDialog(this, "Error en la devolución: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Libro buscarLibroEnLista(String titulo, int edicion, String editorial, int anio) {
        for (Libro libro : biblioteca.getLibros()) {
            if (libro.getTitulo().equalsIgnoreCase(titulo) &&
                libro.getEdicion() == edicion &&
                libro.getEditorial().equalsIgnoreCase(editorial) &&
                libro.getAnio() == anio) {
                return libro;
            }
        }
        return null;
    }

    public void mostrar() {
        setVisible(true);
    }
}
