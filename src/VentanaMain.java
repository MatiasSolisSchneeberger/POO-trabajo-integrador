import javax.swing.*;
import javax.swing.border.Border; // Importar Border
import javax.swing.border.EmptyBorder; // Importar EmptyBorder
import javax.swing.border.LineBorder; // Importar LineBorder
import java.awt.*; // Importar AWT para Layouts, Color, Font, Dimension, Insets
import java.awt.event.ActionListener; // Importar ActionListener

/**
 * Esta clase crea la interfaz de usuario completa sin depender de un archivo .form.
 * El layout se ha recreado usando GridBagLayout.
 */
public class VentanaMain extends JFrame {

    // --- Variables de Componentes ---
    private JPanel main;
    private JButton gestionDeSociosButton;
    private JButton gestionDeLibrosButton;
    private JButton prestamosYDevolucionesButton;
    private JButton consultasEInformesButton;
    private JPanel principal;
    private JPanel header;
    private JButton agregarEstudiandteButton;
    private JPanel body;
    private JButton listarSociosButton;
    private JButton cambiarDiasDePrestamoButton;
    private JPanel libros;
    private JPanel prestamosDevoluciones;
    private JPanel consultasInformes;
    private JButton cantidadDeSociosPorButton;
    private JButton buscarLibroEspecificoButton;
    private JButton volverLibros;
    private JPanel socios;
    private JButton volverSocios;
    private JButton volverPrestamosDevoluciones;
    private JButton volverConsultasInformes;
    private JButton salir;

    // --- Componentes Adicionales ---
    private JLabel labelTituloHeader;
    private JLabel labelOpciones;
    private JLabel labelSocios;
    private JLabel labelLibros;
    private JLabel labelPrestamos;
    private JLabel labelConsultas;

    private JButton quitarSocioButton;
    private JButton agregarDocenteButton;
    private JButton agregarLibroButton;
    private JButton quitarLibroButton;
    private JButton listarLibrosEstadoButton;
    private JButton listarTitulosButton;
    private JButton realizarPrestamoButton;
    private JButton devolverLibroButton;
    private JButton verificarSocioButton;
    private JButton listarVencidosButton;
    private JButton listarDocentesButton;


    // --- Variable de Control ---
    private CardLayout cardLayout;

    // --- Constantes de Estilo ---
    private final Color COLOR_FONDO_MAIN = new Color(-3354155);
    private final Color COLOR_BORDE_PANELES = new Color(-5721672);
    private final Color COLOR_TEXTO_VOLVER_SALIR = new Color(-2279908);
    private final Color COLOR_SEPARADOR = new Color(-5721672);

