import java.io.*;

/**
 * Clase utilitaria para manejar la serialización de la clase Biblioteca completa.
 */
public class GestorPersistencia {

    private static final String NOMBRE_ARCHIVO = "datos_biblioteca.dat";
    private static final String NOMBRE_BIBLIOTECA = "Biblioteca Central UNL";

    /**
     * Guarda el estado completo de la Biblioteca en un archivo.
     * @param biblioteca La instancia de Biblioteca a serializar.
     */
    public static void guardar(Biblioteca biblioteca) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            oos.writeObject(biblioteca);
            System.out.println("Estado de la Biblioteca guardado exitosamente en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar la Biblioteca: " + e.getMessage());
        }
    }

    /**
     * Carga el estado de la Biblioteca desde un archivo.
     * Si falla o el archivo no existe, devuelve una nueva instancia de Biblioteca.
     * @return La instancia de Biblioteca cargada.
     */
    public static Biblioteca cargar() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("Archivo de datos no encontrado. Creando nueva Biblioteca.");
            return new Biblioteca(NOMBRE_BIBLIOTECA);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOMBRE_ARCHIVO))) {
            Biblioteca biblioteca = (Biblioteca) ois.readObject();
            System.out.println("Biblioteca cargada exitosamente desde " + NOMBRE_ARCHIVO);
            return biblioteca;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar la Biblioteca. Creando nueva instancia: " + e.getMessage());
            return new Biblioteca(NOMBRE_BIBLIOTECA);
        }
    }
}
