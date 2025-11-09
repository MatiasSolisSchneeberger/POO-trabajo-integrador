import javax.swing.*;

public class Main {
    //private static final Biblioteca miBiblioteca = new Biblioteca("Biblioteca Central UNL");
    private static final Biblioteca miBiblioteca = GestorPersistencia.cargar();

    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema operativo (Windows, etc.)
        try {
            // para que no se vea raro los botones al seleccionar
            UIManager.put("Button.focusPainted", false);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. Inicia la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            // 2. Pasa la instancia de la biblioteca a la ventana (la "Vista")
            VentanaMain ventana = new VentanaMain(miBiblioteca);

            // 3. La hace visible
            ventana.setVisible(true);
        });
    }
}
