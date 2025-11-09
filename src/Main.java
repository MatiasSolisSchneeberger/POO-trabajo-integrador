import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema operativo (Windows, etc.)
        try {
            // para que no se vea raro los botones al seleccionar
            UIManager.put("Button.focusPainted", false);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. Crea la instancia de la lógica de negocio (el "Modelo")
        Biblioteca miBiblioteca = new Biblioteca("Biblioteca Corrientes");

        // 2. Inicia la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            // 3. Pasa la instancia de la biblioteca a la ventana (la "Vista")
            VentanaMain ventana = new VentanaMain(miBiblioteca);

            // 4. La hace visible
            ventana.setVisible(true);
        });
    }
}
