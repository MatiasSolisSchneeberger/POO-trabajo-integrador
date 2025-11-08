import javax.swing.*;
import java.awt.*;

/**
 * Ventana para quitar un socio de la biblioteca.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaQuitarSocio extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtDni;

    public VentanaQuitarSocio(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Quitar Socio");
        setSize(350, 180);
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
        JLabel titulo = new JLabel("Eliminar Socio");
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

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");

        btnEliminar.addActionListener(e -> eliminar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void eliminar() {
        try {
            int dni = Integer.parseInt(txtDni.getText().trim());
            Socio socio = biblioteca.buscarSocio(dni);

            if (socio != null) {
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar a " + socio.getNombre() + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    biblioteca.quitarSocio(socio);
                    JOptionPane.showMessageDialog(this, "Socio eliminado exitosamente");
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}
