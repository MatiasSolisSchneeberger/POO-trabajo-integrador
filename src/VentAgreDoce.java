import javax.swing.*;
import java.awt.*; // Importamos 'awt' para los LayoutManagers (BorderLayout, GridLayout) y Colores

/**
 * VentAgreDoce (Ventana Agregar Docente)
 *
 * Esta clase extiende JDialog, que es el tipo de ventana ideal para un
 * formulario emergente. No es un JFrame (ventana principal).
 */
public class VentAgreDoce extends JDialog {

    // --- Componentes de la Interfaz ---
    // Los declaramos como privados de la clase para poder acceder a ellos
    // desde los métodos (como onAgregar)
    private JTextField dniField;
    private JTextField nombreField;
    private JTextField areaField;
    private JButton agregarButton;
    private JButton cancelarButton;

    // --- Lógica de Negocio ---
    // Una referencia a nuestra biblioteca para poder llamar a sus métodos
    private Biblioteca miBiblioteca;

    /**
     * Constructor de la ventana (diálogo)
     *
     * @param owner      Es la ventana "padre" (nuestra VentanaMain).
     * @param biblioteca Es la instancia de la lógica de negocio.
     */
    public VentAgreDoce(JFrame owner, Biblioteca biblioteca) {

        // 1. Llamamos al constructor de JDialog (la clase padre)
        // El 'true' al final es MUY importante: hace que la ventana sea "MODAL".
        // Una ventana modal bloquea la ventana 'owner' (VentanaMain) hasta que
        // esta ventana (VentAgreDoce) se cierre. Evita que el usuario haga
        // clic en otros botones mientras agrega un docente.
        super(owner, "Agregar Nuevo Docente", true);

        // Guardamos la referencia a la biblioteca que nos pasaron
        this.miBiblioteca = biblioteca;

        // --- 2. Crear y Configurar Layouts y Componentes ---
        // Aquí construimos la ventana por código.

        // Usamos BorderLayout. Es un layout simple que nos da 5 zonas
        // (Norte, Sur, Este, Oeste, Centro). Nosotros usaremos Centro y Sur.
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // (hgap, vgap)

        // Le damos un borde vacío (padding) para que los componentes
        // no estén pegados a los bordes de la ventana.
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel del Formulario (lo pondremos en el CENTRO) ---

        // Usamos GridLayout. Es perfecto para formularios.
        // (0, 2) significa: "tantas filas como necesites, pero 2 columnas".
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // (filas, cols, hgap, vgap)

        // Inicializamos los componentes
        dniField = new JTextField(20); // 20 es un ancho sugerido
        nombreField = new JTextField(20);
        areaField = new JTextField(20);

        // Agregamos las etiquetas (JLabel) y los campos (JTextField) al panel
        // en orden. El GridLayout los acomodará solo.
        fieldsPanel.add(new JLabel("DNI:"));
        fieldsPanel.add(dniField);
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Área:")); // <-- El campo específico de Docente
        fieldsPanel.add(areaField);

        // --- Panel de Botones (lo pondremos en el SUR) ---

        // FlowLayout alinea los componentes uno al lado del otro.
        // Con 'FlowLayout.RIGHT', los botones se "pegan" a la derecha.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        agregarButton = new JButton("Agregar");
        cancelarButton = new JButton("Cancelar");

        // --- Aplicamos los colores a los botones ---

        // Botón Agregar (Verde)
        agregarButton.setForeground(new Color(40, 167, 69));

        // Botón Cancelar (Rojo)
        cancelarButton.setForeground(new Color(220, 53, 69));

        // Agregamos los botones al panel de botones
        buttonPanel.add(cancelarButton);
        buttonPanel.add(agregarButton);

        // --- 3. Ensamblar la ventana ---
        // Agregamos nuestros dos paneles (formulario y botones) al panel principal
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Le decimos al JDialog que 'mainPanel' es su contenido
        setContentPane(mainPanel);

        // --- 4. Configuración final del JDialog ---

        // pack() es un método mágico: ajusta el tamaño de la ventana
        // automáticamente al tamaño de los componentes que contiene.
        pack();

        // Centra la ventana sobre su 'owner' (VentanaMain)
        setLocationRelativeTo(owner);

        // Le dice a la ventana qué hacer cuando se cierra (con la 'X')
        // DISPOSE_ON_CLOSE solo destruye esta ventana, no el programa entero.
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(agregarButton);

        // --- 5. Action Listeners ---
        // Conectamos los botones a los métodos que definimos abajo
        agregarButton.addActionListener(e -> onAgregar());
        cancelarButton.addActionListener(e -> onCancelar());
    }

    /**
     * Este método se dispara cuando apretamos "Agregar".
     * Aquí va la lógica de validación y la llamada a la Biblioteca.
     */
    private void onAgregar() {
        // Usamos un try-catch por si el usuario pone letras en el DNI
        try {
            // 1. Obtenemos los datos de los campos de texto
            String dniStr = dniField.getText();
            String nombre = nombreField.getText();
            String area = areaField.getText(); // <-- El campo de Docente

            // 2. Validamos que no estén vacíos
            // .isBlank() es como .isEmpty() pero también detecta si solo hay espacios
            if (dniStr.isBlank() || nombre.isBlank() || area.isBlank()) {
                // Mostramos un popup de error
                JOptionPane.showMessageDialog(this, // 'this' es el JDialog
                        "Todos los campos son obligatorios.", // Mensaje
                        "Error de Validación", // Título de la ventana
                        JOptionPane.ERROR_MESSAGE); // Ícono de error
                return; // Cortamos la ejecución del método aquí
            }

            // 3. Convertimos el DNI a número
            int dni = Integer.parseInt(dniStr);
            // Verificar que sea positivo
            if (dni <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El DNI debe ser un número positivo.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución
            }
            // 4. ¡Llamamos a la lógica de negocio!
            // Esta es la única línea que realmente "habla" con la biblioteca.
            // Usamos el método específico para docentes.
            miBiblioteca.nuevoSocioDocente(dni, nombre, area);

            // 5. Informamos éxito y cerramos
            JOptionPane.showMessageDialog(this,
                    "Docente agregado con éxito.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE); // Ícono de información

            dispose(); // Cierra esta ventana de diálogo

        } catch (NumberFormatException ex) {
            // Este catch se activa si Integer.parseInt() falla
            JOptionPane.showMessageDialog(this,
                    "El DNI debe ser un número válido.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Un catch genérico para cualquier otro error que pueda venir
            // de la lógica de la biblioteca (ej. DNI duplicado, si lo implementaras)
            JOptionPane.showMessageDialog(this,
                    "Error al agregar socio: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este método se dispara cuando apretamos "Cancelar".
     */
    private void onCancelar() {
        // dispose() simplemente cierra esta ventana (JDialog)
        dispose();
    }
}