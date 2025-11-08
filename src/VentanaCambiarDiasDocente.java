import javax.swing.*;
import java.awt.*;

/**
 * Ventana para cambiar los días de préstamo de un docente.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaCambiarDiasDocente extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtDni;
    private JTextField txtDias;

    public VentanaCambiarDiasDocente(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Cambiar Días de Préstamo");
        setSize(400, 220);
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
        JLabel titulo = new JLabel("Cambiar Días de Préstamo");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("DNI Docente:"), gbc);

        txtDni = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDni, gbc);

        // Días
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Días (+/-):"), gbc);

        txtDias = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDias, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Cambiar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> cambiar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void cambiar() {
        try {
            int dni = Integer.parseInt(txtDni.getText().trim());
            int dias = Integer.parseInt(txtDias.getText().trim());

            Socio socio = biblioteca.buscarSocio(dni);

            if (socio instanceof Docente docente) {
                if (docente.esResponsable()) {
                    docente.cambiarDiasDePrestamo(dias);
                    JOptionPane.showMessageDialog(this,
                        "Días de préstamo cambiados.\nNuevo límite: " + docente.getDiasPrestamo() + " días");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "El docente no es responsable. No se puede cambiar el límite de días.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (socio != null) {
                JOptionPane.showMessageDialog(this,
                    "El socio con DNI " + dni + " no es Docente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Socio no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}
