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

        // 1. Crea la instancia de la lógica de negocio (el "Modelo")
        if(miBiblioteca.getSocios().isEmpty() && miBiblioteca.getLibros().isEmpty()){
            inicializarDatos();
        }
        // 2. Inicia la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            // 3. Pasa la instancia de la biblioteca a la ventana (la "Vista")
            VentanaMain ventana = new VentanaMain(miBiblioteca);

            // 4. La hace visible
            ventana.setVisible(true);
        });
    }

    private static void inicializarDatos() {
        System.out.println("🛠️ Inicializando datos de prueba...");

        // Socios Estudiantes (días de préstamo por defecto: 20, según ctor de BibliotecaEstudiante)
        miBiblioteca.nuevoSocioEstudiante(12345678, "Ana Garcia", "Ingeniería");
        miBiblioteca.nuevoSocioEstudiante(23456789, "Juan Perez", "Derecho");
        miBiblioteca.nuevoSocioEstudiante(34567890, "Maria Lopez", "Medicina"); // Para prueba de límite (3+1)

        // Socios Docentes (días de préstamo por defecto: 5, según ctor de BibliotecaDocente)
        miBiblioteca.nuevoSocioDocente(45678901, "Dr. Carlos Ruiz", "Matemáticas");
        miBiblioteca.nuevoSocioDocente(56789012, "Lic. Laura Torres", "Historia");

        // Libros
        miBiblioteca.nuevoLibro("Cien años de soledad", 1, "Sudamericana", 1967);
        miBiblioteca.nuevoLibro("El señor de los anillos", 2, "Minotauro", 1954);
        miBiblioteca.nuevoLibro("Física I", 5, "Pearson", 2018);
        miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010);
        miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010); // Duplicado para prueba

        System.out.println("✅ Datos de prueba cargados.\n");
    }
}
