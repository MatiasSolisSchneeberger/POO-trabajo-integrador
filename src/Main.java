import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema operativo (Windows, etc.)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear y mostrar la ventana en el hilo de Swing
        SwingUtilities.invokeLater(() -> new VentanaMain());
    }
}
