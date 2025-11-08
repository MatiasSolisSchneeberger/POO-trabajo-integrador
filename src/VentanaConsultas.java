import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Ventana para consultas e informes de la biblioteca.
 *
 * @author Sistema
 * @version 08/11/2025
 */
public class VentanaConsultas {

    private Biblioteca biblioteca;
    private Ventana ventanaPrincipal;

    public VentanaConsultas(Biblioteca biblioteca, Ventana ventanaPrincipal) {
        this.biblioteca = biblioteca;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void mostrarMenu() {
        String[] opciones = {
            "Listar Préstamos Vencidos",
            "Listar Docentes Responsables",
            "Cantidad de Socios por Tipo",
            "¿Quién tiene un libro específico?",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(ventanaPrincipal,
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

    private void listarPrestamosVencidos() {
        ArrayList<Prestamo> vencidos = biblioteca.prestamosVencidos();

        if (vencidos.isEmpty()) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "¡No hay préstamos vencidos!",
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
        JOptionPane.showMessageDialog(ventanaPrincipal, scrollPane, "Préstamos Vencidos",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarDocentesResponsables() {
        String lista = biblioteca.listaDeDocentesResponsables();
        JTextArea textArea = new JTextArea(lista);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(ventanaPrincipal, scrollPane, "Docentes Responsables",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void contarSociosPorTipo() {
        int estudiantes = biblioteca.cantidadDeSociosPorTipo("Estudiante");
        int docentes = biblioteca.cantidadDeSociosPorTipo("Docente");

        String mensaje = "Cantidad de Socios por Tipo:\n\n" +
                        "Estudiantes: " + estudiantes + "\n" +
                        "Docentes: " + docentes;

        JOptionPane.showMessageDialog(ventanaPrincipal, mensaje, "Cantidad de Socios por Tipo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void quienTieneLibro() {
        VentanaQuienTieneLibro ventana = new VentanaQuienTieneLibro(biblioteca);
        ventana.mostrar();
    }

    private String formatoFecha(Calendar p_fecha) {
        if (p_fecha == null) return "N/A";
        return p_fecha.get(Calendar.DAY_OF_MONTH) + "/" +
               (p_fecha.get(Calendar.MONTH) + 1) + "/" +
               p_fecha.get(Calendar.YEAR);
    }
}
