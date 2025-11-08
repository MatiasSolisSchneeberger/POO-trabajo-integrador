import javax.swing.*;
import java.awt.*;

/**
 * Interfaz gráfica para el sistema de gestión de biblioteca.
 * Utiliza la clase Ventana para implementar un menú completo de gestión.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class InterfazBiblioteca {

    private final Biblioteca miBiblioteca;
    private Ventana ventana;
    private JPanel panelPrincipal;

    public InterfazBiblioteca() {
        miBiblioteca = new Biblioteca("Biblioteca Central UNL");
        ventana = new Ventana();
        inicializarDatos();
        configurarVentana();
        mostrarMenuPrincipal();
    }

    private void configurarVentana() {
        ventana.configurar("Sistema de Gestión - " + miBiblioteca.getNombre(), 600, 400);
    }

    private void inicializarDatos() {
        // Socios Estudiantes
        miBiblioteca.nuevoSocioEstudiante(12345678, "Ana Garcia", "Ingeniería");
        miBiblioteca.nuevoSocioEstudiante(23456789, "Juan Perez", "Derecho");
        miBiblioteca.nuevoSocioEstudiante(34567890, "Maria Lopez", "Medicina");

        // Socios Docentes
        miBiblioteca.nuevoSocioDocente(45678901, "Dr. Carlos Ruiz", "Matemáticas");
        miBiblioteca.nuevoSocioDocente(56789012, "Lic. Laura Torres", "Historia");

        // Libros
        miBiblioteca.nuevoLibro("Cien años de soledad", 1, "Sudamericana", 1967);
        miBiblioteca.nuevoLibro("El señor de los anillos", 2, "Minotauro", 1954);
        miBiblioteca.nuevoLibro("Física I", 5, "Pearson", 2018);
        miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010);
    }

    private void mostrarMenuPrincipal() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel(miBiblioteca.getNombre() + " - Menú Principal");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JButton btnSocios = ventana.crearBoton("Gestión de Socios");
        btnSocios.addActionListener(e -> menuGestionSocios());
        panelPrincipal.add(btnSocios, gbc);

        gbc.gridy = 2;
        JButton btnLibros = ventana.crearBoton("Gestión de Libros");
        btnLibros.addActionListener(e -> menuGestionLibros());
        panelPrincipal.add(btnLibros, gbc);

        gbc.gridy = 3;
        JButton btnPrestamos = ventana.crearBoton("Préstamos y Devoluciones");
        btnPrestamos.addActionListener(e -> menuGestionPrestamos());
        panelPrincipal.add(btnPrestamos, gbc);

        gbc.gridy = 4;
        JButton btnConsultas = ventana.crearBoton("Consultas e Informes");
        btnConsultas.addActionListener(e -> menuConsultas());
        panelPrincipal.add(btnConsultas, gbc);

        gbc.gridy = 5;
        JButton btnSalir = ventana.crearBoton("Salir");
        btnSalir.setBackground(new Color(220, 53, 69));
        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(ventana,
                "¿Está seguro que desea salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panelPrincipal.add(btnSalir, gbc);

        ventana.establecerContenido(panelPrincipal);
        ventana.mostrar();
    }

    private void menuGestionSocios() {
        VentanaGestionSocios gestionSocios = new VentanaGestionSocios(miBiblioteca, ventana);
        gestionSocios.mostrarMenu();
    }

    private void menuGestionLibros() {
        VentanaGestionLibros gestionLibros = new VentanaGestionLibros(miBiblioteca, ventana);
        gestionLibros.mostrarMenu();
    }

    private void menuGestionPrestamos() {
        VentanaGestionPrestamos gestionPrestamos = new VentanaGestionPrestamos(miBiblioteca, ventana);
        gestionPrestamos.mostrarMenu();
    }

    private void menuConsultas() {
        VentanaConsultas consultas = new VentanaConsultas(miBiblioteca, ventana);
        consultas.mostrarMenu();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazBiblioteca());
    }
}
