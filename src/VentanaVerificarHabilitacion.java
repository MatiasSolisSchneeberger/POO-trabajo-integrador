import javax.swing.*;
import java.awt.*;

/**
 * Ventana para verificar la habilitación de un socio.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaVerificarHabilitacion extends JDialog {

    private Biblioteca biblioteca;
    private JTextField txtDni;

    public VentanaVerificarHabilitacion(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        configurarVentana();
        crearFormulario();
    }

    private void configurarVentana() {
        setTitle("Verificar Habilitación");
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
        JLabel titulo = new JLabel("Verificar Habilitación");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("DNI Socio:"), gbc);

        txtDni = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDni, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnVerificar = new JButton("Verificar");
        JButton btnCancelar = new JButton("Cancelar");

        btnVerificar.addActionListener(e -> verificar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnVerificar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void verificar() {
        try {
            int dni = Integer.parseInt(txtDni.getText().trim());
            Socio socio = biblioteca.buscarSocio(dni);

            if (socio == null) {
                JOptionPane.showMessageDialog(this, "Socio no encontrado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String mensaje = "Estado de Habilitación de " + socio.getNombre() +
                           " (" + socio.soyDeLaClase() + ")\n\n";

            if (socio.puedePedir()) {
                mensaje += "¡El socio está habilitado para pedir un nuevo libro!\n";
            } else {
                mensaje += "El socio NO está habilitado para pedir un nuevo libro.\n\n";

                if (socio instanceof Estudiante estudiante) {
                    if (estudiante.cantLibrosPrestados() > 3) {
                        mensaje += "Razón: Excede el límite de 3 libros prestados (" +
                                 estudiante.cantLibrosPrestados() + " actualmente).\n";
                    }
                } else {
                    mensaje += "Razón: Tiene al menos un préstamo vencido (comparado con sus " +
                             socio.getDiasPrestamo() + " días límite).\n";
                }
            }
            mensaje += "\nLibros prestados actualmente: " + socio.cantLibrosPrestados();

            JOptionPane.showMessageDialog(this, mensaje, "Habilitación de Socio",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}
