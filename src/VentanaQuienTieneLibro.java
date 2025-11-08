import javax.swing.*;
import java.awt.*;

/**
 * Ventana para consultar quién tiene un libro específico.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaQuienTieneLibro extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtTitulo;
    private JTextField txtEdicion;
    private JTextField txtEditorial;
    private JTextField txtAnio;

    public VentanaQuienTieneLibro(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("¿Quién tiene el libro?");
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
        JLabel titulo = new JLabel("Consultar Libro");
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
        JButton btnConsultar = new JButton("Consultar");
        JButton btnCancelar = new JButton("Cancelar");

        btnConsultar.addActionListener(e -> consultar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnConsultar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void consultar() {
        try {
            String titulo = txtTitulo.getText().trim();
            int edicion = Integer.parseInt(txtEdicion.getText().trim());
            String editorial = txtEditorial.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());

            Libro libroBuscado = buscarLibroEnLista(titulo, edicion, editorial, anio);

            if (libroBuscado == null) {
                JOptionPane.showMessageDialog(this, "Libro no encontrado en la biblioteca.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String resultado = biblioteca.quienTieneElLibro(libroBuscado);
                String mensaje = "Resultado de la consulta:\n\n" + resultado;

                if (libroBuscado.prestado()) {
                    mensaje += "\n\nEl libro está en posesión de: " +
                             libroBuscado.ultimoPrestamo().getSocio().getNombre();
                }

                JOptionPane.showMessageDialog(this, mensaje, "¿Quién tiene el libro?",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (LibroNoPrestadoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Información",
                    JOptionPane.INFORMATION_MESSAGE);
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
