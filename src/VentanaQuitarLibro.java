import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Ventana para quitar un libro de la biblioteca.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaQuitarLibro extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtTitulo;
    private JTextField txtEdicion;
    private JTextField txtEditorial;
    private JTextField txtAnio;

    public VentanaQuitarLibro(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Quitar Libro");
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
        JLabel titulo = new JLabel("Eliminar Libro");
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
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");

        btnEliminar.addActionListener(e -> eliminar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void eliminar() {
        try {
            String titulo = txtTitulo.getText().trim();
            int edicion = Integer.parseInt(txtEdicion.getText().trim());
            String editorial = txtEditorial.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());

            Libro libroAEliminar = new Libro(titulo, edicion, editorial, anio, new ArrayList<>());

            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el libro \"" + titulo + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                biblioteca.quitarLibro(libroAEliminar);
                JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente");
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edición y Año deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}
