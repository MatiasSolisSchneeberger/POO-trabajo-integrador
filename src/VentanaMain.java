import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class VentanaMain extends JFrame {
    private Biblioteca miBiblioteca;

    // --- Componentes de la Interfaz ---
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

    // El layout que controla la navegación
    private CardLayout cardLayout;

    // --- Constantes para el CardLayout ---
    // nombres para poder navegar
    private static final String PANEL_PRINCIPAL = "Principal";
    private static final String PANEL_SOCIOS = "Socios";
    private static final String PANEL_LIBROS = "Libros";
    private static final String PANEL_PRESTAMOS = "Prestamos";
    private static final String PANEL_CONSULTAS = "Consultas";

    // --- Constantes de colores ---
    private static final Color COLOR_ROJO = new Color(220, 53, 69);

    VentanaMain(Biblioteca p_biblioteca) {
        this.miBiblioteca = p_biblioteca;

        // --- INICIO DE LA CONSTRUCCIÓN DE LA UI (NUEVO) ---
        // Aquí creamos todos los componentes que antes hacía el .form

        // 1. Panel Principal (Raíz)
        main = new JPanel(new BorderLayout(12, 12)); // hgap/vgap
        main.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24)); // Padding
        main.setBackground(new Color(236, 239, 241)); // Un color gris claro

        // 2. Header
        header = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12)); // padding
        header.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189)));
        gestionDeBibliotecaLabel = new JLabel("Gestion De Biblioteca");
        gestionDeBibliotecaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(gestionDeBibliotecaLabel);

        // 3. Body (Contenedor de Paneles)
        cardLayout = new CardLayout();
        body = new JPanel(cardLayout);
        body.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189)));

        // 4. Crear cada panel ("Card")
        // --- Panel "Principal" ---
        principal = new JPanel(new GridBagLayout()); // Usamos GridBagLayout para centrar
        JPanel panelPrincipalBotones = new JPanel();
        panelPrincipalBotones.setLayout(new BoxLayout(panelPrincipalBotones, BoxLayout.Y_AXIS));

        opcionesLabel = new JLabel("Opciones");
        opcionesLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        opcionesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gestionDeSociosButton = new JButton("Gestion de Socios");
        gestionDeLibrosButton = new JButton("Gestion de Libros");
        prestamosYDevolucionesButton = new JButton("Prestamos y Devoluciones");
        consultasEInformesButton = new JButton("Consultas e Informes");
        salirButton = new JButton("Salir");
        salirButton.setForeground(COLOR_ROJO);

        // Dar el mismo tamaño a todos los botones del menú
        Dimension btnSize = new Dimension(250, 40);
        gestionDeSociosButton.setPreferredSize(btnSize);
        gestionDeLibrosButton.setPreferredSize(btnSize);
        prestamosYDevolucionesButton.setPreferredSize(btnSize);
        consultasEInformesButton.setPreferredSize(btnSize);
        salirButton.setPreferredSize(btnSize);

        panelPrincipalBotones.add(opcionesLabel);
        panelPrincipalBotones.add(Box.createRigidArea(new Dimension(0, 24))); // Espaciador
        panelPrincipalBotones.add(gestionDeSociosButton);
        panelPrincipalBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelPrincipalBotones.add(gestionDeLibrosButton);
        panelPrincipalBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelPrincipalBotones.add(prestamosYDevolucionesButton);
        panelPrincipalBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelPrincipalBotones.add(consultasEInformesButton);
        panelPrincipalBotones.add(Box.createRigidArea(new Dimension(0, 24)));
        panelPrincipalBotones.add(Box.createVerticalGlue()); // Espaciador flexible
        panelPrincipalBotones.add(salirButton);
        principal.add(panelPrincipalBotones); // Agrega el panel de botones al panel centrado

        // --- Panel "Socios" ---
        sociosLabel = new JLabel("Socios");
        agregarEstudiandteButton = new JButton("Agregar Estudiante");
        agregarDocenteButton = new JButton("Agregar Docente");
        quitarSocioButton = new JButton("Quitar Socio");
        listarSociosButton = new JButton("Listar Socios");
        cambiarDiasDePrestamoButton = new JButton("Cambiar Dias de Prestamo (Docente)");
        volverSociosButton = new JButton("Volver");
        volverSociosButton.setForeground(COLOR_ROJO);
        socios = crearPanelDeSeccion(sociosLabel, volverSociosButton,
                agregarEstudiandteButton, agregarDocenteButton, quitarSocioButton,
                listarSociosButton, cambiarDiasDePrestamoButton);

        // --- Panel "Libros" ---
        librosLabel = new JLabel("Libros");
        agregarLibroButton = new JButton("Agregar Libro");
        quitarLibroButton = new JButton("Quitar Libro");
        listarLibrosConEstadoButton = new JButton("Listar Libros (con estado)");
        listarTitulosButton = new JButton("Listar Titulos");
        volverLibrosButton = new JButton("Volver");
        volverLibrosButton.setForeground(COLOR_ROJO);
        libros = crearPanelDeSeccion(librosLabel, volverLibrosButton,
                agregarLibroButton, quitarLibroButton,
                listarLibrosConEstadoButton, listarTitulosButton);


        // --- Panel "Préstamos" ---
        gestionarPresYDevoLabel = new JLabel("Gestionar Prestamos y Devoluciones");
        realizarPrestamoButton = new JButton("Realizar Prestamo");
        devolverLibroButton = new JButton("Devolver Libro");
        verificarHabilitacionSocioButton = new JButton("Verificar Habilitacion Socio");
        volverPresDevoButton = new JButton("Volver");
        volverPresDevoButton.setForeground(COLOR_ROJO);
        prestamosDevoluciones = crearPanelDeSeccion(gestionarPresYDevoLabel, volverPresDevoButton,
                realizarPrestamoButton, devolverLibroButton, verificarHabilitacionSocioButton);


        // --- Panel "Consultas" ---
        consultasEInformesLabel = new JLabel("Consultas e Informes");
        listarPrestamosVencidosButton = new JButton("Listar Prestamos Vencidos");
        listarDocentesResponsablesButton = new JButton("Listar Docentes Responsables");
        cantidadDeSociosPorButton = new JButton("Cantidad de Socios por Tipo");
        buscarLibroEspecificoButton = new JButton("Buscar Libro Especifico");
        volverConsInfoButton = new JButton("Volver");
        volverConsInfoButton.setForeground(COLOR_ROJO);
        consultasInformes = crearPanelDeSeccion(consultasEInformesLabel, volverConsInfoButton,
                listarPrestamosVencidosButton, listarDocentesResponsablesButton,
                cantidadDeSociosPorButton, buscarLibroEspecificoButton);

        // 5. Agregar los paneles ("Cards") al 'body'
        body.add(principal, PANEL_PRINCIPAL);
        body.add(socios, PANEL_SOCIOS);
        body.add(libros, PANEL_LIBROS);
        body.add(prestamosDevoluciones, PANEL_PRESTAMOS);
        body.add(consultasInformes, PANEL_CONSULTAS);

        // 6. Ensamblar la ventana principal
        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);


        // Esta línea ahora funciona, porque 'main' ya no es null
        setContentPane(main);

        // --- Inicio de la Lógica (Tu código existente) ---

        setTitle("Gestion de Biblioteca | " + p_biblioteca.getNombre());
        //setDefaultCloseOperation(EXIT_ON_CLOSE); estoy forzado a implementar otra función más debajo para la implementación de la persistencia de datos
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //cambio necesario para que se pueda interceptar el cierre de la aplicación
        addWindowListener(new java.awt.event.WindowAdapter(){
        /**
         * Este método es llamado cuando el usuario intenta cerrar la ventana.
         * Su tipo de retorno es **void**, por eso no te pedirá un 'return'.
         */
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            // A. Guardamos el estado actual de la Biblioteca
            GestorPersistencia.guardar(miBiblioteca);

            // B. Terminamos la aplicación de manera segura
            System.exit(0);
        }
    });
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));

        // Titulo (ya se hizo en el header, pero lo re-aseguramos)
        if (miBiblioteca.getNombre() != null) {
            gestionDeBibliotecaLabel.setText("Gestion De Biblioteca | " + miBiblioteca.getNombre());
        }

        // --- A. Botones de Navegación Principal ---
        gestionDeSociosButton.addActionListener(e -> mostrarPanel(PANEL_SOCIOS));
        gestionDeLibrosButton.addActionListener(e -> mostrarPanel(PANEL_LIBROS));
        prestamosYDevolucionesButton.addActionListener(e -> mostrarPanel(PANEL_PRESTAMOS));
        consultasEInformesButton.addActionListener(e -> mostrarPanel(PANEL_CONSULTAS));
        //salirButton.addActionListener(e -> System.exit(0));
        
        //salirButton con lógica necesaria para el funcionamiento de la persistencia de Archivos.
        salirButton.addActionListener(e -> {
        GestorPersistencia.guardar(miBiblioteca); // Guarda antes de salir
        System.exit(0);                          // Cierra la aplicación
        });

        // --- B. Botones de "Volver" ---
        volverSociosButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverLibrosButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverPresDevoButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));
        volverConsInfoButton.addActionListener(e -> mostrarPanel(PANEL_PRINCIPAL));

        // --- C. Botones de Funcionalidad ---

        // --- Panel Socios ---
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
            VentListSoci dialogo = new VentListSoci(
                    this,
                    "Listado de Socios",
                    listadoDeSocios
            );
            dialogo.setVisible(true);
        });
        quitarSocioButton.addActionListener(e -> {
            VentQuitarSocio dialogo = new VentQuitarSocio(this, miBiblioteca);
            dialogo.setVisible(true);
        });
        cambiarDiasDePrestamoButton.addActionListener(e -> mostrarPlaceholder("Cambiar Días Préstamo Docente"));


        // ---- Panel Libros ----
        agregarLibroButton.addActionListener(e -> {
            VentAgreLibro dialogo = new VentAgreLibro(this, miBiblioteca);
            dialogo.setVisible(true);
        });

        quitarLibroButton.addActionListener(e -> {
            // 1. Creamos la nueva ventana de diálogo para Quitar Libro
            VentQuitarLibro dialogo = new VentQuitarLibro(this, miBiblioteca);
            // 2. La hacemos visible (y modal)
            dialogo.setVisible(true);
        });

        listarLibrosConEstadoButton.addActionListener(e -> {
            // 1. Verificamos primero si hay libros, para cumplir con "que avise si no hay"
            //    Accedemos directamente a la lista de la biblioteca para la comprobación.
            if (miBiblioteca.getLibros().isEmpty()) {

                // Si no hay libros, mostramos un popup simple
                JOptionPane.showMessageDialog(this, // 'this' es la VentanaMain
                        "No hay libros registrados en la biblioteca.", // Mensaje
                        "Listado Vacío", // Título del popup
                        JOptionPane.INFORMATION_MESSAGE); // Ícono
            } else {

                // 2. Si hay libros, obtenemos el String (que ya incluye el estado)
                String listadoDeLibros = miBiblioteca.listaDeLibros();

                // 3. Reutilizamos nuestro diálogo genérico VentListSoci
                //    (Asumo que tu VentListarSocios se llama VentListSoci)
                VentListSoci dialogo = new VentListSoci(
                        this, // El owner (VentanaMain)
                        "Listado de Libros (con Estado)", // El título de la ventana
                        listadoDeLibros                   // El String que mostrará
                );

                // 4. Mostramos la ventana
                dialogo.setVisible(true);
            }
        });
        // muy corto para hacerlo en un archivo aparte
        listarTitulosButton.addActionListener(e -> {
            // 1. Obtenemos el String formateado de la lógica de negocio
            String listadoDeTitulos = miBiblioteca.listaDeTitulos();

            // 2. Creamos nuestro diálogo genérico, pasándole el título y el contenido
            //    (Estamos reutilizando la misma clase VentListSoci)
            VentListSoci dialogo = new VentListSoci(
                    this, // El owner (VentanaMain)
                    "Listado de Títulos Únicos", // El título de la ventana
                    listadoDeTitulos             // El String que mostrará
            );

            // 3. Mostramos la ventana
            dialogo.setVisible(true);
        });

        // ---- Panel Préstamos ----
        realizarPrestamoButton.addActionListener(e -> {
            // 1. Creamos la nueva ventana de diálogo para Realizar Préstamo
            VentRealizarPrestamo dialogo = new VentRealizarPrestamo(this, miBiblioteca);
            // 2. La hacemos visible (y modal)
            dialogo.setVisible(true);
        });

        devolverLibroButton.addActionListener(e -> {
            // 1. Creamos la nueva ventana de diálogo para Devolver Libro
            VentDevolverLibro dialogo = new VentDevolverLibro(this, miBiblioteca);
            // 2. La hacemos visible (y modal)
            dialogo.setVisible(true);
        });
        verificarHabilitacionSocioButton.addActionListener(e -> {
            // 1. Creamos la nueva ventana de diálogo
            VentVerificarSocio dialogo = new VentVerificarSocio(this, miBiblioteca);
            // 2. La hacemos visible (y modal)
            dialogo.setVisible(true);
        });


        // ---- Panel Consultas ----
        listarPrestamosVencidosButton.addActionListener(e -> {
            // 1. Obtenemos la lista de préstamos vencidos
            ArrayList<Prestamo> vencidos = miBiblioteca.prestamosVencidos();

            // 2. Verificamos si la lista está vacía
            if (vencidos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "¡No hay préstamos vencidos al día de hoy!",
                        "Préstamos al Día",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // 3. Formateamos la lista en un String legible
                // Usamos StringBuilder para construir el String eficientemente
                StringBuilder sb = new StringBuilder();
                sb.append("--- Préstamos Vencidos (al día de hoy) ---\n\n");

                for (int i = 0; i < vencidos.size(); i++) {
                    Prestamo p = vencidos.get(i);
                    Socio s = p.getSocio();
                    Libro l = p.getLibro();

                    sb.append("N°: ").append(i + 1).append("\n");
                    sb.append("  Socio: ").append(s.getNombre()).append(" (DNI: ").append(s.getDniSocio()).append(")\n");
                    sb.append("  Libro: ").append(l.getTitulo()).append("\n");

                    // Formateamos la fecha (Calendar a String)
                    Calendar fecha = p.getFechaRetiro();
                    String fechaStr = fecha.get(Calendar.DAY_OF_MONTH) + "/" +
                            (fecha.get(Calendar.MONTH) + 1) + "/" + // +1 porque los meses son 0-11
                            fecha.get(Calendar.YEAR);

                    sb.append("  Fecha Retiro: ").append(fechaStr).append("\n");
                    sb.append("  Días Límite: ").append(s.getDiasPrestamo()).append(" días\n\n");
                }

                // 4. Mostramos el String en nuestra ventana de listado genérica
                VentListSoci dialogo = new VentListSoci(
                        this,
                        "Listado de Préstamos Vencidos",
                        sb.toString() // El String que acabamos de construir
                );
                dialogo.setVisible(true);
            }
        });

        listarDocentesResponsablesButton.addActionListener(e -> {
            // 1. Obtenemos el String formateado de la lógica de negocio
            String listado = miBiblioteca.listaDeDocentesResponsables();

            // 2. Verificamos si está vacío
            if (listado.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "No hay docentes responsables registrados.",
                        "Listado Vacío",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // 3. Si no está vacío, lo mostramos en nuestra ventana de listado
                VentListSoci dialogo = new VentListSoci(
                        this,
                        "Listado de Docentes Responsables",
                        listado // El String que ya viene formateado
                );
                dialogo.setVisible(true);
            }
        });

        cantidadDeSociosPorButton.addActionListener(e -> {
            // 1. Obtenemos la cantidad para cada tipo de socio
            int estudiantes = miBiblioteca.cantidadDeSociosPorTipo("Estudiante");
            int docentes = miBiblioteca.cantidadDeSociosPorTipo("Docente");
            int total = estudiantes + docentes;

            // 2. Creamos el mensaje para mostrar
            String mensaje = "Conteo actual de socios en la biblioteca:\n\n"
                    + "Socios tipo Estudiante: " + estudiantes + "\n"
                    + "Socios tipo Docente:    " + docentes + "\n"
                    + "------------------------------------\n"
                    + "Total de Socios: " + total;

            // 3. Mostramos el resultado en un popup de información
            JOptionPane.showMessageDialog(this, // 'this' es la VentanaMain
                    mensaje, // El mensaje que acabamos de crear
                    "Cantidad de Socios por Tipo", // Título del popup
                    JOptionPane.INFORMATION_MESSAGE); // Ícono de información
        });

        buscarLibroEspecificoButton.addActionListener(e -> {
            // 1. Creamos la nueva ventana de diálogo
            VentBuscarLibro dialogo = new VentBuscarLibro(this, miBiblioteca);
            // 2. La hacemos visible (y modal)
            dialogo.setVisible(true);
        });
        // Aseguramos que el panel principal sea el primero en mostrarse
        mostrarPanel(PANEL_PRINCIPAL);

        // 6. Hacer visible el JFrame
        setVisible(true);
    }

    /**
     * Crea un panel de sección estándar (como Socios, Libros, etc.)
     *
     * @param titulo    El JLabel del título
     * @param btnVolver El JButton para volver
     * @param botones   El resto de botones de función para esta sección
     * @return Un JPanel construido
     */
    private JPanel crearPanelDeSeccion(JLabel titulo, JButton btnVolver, JButton... botones) {
        // Panel principal de la sección
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de botones (centrado)
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        Dimension btnSize = new Dimension(300, 40); // Tamaño estándar
        for (JButton btn : botones) {
            btn.setPreferredSize(btnSize);
            btn.setMaximumSize(btnSize); // Para BoxLayout
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelBotones.add(btn);
            panelBotones.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciador
        }

        // Panel contenedor para centrar el panel de botones
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.add(panelBotones);
        panel.add(panelCentro, BorderLayout.CENTER);

        // Botón Volver (abajo)
        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelVolver.add(btnVolver);
        panel.add(panelVolver, BorderLayout.SOUTH);

        return panel;
    }


    /**
     * Muestra un popup simple para funciones no implementadas.
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
     * @param panelNombre El nombre (String) del panel a mostrar
     */
    private void mostrarPanel(String panelNombre) {
        // Ahora usamos la variable de clase 'cardLayout'
        cardLayout.show(body, panelNombre);
    }
}
