import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * VentanaMain (Vista Principal)
 * <p>
 * Esta clase es el JFrame principal de la aplicación Gestor de Biblioteca.
 * Se encarga de mostrar la navegación principal (usando un CardLayout)
 * y de lanzar los diálogos (JDialog) para cada funcionalidad específica.
 * <p>
 * Sigue un patrón de Vista-Controlador simple, donde esta clase (la Vista)
 * llama a los métodos de 'miBiblioteca' (el Controlador/Modelo) para
 * realizar las operaciones.
 *
 * @author Matias Solis Schneebrger
 * @version 1.1
 */
public class VentanaMain extends JFrame {

    // --- Referencia a la Lógica de Negocio ---
    private Biblioteca miBiblioteca;

    // --- Componentes de la Interfaz (UI) ---
    // (Declarados aquí para ser accesibles desde todos los métodos)
    private JPanel main, body, header, principal, socios, libros, prestamosDevoluciones, consultasInformes;
    private JButton gestionDeSociosButton, prestamosYDevolucionesButton, gestionDeLibrosButton, consultasEInformesButton, salirButton;
    private JButton cambiarDiasDePrestamoButton, listarSociosButton, agregarEstudiandteButton, quitarSocioButton, agregarDocenteButton, volverSociosButton;
    private JButton agregarLibroButton, quitarLibroButton, listarLibrosConEstadoButton, listarTitulosButton, volverLibrosButton;
    private JButton realizarPrestamoButton, devolverLibroButton, verificarHabilitacionSocioButton, volverPresDevoButton;
    private JButton listarPrestamosVencidosButton, listarDocentesResponsablesButton, volverConsInfoButton, cantidadDeSociosPorButton, buscarLibroEspecificoButton;
    private JLabel opcionesLabel, sociosLabel, librosLabel, gestionarPresYDevoLabel, consultasEInformesLabel, gestionDeBibliotecaLabel;

    // Layout para la navegación entre paneles
    private CardLayout cardLayout;

    // --- Constantes ---
    private static final String PANEL_PRINCIPAL = "Principal";
    private static final String PANEL_SOCIOS = "Socios";
    private static final String PANEL_LIBROS = "Libros";
    private static final String PANEL_PRESTAMOS = "Prestamos";
    private static final String PANEL_CONSULTAS = "Consultas";
    private static final Color COLOR_ROJO = new Color(220, 53, 69);

    /**
     * Constructor principal de VentanaMain.
     * Inicializa la lógica y llama a los métodos de configuración.
     *
     * @param p_biblioteca La instancia de la lógica de negocio (Modelo).
     */
    VentanaMain(Biblioteca p_biblioteca) {
        this.miBiblioteca = p_biblioteca;

        initUI();        // 1. Construye todos los componentes visuales
        initListeners(); // 2 Asigna la lógica a los botones
        initFrame();     // 3 Configura y muestra la ventana
    }

    // --- CONSTRUCCIÓN DE LA INTERFAZ ---

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     * Delega la creación de cada panel "Card" a métodos especializados.
     */
    private void initUI() {
        // 1 Panel Principal (Raíz)
        main = new JPanel(new BorderLayout(12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        main.setBackground(new Color(236, 239, 241));

        // 2 Header
        header = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        header.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189)));
        gestionDeBibliotecaLabel = new JLabel("Gestion De Biblioteca | " + miBiblioteca.getNombre());
        gestionDeBibliotecaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(gestionDeBibliotecaLabel);

