import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Interfaz gráfica para el sistema de gestión de biblioteca.
 * Utiliza JFrame para implementar un menú completo de gestión.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class InterfazBiblioteca extends JFrame {

    private final Biblioteca miBiblioteca;
    private JPanel panelPrincipal;

    public InterfaceBiblioteca() {
        miBiblioteca = new Biblioteca("Biblioteca Central UNL");
        inicializarDatos();
        configurarVentana();
        mostrarMenuPrincipal();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión - " + miBiblioteca.getNombre());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
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
        JButton btnSocios = crearBoton("Gestión de Socios");
        btnSocios.addActionListener(e -> menuGestionSocios());
        panelPrincipal.add(btnSocios, gbc);

        gbc.gridy = 2;
        JButton btnLibros = crearBoton("Gestión de Libros");
        btnLibros.addActionListener(e -> menuGestionLibros());
        panelPrincipal.add(btnLibros, gbc);

        gbc.gridy = 3;
        JButton btnPrestamos = crearBoton("Préstamos y Devoluciones");
        btnPrestamos.addActionListener(e -> menuGestionPrestamos());
        panelPrincipal.add(btnPrestamos, gbc);

        gbc.gridy = 4;
        JButton btnConsultas = crearBoton("Consultas e Informes");
        btnConsultas.addActionListener(e -> menuConsultas());
        panelPrincipal.add(btnConsultas, gbc);

        gbc.gridy = 5;
        JButton btnSalir = crearBoton("Salir");
        btnSalir.setBackground(new Color(220, 53, 69));
        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panelPrincipal.add(btnSalir, gbc);

        setContentPane(panelPrincipal);
        setVisible(true);
    }

    private void menuGestionSocios() {
        String[] opciones = {
            "Agregar Estudiante",
            "Agregar Docente",
            "Quitar Socio",
            "Listar Socios",
            "Cambiar Días de Préstamo (Docente)",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(this,
            "Gestión de Socios - Seleccione una opción:",
            "Gestión de Socios",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> agregarSocioEstudiante();
            case 1 -> agregarSocioDocente();
            case 2 -> quitarSocio();
            case 3 -> listarSocios();
            case 4 -> cambiarDiasDocente();
        }
    }

    private void menuGestionLibros() {
        String[] opciones = {
            "Agregar Libro",
            "Quitar Libro",
            "Listar Libros (con estado)",
            "Listar Títulos Únicos",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(this,
            "Gestión de Libros - Seleccione una opción:",
            "Gestión de Libros",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> agregarLibro();
            case 1 -> quitarLibro();
            case 2 -> listarLibros();
            case 3 -> listarTitulos();
        }
    }

    private void menuGestionPrestamos() {
        String[] opciones = {
            "Realizar Préstamo",
            "Devolver Libro",
            "Verificar Habilitación de Socio",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(this,
            "Préstamos y Devoluciones - Seleccione una opción:",
            "Préstamos y Devoluciones",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> realizarPrestamo();
            case 1 -> devolverLibro();
            case 2 -> verificarHabilitacionSocio();
        }
    }

    private void menuConsultas() {
        String[] opciones = {
            "Listar Préstamos Vencidos",
            "Listar Docentes Responsables",
            "Cantidad de Socios por Tipo",
            "¿Quién tiene un libro específico?",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(this,
            "Consultas e Informes - Seleccione una opción:",
            "Consultas e Informes",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        switch (seleccion) {
            case 0 -> listarPrestamosVencidos();
            case 1 -> listarDocentesResponsables();
            case 2 -> contarSociosPorTipo();
            case 3 -> quienTieneLibro();
        }
    }

    // Métodos de Gestión de Socios

    private void agregarSocioEstudiante() {
        try {
            String dniStr = JOptionPane.showInputDialog(this, "DNI del Estudiante:");
            if (dniStr == null) return;
            int dni = Integer.parseInt(dniStr);

            String nombre = JOptionPane.showInputDialog(this, "Nombre del Estudiante:");
            if (nombre == null) return;

            String carrera = JOptionPane.showInputDialog(this, "Carrera:");
            if (carrera == null) return;

            miBiblioteca.nuevoSocioEstudiante(dni, nombre, carrera);
            JOptionPane.showMessageDialog(this, "Estudiante agregado exitosamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarSocioDocente() {
        try {
            String dniStr = JOptionPane.showInputDialog(this, "DNI del Docente:");
            if (dniStr == null) return;
            int dni = Integer.parseInt(dniStr);

            String nombre = JOptionPane.showInputDialog(this, "Nombre del Docente:");
            if (nombre == null) return;

            String area = JOptionPane.showInputDialog(this, "Área de Especialización:");
            if (area == null) return;

            miBiblioteca.nuevoSocioDocente(dni, nombre, area);
            JOptionPane.showMessageDialog(this, "Docente agregado exitosamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quitarSocio() {
        try {
            String dniStr = JOptionPane.showInputDialog(this, "DNI del socio a quitar:");
            if (dniStr == null) return;
            int dni = Integer.parseInt(dniStr);

            Socio socio = miBiblioteca.buscarSocio(dni);
            if (socio != null) {
                miBiblioteca.quitarSocio(socio);
                JOptionPane.showMessageDialog(this, "Socio eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarSocios() {
        String lista = miBiblioteca.listaDeSocios();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Lista de Socios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarDiasDocente() {
        try {
            String dniStr = JOptionPane.showInputDialog(this, "DNI del Docente a modificar:");
            if (dniStr == null) return;
            int dni = Integer.parseInt(dniStr);

            Socio socio = miBiblioteca.buscarSocio(dni);

            if (socio instanceof Docente docente) {
                if (docente.esResponsable()) {
                    String diasStr = JOptionPane.showInputDialog(this, "Días a sumar/restar (ej: 3, -2):");
                    if (diasStr == null) return;
                    int dias = Integer.parseInt(diasStr);

                    docente.cambiarDiasDePrestamo(dias);
                    JOptionPane.showMessageDialog(this,
                        "Días de préstamo cambiados.\nNuevo límite: " + docente.getDiasPrestamo() + " días");
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

    // Métodos de Gestión de Libros

    private void agregarLibro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título:");
            if (titulo == null) return;

            String edicionStr = JOptionPane.showInputDialog(this, "Edición:");
            if (edicionStr == null) return;
            int edicion = Integer.parseInt(edicionStr);

            String editorial = JOptionPane.showInputDialog(this, "Editorial:");
            if (editorial == null) return;

            String anioStr = JOptionPane.showInputDialog(this, "Año de publicación:");
            if (anioStr == null) return;
            int anio = Integer.parseInt(anioStr);

            miBiblioteca.nuevoLibro(titulo, edicion, editorial, anio);
            JOptionPane.showMessageDialog(this, "Libro agregado exitosamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quitarLibro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título del libro a quitar:");
            if (titulo == null) return;

            String edicionStr = JOptionPane.showInputDialog(this, "Edición del libro a quitar:");
            if (edicionStr == null) return;
            int edicion = Integer.parseInt(edicionStr);

            String editorial = JOptionPane.showInputDialog(this, "Editorial del libro a quitar:");
            if (editorial == null) return;

            String anioStr = JOptionPane.showInputDialog(this, "Año de publicación del libro a quitar:");
            if (anioStr == null) return;
            int anio = Integer.parseInt(anioStr);

            Libro libroAEliminar = new Libro(titulo, edicion, editorial, anio, new ArrayList<>());
            miBiblioteca.quitarLibro(libroAEliminar);
            JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarLibros() {
        String lista = miBiblioteca.listaDeLibros();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Lista de Libros", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarTitulos() {
        String lista = miBiblioteca.listaDeTitulos();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Lista de Títulos Únicos", JOptionPane.INFORMATION_MESSAGE);
    }

    // Métodos de Gestión de Préstamos

    private void realizarPrestamo() {
        try {
            String dniStr = JOptionPane.showInputDialog(this, "DNI del Socio:");
            if (dniStr == null) return;
            int dni = Integer.parseInt(dniStr);

            String titulo = JOptionPane.showInputDialog(this, "Título del Libro a prestar:");
            if (titulo == null) return;

            String edicionStr = JOptionPane.showInputDialog(this, "Edición del Libro:");
            if (edicionStr == null) return;
            int edicion = Integer.parseInt(edicionStr);

            String editorial = JOptionPane.showInputDialog(this, "Editorial del Libro:");
            if (editorial == null) return;

            String anioStr = JOptionPane.showInputDialog(this, "Año del Libro:");
            if (anioStr == null) return;
            int anio = Integer.parseInt(anioStr);

            Socio socio = miBiblioteca.buscarSocio(dni);
            Libro libroAPrestar = buscarLibroEnLista(titulo, edicion, editorial, anio);

            if (socio == null) {
                JOptionPane.showMessageDialog(this, "Socio con DNI " + dni + " no encontrado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (libroAPrestar == null) {
                JOptionPane.showMessageDialog(this, "Libro no encontrado en la biblioteca.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Calendar fechaRetiro = new GregorianCalendar();

            if (miBiblioteca.prestarLibro(fechaRetiro, socio, libroAPrestar)) {
                JOptionPane.showMessageDialog(this,
                    "Préstamo realizado con éxito.\n" +
                    "Socio: " + socio.getNombre() + "\n" +
                    "Libro: " + libroAPrestar.getTitulo() + "\n" +
                    "Días límite de préstamo: " + socio.getDiasPrestamo() + " días");
            } else {
                String mensaje = "Préstamo NO realizado.\n";
                if (!socio.puedePedir()) {
                    mensaje += "Razón: El socio no está habilitado para pedir.\n";
                }
                if (libroAPrestar.prestado()) {
                    mensaje += "Razón: El libro ya está prestado.";
                }
                JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolverLibro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título del Libro a devolver:");
            if (titulo == null) return;

            String edicionStr = JOptionPane.showInputDialog(this, "Edición del Libro:");
            if (edicionStr == null) return;
            int edicion = Integer.parseInt(edicionStr);

            String editorial = JOptionPane.showInputDialog(this, "Editorial del Libro:");
            if (editorial == null) return;

            String anioStr = JOptionPane.showInputDialog(this, "Año del Libro:");
            if (anioStr == null) return;
            int anio = Integer.parseInt(anioStr);

            Libro libroADevolver = buscarLibroEnLista(titulo, edicion, editorial, anio);

            if (libroADevolver == null) {
                JOptionPane.showMessageDialog(this, "Libro no encontrado en la biblioteca.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                miBiblioteca.devolverLibro(libroADevolver);
                JOptionPane.showMessageDialog(this,
                    "Devolución de \"" + libroADevolver.getTitulo() + "\" registrada con éxito.");
            } catch (LibroNoPrestadoException e) {
                JOptionPane.showMessageDialog(this, "Error en la devolución: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarHabilitacionSocio() {
        try {
            String dniStr = JOptionPane.showInputDialog(this, "DNI del Socio a verificar:");
            if (dniStr == null) return;
            int dni = Integer.parseInt(dniStr);

            Socio socio = miBiblioteca.buscarSocio(dni);

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

    // Métodos de Consultas

    private void listarPrestamosVencidos() {
        ArrayList<Prestamo> vencidos = miBiblioteca.prestamosVencidos();

        if (vencidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No hay préstamos vencidos!",
                "Préstamos Vencidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Préstamos Vencidos (al día de hoy):\n\n");
        for (int i = 0; i < vencidos.size(); i++) {
            Prestamo p = vencidos.get(i);
            sb.append((i + 1)).append(". ").append(p.getLibro().getTitulo())
              .append(" | Socio: ").append(p.getSocio().getNombre())
              .append(" | Retiro: ").append(formatoFecha(p.getFechaRetiro()))
              .append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Préstamos Vencidos",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarDocentesResponsables() {
        String lista = miBiblioteca.listaDeDocentesResponsables();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Docentes Responsables",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void contarSociosPorTipo() {
        int estudiantes = miBiblioteca.cantidadDeSociosPorTipo("Estudiante");
        int docentes = miBiblioteca.cantidadDeSociosPorTipo("Docente");

        String mensaje = "Cantidad de Socios por Tipo:\n\n" +
                        "Estudiantes: " + estudiantes + "\n" +
                        "Docentes: " + docentes;

        JOptionPane.showMessageDialog(this, mensaje, "Cantidad de Socios por Tipo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void quienTieneLibro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título del Libro a consultar:");
            if (titulo == null) return;

            String edicionStr = JOptionPane.showInputDialog(this, "Edición del Libro:");
            if (edicionStr == null) return;
            int edicion = Integer.parseInt(edicionStr);

            String editorial = JOptionPane.showInputDialog(this, "Editorial del Libro:");
            if (editorial == null) return;

            String anioStr = JOptionPane.showInputDialog(this, "Año del Libro:");
            if (anioStr == null) return;
            int anio = Integer.parseInt(anioStr);

            Libro libroBuscado = buscarLibroEnLista(titulo, edicion, editorial, anio);

            if (libroBuscado == null) {
                JOptionPane.showMessageDialog(this, "Libro no encontrado en la biblioteca.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String resultado = miBiblioteca.quienTieneElLibro(libroBuscado);
                String mensaje = "Resultado de la consulta:\n\n" + resultado;

                if (libroBuscado.prestado()) {
                    mensaje += "\n\nEl libro está en posesión de: " +
                             libroBuscado.ultimoPrestamo().getSocio().getNombre();
                }

                JOptionPane.showMessageDialog(this, mensaje, "¿Quién tiene el libro?",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (LibroNoPrestadoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos auxiliares

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(250, 40));
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(0, 123, 255));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 2));
        return boton;
    }

    private Libro buscarLibroEnLista(String titulo, int edicion, String editorial, int anio) {
        for (Libro libro : miBiblioteca.getLibros()) {
            if (libro.getTitulo().equalsIgnoreCase(titulo) &&
                libro.getEdicion() == edicion &&
                libro.getEditorial().equalsIgnoreCase(editorial) &&
                libro.getAnio() == anio) {
                return libro;
            }
        }
        return null;
    }

    private String formatoFecha(Calendar p_fecha) {
        if (p_fecha == null) return "N/A";
        return p_fecha.get(Calendar.DAY_OF_MONTH) + "/" +
               (p_fecha.get(Calendar.MONTH) + 1) + "/" +
               p_fecha.get(Calendar.YEAR);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceBiblioteca());
    }
}
