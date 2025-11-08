import javax.swing.*;
import java.awt.*;

/**
 * Ventana de formulario para agregar un docente.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaFormularioDocente extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtDni;
    private JTextField txtNombre;
    private JTextField txtArea;

    public VentanaFormularioDocente(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Agregar Docente");
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
        JLabel titulo = new JLabel("Nuevo Docente");
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

        // Área
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Área:"), gbc);

        txtArea = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtArea, gbc);

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
            String area = txtArea.getText().trim();

            if (nombre.isEmpty() || area.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            biblioteca.nuevoSocioDocente(dni, nombre, area);
            JOptionPane.showMessageDialog(this, "Docente agregado exitosamente");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}
