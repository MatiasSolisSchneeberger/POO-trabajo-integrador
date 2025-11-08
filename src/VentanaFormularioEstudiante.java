import javax.swing.*;
import java.awt.*;

/**
 * Ventana de formulario para agregar un estudiante.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaFormularioEstudiante extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtDni;
    private JTextField txtNombre;
    private JTextField txtCarrera;

    public VentanaFormularioEstudiante(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Agregar Estudiante");
        setSize(400, 250);
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
        JLabel titulo = new JLabel("Nuevo Estudiante");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("DNI:"), gbc);

        txtDni = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDni, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        // Carrera
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Carrera:"), gbc);

        txtCarrera = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtCarrera, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void guardar() {
        try {
            int dni = Integer.parseInt(txtDni.getText().trim());
            String nombre = txtNombre.getText().trim();
            String carrera = txtCarrera.getText().trim();

            if (nombre.isEmpty() || carrera.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            biblioteca.nuevoSocioEstudiante(dni, nombre, carrera);
            JOptionPane.showMessageDialog(this, "Estudiante agregado exitosamente");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}
