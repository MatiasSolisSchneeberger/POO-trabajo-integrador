import javax.swing.*;
import java.awt.*;

public class VentanaMain extends JFrame {
    private Biblioteca miBiblioteca;

    private JPanel main;
    private JPanel body;
    private JPanel header;
    private JPanel principal;
    private JPanel socios;
    private JPanel libros;
    private JPanel prestamosDevoluciones;
    private JPanel consultasInformes;

    private JButton gestionDeSociosButton;
    private JButton prestamosYDevolucionesButton;
    private JButton gestionDeLibrosButton;
    private JButton consultasEInformesButton;
    private JButton salirButton;
    private JButton cambiarDiasDePrestamoButton;
    private JButton listarSociosButton;
    private JButton agregarEstudiandteButton;
    private JButton quitarSocioButton;
    private JButton agregarDocenteButton;
    private JButton volverSociosButton;
    private JButton agregarLibroButton;
    private JButton quitarLibroButton;
    private JButton listarLibrosConEstadoButton;
    private JButton listarTitulosButton;
    private JButton volverLibrosButton;
    private JButton realizarPrestamoButton;
    private JButton devolverLibroButton;
    private JButton verificarHabilitacionSocioButton;
    private JButton volverPresDevoButton;
    private JButton listarPrestamosVencidosButton;
    private JButton listarDocentesResponsablesButton;
    private JButton volverConsInfoButton;
    private JButton cantidadDeSociosPorButton;
    private JButton buscarLibroEspecificoButton;

    private JLabel opcionesLabel;
    private JLabel sociosLabel;
    private JLabel librosLabel;
    private JLabel gestionarPresYDevoLabel;
    private JLabel consultasEInformesLabel;
    private JLabel gestionDeBibliotecaLabel;

    private static final String PANEL_PRINCIPAL = "Card1";
    private static final String PANEL_SOCIOS = "Card2";
    private static final String PANEL_LIBROS = "Card4";
    private static final String PANEL_PRESTAMOS = "Card5";
    private static final String PANEL_CONSULTAS = "Card6";

    VentanaMain(Biblioteca p_biblioteca){
        this.miBiblioteca = p_biblioteca;

        setContentPane(main);
        setTitle("Gestion de Biblioteca | " + p_biblioteca.getNombre()); // Pone el título
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));

        // Titulo
        if (miBiblioteca.getNombre() != null) {
            gestionDeBibliotecaLabel.setText("Gestion De Biblioteca | " + miBiblioteca.getNombre());
        }

        // --- A. Botones de Navegación Principal ---
        gestionDeSociosButton.addActionListener(e -> mostrarPanel(PANEL_SOCIOS));
        gestionDeLibrosButton.addActionListener(e -> mostrarPanel(PANEL_LIBROS));
        prestamosYDevolucionesButton.addActionListener(e -> mostrarPanel(PANEL_PRESTAMOS));
        consultasEInformesButton.addActionListener(e -> mostrarPanel(PANEL_CONSULTAS));

        salirButton.addActionListener(e -> System.exit(0));

        // --- B. Botones de "Volver" ---
        volverSociosButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverLibrosButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverPresDevoButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverConsInfoButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));

        // --- C. Botones de Funcionalidad (con placeholder) ---

        // --- Panel Socios

        // Agregar nuevo Estudiante
        agregarEstudiandteButton.addActionListener(e -> {
            // 1. Crea la nueva ventana de diálogo
            //    'this' es VentanaMain (el JFrame 'owner')
            //    'miBiblioteca' es la instancia que VentanaMain ya tiene
            VentAgreEstu dialogo = new VentAgreEstu(this, miBiblioteca);

            // 2. La hace visible
            //    El código se pausará aquí hasta que el diálogo se cierre
            dialogo.setVisible(true);
        });

        // Agregar nuevo Docente
        agregarDocenteButton.addActionListener(e -> {
            // 1. Crea la nueva ventana de diálogo para Docente
            VentAgreDoce dialogo = new VentAgreDoce(this, miBiblioteca);
            // 2. La hace visible (y modal)
            dialogo.setVisible(true);
        });
        // Listar Socios
        listarSociosButton.addActionListener(e -> {
            // 1. Obtenemos el String completo de la lógica de negocio
            String listadoDeSocios = miBiblioteca.listaDeSocios();

            // 2. Creamos nuestro nuevo diálogo, pasándole el título y el contenido
            VentListSoci dialogo = new VentListSoci(
                    this, // El owner (VentanaMain)
                    "Listado de Socios", // El título de la ventana
                    listadoDeSocios      // El String que mostrará
            );

            // 3. Mostramos la ventana
            dialogo.setVisible(true);
        });

        // Quitar Socio
        quitarSocioButton.addActionListener(e -> {
            // 1. Creamos la nueva ventana de diálogo para Quitar Socio
            VentQuitarSocio dialogo = new VentQuitarSocio(this, miBiblioteca);
            // 2. La hacemos visible (y modal)
            dialogo.setVisible(true);
        });


        // ---- Panel Libros ----
        agregarLibroButton.addActionListener(e -> mostrarPlaceholder("Agregar Libro"));
        quitarLibroButton.addActionListener(e -> mostrarPlaceholder("Quitar Libro"));
        listarLibrosConEstadoButton.addActionListener(e -> mostrarPlaceholder("Listar Libros (con estado)"));
        listarTitulosButton.addActionListener(e -> mostrarPlaceholder("Listar Títulos"));



        // ---- Panel Préstamos ----
        realizarPrestamoButton.addActionListener(e -> mostrarPlaceholder("Realizar Préstamo"));
        devolverLibroButton.addActionListener(e -> mostrarPlaceholder("Devolver Libro"));
        verificarHabilitacionSocioButton.addActionListener(e -> mostrarPlaceholder("Verificar Habilitación Socio"));



        // ---- Panel Consultas ----
        listarPrestamosVencidosButton.addActionListener(e -> mostrarPlaceholder("Listar Préstamos Vencidos"));
        listarDocentesResponsablesButton.addActionListener(e -> mostrarPlaceholder("Listar Docentes Responsables"));
        cantidadDeSociosPorButton.addActionListener(e -> mostrarPlaceholder("Cantidad de Socios por Tipo"));
        buscarLibroEspecificoButton.addActionListener(e -> mostrarPlaceholder("Buscar Libro Específico"));

        // Aseguramos que el panel principal sea el primero en mostrarse
        mostrarPanel(PANEL_PRINCIPAL);

        // 6. Hacer visible el JFrame
        setVisible(true);
    }

    private void mostrarPlaceholder(String funcionalidad) {
        JOptionPane.showMessageDialog(this,
                "'" + funcionalidad + "' aún no implementado.",
                "En Desarrollo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarPanel(String panelNombre) {
        CardLayout cl = (CardLayout) (body.getLayout());
        cl.show(body, panelNombre);
    }

}