        // 3 Body (Contenedor de Paneles)
        cardLayout = new CardLayout();
        body = new JPanel(cardLayout);
        body.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189)));

        // 4. Crear y agregar cada panel ("Card")
        principal = buildPrincipalPanel();
        socios = buildSociosPanel();
        libros = buildLibrosPanel();
        prestamosDevoluciones = buildPrestamosPanel();
        consultasInformes = buildConsultasPanel();

        body.add(principal, PANEL_PRINCIPAL);
        body.add(socios, PANEL_SOCIOS);
        body.add(libros, PANEL_LIBROS);
        body.add(prestamosDevoluciones, PANEL_PRESTAMOS);
        body.add(consultasInformes, PANEL_CONSULTAS);

        // 5. Ensamblar la ventana principal
        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        // 6. Asignar el panel 'main' como el contenido del JFrame
        setContentPane(main);
    }

    /**
     * Construye el panel del menú principal.
     *
     * @return El JPanel del menú principal.
     */
    private JPanel buildPrincipalPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); // Para centrar el contenido
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        opcionesLabel = new JLabel("Opciones");
        opcionesLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        opcionesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Inicializar botones
        gestionDeSociosButton = new JButton("Gestion de Socios");
        gestionDeLibrosButton = new JButton("Gestion de Libros");
        prestamosYDevolucionesButton = new JButton("Prestamos y Devoluciones");
        consultasEInformesButton = new JButton("Consultas e Informes");
        salirButton = new JButton("Salir");
        salirButton.setForeground(COLOR_ROJO);

        // Estandarizar tamaño de botones
        Dimension btnSize = new Dimension(250, 40);
        gestionDeSociosButton.setPreferredSize(btnSize);
        gestionDeLibrosButton.setPreferredSize(btnSize);
        prestamosYDevolucionesButton.setPreferredSize(btnSize);
        consultasEInformesButton.setPreferredSize(btnSize);
        salirButton.setPreferredSize(btnSize);

        // Agregar componentes al panel de botones
        panelBotones.add(opcionesLabel);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 24)));
        panelBotones.add(gestionDeSociosButton);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(gestionDeLibrosButton);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(prestamosYDevolucionesButton);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(consultasEInformesButton);
        panelBotones.add(Box.createVerticalGlue()); // Espaciador flexible que empuja 'Salir' al fondo
        panelBotones.add(salirButton);

        panel.add(panelBotones); // Agrega el panel de botones al panel centrado
        return panel;
    }

    /**
     * Construye el panel de "Gestión de Socios".
     *
     * @return El JPanel de Socios.
     */
    private JPanel buildSociosPanel() {
        sociosLabel = new JLabel("Socios");
        agregarEstudiandteButton = new JButton("Agregar Estudiante");
        agregarDocenteButton = new JButton("Agregar Docente");
        quitarSocioButton = new JButton("Quitar Socio");
        listarSociosButton = new JButton("Listar Socios");
        cambiarDiasDePrestamoButton = new JButton("Cambiar Dias de Prestamo (Docente)");
        volverSociosButton = new JButton("Volver");
        volverSociosButton.setForeground(COLOR_ROJO);

        return crearPanelDeSeccion(sociosLabel, volverSociosButton,
                agregarEstudiandteButton, agregarDocenteButton, quitarSocioButton,
                listarSociosButton, cambiarDiasDePrestamoButton);
    }

    /**
     * Construye el panel de "Gestión de Libros".
     *
     * @return El JPanel de Libros.
     */
    private JPanel buildLibrosPanel() {
        librosLabel = new JLabel("Libros");
        agregarLibroButton = new JButton("Agregar Libro");
        quitarLibroButton = new JButton("Quitar Libro");
        listarLibrosConEstadoButton = new JButton("Listar Libros (con estado)");
        listarTitulosButton = new JButton("Listar Titulos");
        volverLibrosButton = new JButton("Volver");
        volverLibrosButton.setForeground(COLOR_ROJO);

        return crearPanelDeSeccion(librosLabel, volverLibrosButton,
                agregarLibroButton, quitarLibroButton,
                listarLibrosConEstadoButton, listarTitulosButton);
    }

    /**
     * Construye el panel de "Préstamos y Devoluciones".
     *
     * @return El JPanel de Préstamos.
     */
    private JPanel buildPrestamosPanel() {
        gestionarPresYDevoLabel = new JLabel("Gestionar Prestamos y Devoluciones");
        realizarPrestamoButton = new JButton("Realizar Prestamo");
        devolverLibroButton = new JButton("Devolver Libro");
        verificarHabilitacionSocioButton = new JButton("Verificar Habilitacion Socio");
        volverPresDevoButton = new JButton("Volver");
        volverPresDevoButton.setForeground(COLOR_ROJO);

        return crearPanelDeSeccion(gestionarPresYDevoLabel, volverPresDevoButton,
                realizarPrestamoButton, devolverLibroButton, verificarHabilitacionSocioButton);
    }

    /**
     * Construye el panel de "Consultas e Informes".
     *
     * @return El JPanel de Consultas.
     */
    private JPanel buildConsultasPanel() {
        consultasEInformesLabel = new JLabel("Consultas e Informes");
        listarPrestamosVencidosButton = new JButton("Listar Prestamos Vencidos");
        listarDocentesResponsablesButton = new JButton("Listar Docentes Responsables");
        cantidadDeSociosPorButton = new JButton("Cantidad de Socios por Tipo");
        buscarLibroEspecificoButton = new JButton("Buscar Libro Especifico");
        volverConsInfoButton = new JButton("Volver");
        volverConsInfoButton.setForeground(COLOR_ROJO);

        return crearPanelDeSeccion(consultasEInformesLabel, volverConsInfoButton,
                listarPrestamosVencidosButton, listarDocentesResponsablesButton,
                cantidadDeSociosPorButton, buscarLibroEspecificoButton);
    }

    // --- 2. ASIGNACIÓN DE LÓGICA (LISTENERS) ---

    /**
     * Inicializa todos los ActionListeners de la aplicación.
     * Delega a métodos especializados por panel.
     */
    private void initListeners() {
        // Navegación principal y botones de "Volver"
        initNavegacionListeners();

        // Botones de cada panel de sección
        initSociosListeners();
        initLibrosListeners();
        initPrestamosListeners();
        initConsultasListeners();
    }

    /**
     * Asigna los listeners para la navegación principal (CardLayout) y Salir.
     */
    private void initNavegacionListeners() {
        // Botones del Menú Principal
        gestionDeSociosButton.addActionListener(e -> mostrarPanel(PANEL_SOCIOS));
        gestionDeLibrosButton.addActionListener(e -> mostrarPanel(PANEL_LIBROS));
        prestamosYDevolucionesButton.addActionListener(e -> mostrarPanel(PANEL_PRESTAMOS));
        consultasEInformesButton.addActionListener(e -> mostrarPanel(PANEL_CONSULTAS));
        salirButton.addActionListener(e -> System.exit(0));

        // Botones de "Volver"
        volverSociosButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverLibrosButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverPresDevoButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverConsInfoButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
    }

    /**
     * Asigna los listeners para los botones del panel "Socios".
     */
    private void initSociosListeners() {
        agregarEstudiandteButton.addActionListener(e -> {
            VentAgreEstu dialogo = new VentAgreEstu(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        agregarDocenteButton.addActionListener(e -> {
            VentAgreDoce dialogo = new VentAgreDoce(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        listarSociosButton.addActionListener(e -> {
            String listadoDeSocios = miBiblioteca.listaDeSocios();
            VentListSoci dialogo = new VentListSoci(this, "Listado de Socios", listadoDeSocios);
            dialogo.setVisible(true);
        });

        quitarSocioButton.addActionListener(e -> {
            VentQuitarSocio dialogo = new VentQuitarSocio(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        cambiarDiasDePrestamoButton.addActionListener(e -> mostrarPlaceholder("Cambiar Días Préstamo Docente"));
    }

    /**
     * Asigna los listeners para los botones del panel "Libros".
     */
    private void initLibrosListeners() {
        agregarLibroButton.addActionListener(e -> {
            VentAgreLibro dialogo = new VentAgreLibro(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        quitarLibroButton.addActionListener(e -> {
            VentQuitarLibro dialogo = new VentQuitarLibro(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        listarLibrosConEstadoButton.addActionListener(e -> {
            if (miBiblioteca.getLibros().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay libros registrados en la biblioteca.", "Listado Vacío", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String listadoDeLibros = miBiblioteca.listaDeLibros();
                VentListSoci dialogo = new VentListSoci(this, "Listado de Libros (con Estado)", listadoDeLibros);
                dialogo.setVisible(true);
            }
        });

        listarTitulosButton.addActionListener(e -> {
            String listadoDeTitulos = miBiblioteca.listaDeTitulos();
            VentListSoci dialogo = new VentListSoci(this, "Listado de Títulos Únicos", listadoDeTitulos);
            dialogo.setVisible(true);
        });
    }

    /**
     * Asigna los listeners para los botones del panel "Préstamos".
     */
    private void initPrestamosListeners() {
        realizarPrestamoButton.addActionListener(e -> {
            VentRealizarPrestamo dialogo = new VentRealizarPrestamo(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        devolverLibroButton.addActionListener(e -> {
            VentDevolverLibro dialogo = new VentDevolverLibro(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        verificarHabilitacionSocioButton.addActionListener(e -> {
            VentVerificarSocio dialogo = new VentVerificarSocio(this, miBiblioteca);
            dialogo.setVisible(true);
        });
    }

    /**
     * Asigna los listeners para los botones del panel "Consultas".
     */
    private void initConsultasListeners() {
        listarPrestamosVencidosButton.addActionListener(e -> {
            ArrayList<Prestamo> vencidos = miBiblioteca.prestamosVencidos();
            if (vencidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "¡No hay préstamos vencidos al día de hoy!", "Préstamos al Día", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("--- Préstamos Vencidos (al día de hoy) ---\n\n");
                for (int i = 0; i < vencidos.size(); i++) {
                    Prestamo p = vencidos.get(i);
                    Socio s = p.getSocio();
                    Libro l = p.getLibro();
                    sb.append("N°: ").append(i + 1).append("\n");
                    sb.append("  Socio: ").append(s.getNombre()).append(" (DNI: ").append(s.getDniSocio()).append(")\n");
                    sb.append("  Libro: ").append(l.getTitulo()).append("\n");
                    Calendar fecha = p.getFechaRetiro();
                    String fechaStr = fecha.get(Calendar.DAY_OF_MONTH) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR);
                    sb.append("  Fecha Retiro: ").append(fechaStr).append("\n");
                    sb.append("  Días Límite: ").append(s.getDiasPrestamo()).append(" días\n\n");
                }
                VentListSoci dialogo = new VentListSoci(this, "Listado de Préstamos Vencidos", sb.toString());
                dialogo.setVisible(true);
            }
        });

        listarDocentesResponsablesButton.addActionListener(e -> {
            String listado = miBiblioteca.listaDeDocentesResponsables();
            if (listado.isBlank()) {
                JOptionPane.showMessageDialog(this, "No hay docentes responsables registrados.", "Listado Vacío", JOptionPane.INFORMATION_MESSAGE);
            } else {
                VentListSoci dialogo = new VentListSoci(this, "Listado de Docentes Responsables", listado);
                dialogo.setVisible(true);
            }
        });

        cantidadDeSociosPorButton.addActionListener(e -> {
            int estudiantes = miBiblioteca.cantidadDeSociosPorTipo("Estudiante");
            int docentes = miBiblioteca.cantidadDeSociosPorTipo("Docente");
            int total = estudiantes + docentes;
            String mensaje = "Conteo actual de socios en la biblioteca:\n\n"
                    + "Socios tipo Estudiante: " + estudiantes + "\n"
                    + "Socios tipo Docente:    " + docentes + "\n"
                    + "------------------------------------\n"
                    + "Total de Socios: " + total;
            JOptionPane.showMessageDialog(this, mensaje, "Cantidad de Socios por Tipo", JOptionPane.INFORMATION_MESSAGE);
        });

        buscarLibroEspecificoButton.addActionListener(e -> {
            VentBuscarLibro dialogo = new VentBuscarLibro(this, miBiblioteca);
            dialogo.setVisible(true);
        });
    }

    // --- 3. CONFIGURACIÓN DEL FRAME ---

    /**
     * Configura las propiedades finales del JFrame y lo hace visible.
     */
    private void initFrame() {
        setTitle("Gestion de Biblioteca | " + miBiblioteca.getNombre());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar
        setMinimumSize(new Dimension(1280, 720)); // Tamaño mínimo

        // Aseguramos que el panel principal sea el primero en mostrarse
        mostrarPanel(PANEL_PRINCIPAL);

        // Hacer visible el JFrame (¡El último paso!)
        setVisible(true);
    }

    // --- MÉTODOS AYUDANTES (Helpers) ---

    /**
     * Crea un panel de sección estándar (como Socios, Libros, etc.)
     *
     * @param titulo    El JLabel del título para el panel.
     * @param btnVolver El JButton para volver al menú principal.
     * @param botones   (Varargs) El resto de botones de función para esta sección.
     * @return Un JPanel construido y listo para ser agregado al CardLayout.
     */
    private JPanel crearPanelDeSeccion(JLabel titulo, JButton btnVolver, JButton... botones) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título en la parte superior (NORTE)
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de botones en el centro (CENTRO)
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS)); // Layout vertical

        Dimension btnSize = new Dimension(216, 36); // Tamaño estándar
        for (JButton btn : botones) {
            btn.setPreferredSize(btnSize);
            btn.setMaximumSize(btnSize); // Para BoxLayout
            btn.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrados
            panelBotones.add(btn);
            panelBotones.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciador
        }

        // Usamos GridBagLayout para centrar el panel de botones verticalmente
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.add(panelBotones);
        panel.add(panelCentro, BorderLayout.CENTER);

        // Botón Volver en la parte inferior (SUR)
        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelVolver.add(btnVolver);
        panel.add(panelVolver, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Muestra un popup simple (JOptionPane) para funciones no implementadas.
     *
     * @param funcionalidad El nombre de la acción.
     */
    private void mostrarPlaceholder(String funcionalidad) {
        JOptionPane.showMessageDialog(this,
                "'" + funcionalidad + "' aún no implementado.",
                "En Desarrollo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Cambia el panel visible en el CardLayout del 'body'.
     *
     * @param panelNombre El nombre (String) del panel a mostrar (ej: "Principal")
     */
    private void mostrarPanel(String panelNombre) {
        cardLayout.show(body, panelNombre);
    }
}