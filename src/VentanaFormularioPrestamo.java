import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Ventana de formulario para realizar un préstamo.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaFormularioPrestamo extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtDniSocio;
    private JTextField txtTitulo;
    private JTextField txtEdicion;
    private JTextField txtEditorial;
    private JTextField txtAnio;

    public VentanaFormularioPrestamo(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Realizar Préstamo");
        setSize(400, 350);
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
        JLabel titulo = new JLabel("Nuevo Préstamo");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // DNI Socio
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("DNI Socio:"), gbc);

        txtDniSocio = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDniSocio, gbc);

        // Título del libro
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Título:"), gbc);

        txtTitulo = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtTitulo, gbc);

        // Edición
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Edición:"), gbc);

        txtEdicion = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEdicion, gbc);

        // Editorial
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Editorial:"), gbc);

        txtEditorial = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEditorial, gbc);

        // Año
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Año:"), gbc);

        txtAnio = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtAnio, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnPrestar = new JButton("Prestar");
        JButton btnCancelar = new JButton("Cancelar");

        btnPrestar.addActionListener(e -> prestar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnPrestar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void prestar() {
        try {
            int dni = Integer.parseInt(txtDniSocio.getText().trim());
            String titulo = txtTitulo.getText().trim();
            int edicion = Integer.parseInt(txtEdicion.getText().trim());
            String editorial = txtEditorial.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());

            Socio socio = biblioteca.buscarSocio(dni);
            Libro libroAPrestar = buscarLibroEnLista(titulo, edicion, editorial, anio);

            if (socio == null) {
                JOptionPane.showMessageDialog(this, "Socio con DNI " + dni + " no encontrado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (libroAPrestar == null) {
                JOptionPane.showMessageDialog(this, "Libro no encontrado en la biblioteca.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Calendar fechaRetiro = new GregorianCalendar();

            if (biblioteca.prestarLibro(fechaRetiro, socio, libroAPrestar)) {
                JOptionPane.showMessageDialog(this,
                    "Préstamo realizado con éxito.\n" +
                    "Socio: " + socio.getNombre() + "\n" +
                    "Libro: " + libroAPrestar.getTitulo() + "\n" +
                    "Días límite de préstamo: " + socio.getDiasPrestamo() + " días");
                dispose();
            } else {
                String mensaje = "Préstamo NO realizado.\n";
                if (!socio.puedePedir()) {
                    mensaje += "Razón: El socio no está habilitado para pedir.\n";
                }
                if (libroAPrestar.prestado()) {
                    mensaje += "Razón: El libro ya está prestado.";
                }
                JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
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