    private final Font FONT_HEADER_TITULO = new Font("SansSerif", Font.BOLD, 24);
    private final Font FONT_PAGINA_TITULO = new Font("SansSerif", Font.BOLD, 16);
    private final Font FONT_BOTON_MENU = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Constructor que construye toda la UI desde cero.
     */
    VentanaMain() {
        // 1. Configurar el JFrame
        super("Gestion de Biblioteca"); // Pone el título
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));

        // 2. Construir todos los componentes
        initComponents();

        // 3. Asignar el panel 'main' como el panel de contenido
        setContentPane(main);

        // 4. Configurar los Action Listeners
        setupListeners();

        // 5. Mostrar la carta inicial
        cardLayout.show(body, "PRINCIPAL");

        // 6. Hacer visible el JFrame
        setVisible(true);
    }

    /**
     * Inicializa y ensambla todos los componentes de la UI.
     */
    private void initComponents() {

        // --- Panel 'main' ---
        main = new JPanel(new GridBagLayout());
        main.setBackground(COLOR_FONDO_MAIN);
        main.setBorder(new EmptyBorder(24, 24, 24, 24));
        main.setPreferredSize(new Dimension(1920, 1080));
        GridBagConstraints gbcMain = new GridBagConstraints();

        // --- Panel 'header' ---
        header = new JPanel(new GridBagLayout()); // GridBag para centrar fácil
        Border bordeHeader = BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE_PANELES),
                new EmptyBorder(12, 12, 12, 12)
        );
        header.setBorder(bordeHeader);

        labelTituloHeader = new JLabel("Gestor de Biblioteca");
        labelTituloHeader.setFont(FONT_HEADER_TITULO);
        header.add(labelTituloHeader, new GridBagConstraints()); // Centrado por defecto

        // --- Panel 'body' ---
        cardLayout = new CardLayout(0, 0);
        body = new JPanel(cardLayout);

        // --- Crear y añadir las "Cartas" (Páginas) ---
        principal = createPrincipalPanel();
        socios = createSociosPanel();
        libros = createLibrosPanel();
        prestamosDevoluciones = createPrestamosPanel();
        consultasInformes = createConsultasPanel();

        body.add(principal, "PRINCIPAL");
        body.add(socios, "SOCIOS");
        body.add(libros, "LIBROS");
        body.add(prestamosDevoluciones, "PRESTAMOS");
        body.add(consultasInformes, "CONSULTAS");

        // --- Ensamblar 'main' ---
        // Añadir 'header' a 'main'
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        gbcMain.anchor = GridBagConstraints.NORTH;
        gbcMain.weightx = 1.0;
        gbcMain.weighty = 0.0;
        gbcMain.insets = new Insets(0, 0, 12, 0); // vgap="12"
        main.add(header, gbcMain);

        // Añadir 'body' a 'main'
        gbcMain.gridy = 1;
        gbcMain.fill = GridBagConstraints.BOTH; // fill="3"
        gbcMain.weighty = 1.0; // Ocupa el resto del espacio
        gbcMain.insets = new Insets(0, 0, 0, 0);
        main.add(body, gbcMain);
    }

    /**
     * Crea el panel del Menú Principal ("principal")
     */
    private JPanel createPrincipalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE_PANELES),
                new EmptyBorder(12, 12, 12, 12)
        ));

        // GridBagConstraints para el contenido (columna 1)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 5, 0); // Espacio entre botones

        // Añadir espaciador izquierdo (columna 0)
        panel.add(createSpacer(true), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        // --- Contenido (Columna 1) ---
        labelOpciones = new JLabel("Opciones");
        labelOpciones.setFont(FONT_PAGINA_TITULO);
        labelOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelOpciones, gbc);

        gbc.gridy++;
        gestionDeSociosButton = new JButton("Gestion de Socios");
        configureButton(gestionDeSociosButton, FONT_BOTON_MENU);
        panel.add(gestionDeSociosButton, gbc);

        gbc.gridy++;
        gestionDeLibrosButton = new JButton("Gestion de Libros");
        configureButton(gestionDeLibrosButton, FONT_BOTON_MENU);
        panel.add(gestionDeLibrosButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        prestamosYDevolucionesButton = new JButton("Prestamos y Devoluciones");
        configureButton(prestamosYDevolucionesButton, FONT_BOTON_MENU);
        panel.add(prestamosYDevolucionesButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        consultasEInformesButton = new JButton("Consultas e Informes");
        configureButton(consultasEInformesButton, FONT_BOTON_MENU);
        panel.add(consultasEInformesButton, gbc);

        gbc.gridy++; // Espaciador vertical
        panel.add(createSpacer(false), new GridBagConstraints(1, gbc.gridy, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

        gbc.gridy++; // Botón Salir
        gbc.anchor = GridBagConstraints.SOUTH;
        salir = new JButton("Salir");
        configureButton(salir, FONT_BOTON_MENU);
        salir.setForeground(COLOR_TEXTO_VOLVER_SALIR);
        panel.add(salir, gbc);

        // Añadir espaciador derecho (columna 2)
        panel.add(createSpacer(true), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        return panel;
    }

    /**
     * Crea el panel de Gestión de Socios ("socios")
     */
    private JPanel createSociosPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 5, 0);

        panel.add(createSpacer(true), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        // --- Contenido (Columna 1) ---
        labelSocios = new JLabel("Socios");
        labelSocios.setFont(FONT_PAGINA_TITULO);
        labelSocios.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelSocios, gbc);

        gbc.gridy++;
        agregarEstudiandteButton = new JButton("Agregar Estudiandte");
        panel.add(agregarEstudiandteButton, gbc);

        gbc.gridy++;
        agregarDocenteButton = new JButton("Agregar Docente");
        panel.add(agregarDocenteButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        listarSociosButton = new JButton("Listar Socios");
        panel.add(listarSociosButton, gbc);

        gbc.gridy++;
        quitarSocioButton = new JButton("Quitar Socio");
        panel.add(quitarSocioButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        cambiarDiasDePrestamoButton = new JButton("Cambiar Dias de Prestamo (Docente)");
        panel.add(cambiarDiasDePrestamoButton, gbc);

        gbc.gridy++; // Espaciador vertical
        panel.add(createSpacer(false), new GridBagConstraints(1, gbc.gridy, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

        gbc.gridy++; // Botón Volver
        gbc.anchor = GridBagConstraints.SOUTH;
        volverSocios = new JButton("Voler");
        configureButton(volverSocios, FONT_BOTON_MENU);
        volverSocios.setForeground(COLOR_TEXTO_VOLVER_SALIR);
        panel.add(volverSocios, gbc);

        panel.add(createSpacer(true), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        return panel;
    }

    /**
     * Crea el panel de Gestión de Libros ("libros")
     */
    private JPanel createLibrosPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE_PANELES),
                new EmptyBorder(12, 12, 12, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 5, 0);

        panel.add(createSpacer(true), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        // --- Contenido (Columna 1) ---
        labelLibros = new JLabel("Libros");
        labelLibros.setFont(FONT_PAGINA_TITULO);
        labelLibros.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelLibros, gbc);

        gbc.gridy++;
        agregarLibroButton = new JButton("Agregar Libro");
        panel.add(agregarLibroButton, gbc);

        gbc.gridy++;
        quitarLibroButton = new JButton("Quitar Libro");
        panel.add(quitarLibroButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        listarLibrosEstadoButton = new JButton("Listar Libros (con estado)");
        panel.add(listarLibrosEstadoButton, gbc);

        gbc.gridy++;
        listarTitulosButton = new JButton("Listar Titulos");
        panel.add(listarTitulosButton, gbc);

        gbc.gridy++; // Espaciador vertical
        panel.add(createSpacer(false), new GridBagConstraints(1, gbc.gridy, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

        gbc.gridy++; // Botón Volver
        gbc.anchor = GridBagConstraints.SOUTH;
        volverLibros = new JButton("Voler");
        configureButton(volverLibros, FONT_BOTON_MENU);
        volverLibros.setForeground(COLOR_TEXTO_VOLVER_SALIR);
        panel.add(volverLibros, gbc);

        panel.add(createSpacer(true), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        return panel;
    }

    /**
     * Crea el panel de Préstamos y Devoluciones
     */
    private JPanel createPrestamosPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE_PANELES),
                new EmptyBorder(12, 12, 12, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 5, 0);

        panel.add(createSpacer(true), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        // --- Contenido (Columna 1) ---
        labelPrestamos = new JLabel("Gestionar Prestamos y Devoluciones");
        labelPrestamos.setFont(FONT_PAGINA_TITULO);
        labelPrestamos.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelPrestamos, gbc);

        gbc.gridy++;
        realizarPrestamoButton = new JButton("Realizar Prestamo");
        panel.add(realizarPrestamoButton, gbc);

        gbc.gridy++;
        devolverLibroButton = new JButton("Devolver Libro");
        panel.add(devolverLibroButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        verificarSocioButton = new JButton("Verificar Habilitacion Socio");
        panel.add(verificarSocioButton, gbc);

        gbc.gridy++; // Espaciador vertical
        panel.add(createSpacer(false), new GridBagConstraints(1, gbc.gridy, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

        gbc.gridy++; // Botón Volver
        gbc.anchor = GridBagConstraints.SOUTH;
        volverPrestamosDevoluciones = new JButton("Voler");
        configureButton(volverPrestamosDevoluciones, FONT_BOTON_MENU);
        volverPrestamosDevoluciones.setForeground(COLOR_TEXTO_VOLVER_SALIR);
        panel.add(volverPrestamosDevoluciones, gbc);

        panel.add(createSpacer(true), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        return panel;
    }

    /**
     * Crea el panel de Consultas e Informes
     */
    private JPanel createConsultasPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE_PANELES),
                new EmptyBorder(12, 12, 12, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 5, 0);

        panel.add(createSpacer(true), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        // --- Contenido (Columna 1) ---
        labelConsultas = new JLabel("Consultas e Informes");
        labelConsultas.setFont(FONT_PAGINA_TITULO);
        labelConsultas.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelConsultas, gbc);

        gbc.gridy++;
        listarVencidosButton = new JButton("Listar Prestamos Vencidos");
        panel.add(listarVencidosButton, gbc);

        gbc.gridy++;
        listarDocentesButton = new JButton("Listar Docentes Responsables");
        panel.add(listarDocentesButton, gbc);

        gbc.gridy++;
        cantidadDeSociosPorButton = new JButton("Cantidad de Socios por Tipo");
        panel.add(cantidadDeSociosPorButton, gbc);

        gbc.gridy++;
        panel.add(createSeparator(), gbc);

        gbc.gridy++;
        buscarLibroEspecificoButton = new JButton("Buscar Libro Especifico");
        panel.add(buscarLibroEspecificoButton, gbc);

        gbc.gridy++; // Espaciador vertical
        panel.add(createSpacer(false), new GridBagConstraints(1, gbc.gridy, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

        gbc.gridy++; // Botón Volver
        gbc.anchor = GridBagConstraints.SOUTH;
        volverConsultasInformes = new JButton("Voler");
        configureButton(volverConsultasInformes, FONT_BOTON_MENU);
        volverConsultasInformes.setForeground(COLOR_TEXTO_VOLVER_SALIR);
        panel.add(volverConsultasInformes, gbc);

        panel.add(createSpacer(true), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        return panel;
    }

    /**
     * Configura los Action Listeners para la navegación
     */
    private void setupListeners() {
        // --- Navegación del Menú Principal ---
        // Usamos lambdas para hacerlo más corto

        gestionDeSociosButton.addActionListener(e -> cardLayout.show(body, "SOCIOS"));
        gestionDeLibrosButton.addActionListener(e -> cardLayout.show(body, "LIBROS"));
        prestamosYDevolucionesButton.addActionListener(e -> cardLayout.show(body, "PRESTAMOS"));
        consultasEInformesButton.addActionListener(e -> cardLayout.show(body, "CONSULTAS"));

        // --- Botones de "Volver" ---
        ActionListener volverAction = e -> cardLayout.show(body, "PRINCIPAL");

        volverSocios.addActionListener(volverAction);
        volverLibros.addActionListener(volverAction);
        volverPrestamosDevoluciones.addActionListener(volverAction);
        volverConsultasInformes.addActionListener(volverAction);

        // --- Botón de Salir ---
        salir.addActionListener(e -> System.exit(0));
    }


    // --- MÉTODOS AYUDANTES ---

    /**
     * Crea un JSeparator con el color del .form
     */
    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setBackground(COLOR_SEPARADOR);
        separator.setForeground(COLOR_SEPARADOR);
        return separator;
    }

    /**
     * Crea un panel vacío para usar como espaciador (hspacer o vspacer)
     *
     * @param horizontal Si es true, es un hspacer (peso en x). Si es false, vspacer (peso en y).
     */
    private JPanel createSpacer(boolean horizontal) {
        JPanel spacer = new JPanel();
        spacer.setOpaque(false); // Lo hace invisible
        // El peso (weightx/weighty) se asigna al añadirlo con GridBagConstraints
        return spacer;
    }

    /**
     * Aplica las propiedades comunes de los botones
     */
    private void configureButton(JButton button, Font font) {
        if (font != null) {
            button.setFont(font);
        }
        button.setFocusPainted(false);
        button.setRequestFocusEnabled(false);

    }
}