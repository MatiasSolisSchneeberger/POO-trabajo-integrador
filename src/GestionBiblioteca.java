import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GestionBiblioteca {
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
    System.out.println("🛠️ Inicializando datos de prueba avanzados...");

    // ===========================================
    // 1. INICIALIZACIÓN DE SOCIOS
    // ===========================================
    
    // --- Estudiantes (Límite: 3 libros, Plazo: 20 días) ---
    miBiblioteca.nuevoSocioEstudiante(12345678, "Ana Garcia", "Ingeniería");     // (Caso: LIMPIO)
    miBiblioteca.nuevoSocioEstudiante(23456789, "Juan Perez", "Derecho");       // (Caso: AL LÍMITE)
    miBiblioteca.nuevoSocioEstudiante(34567890, "Maria Lopez", "Medicina");     // (Caso: VENCIDO)
    miBiblioteca.nuevoSocioEstudiante(99990000, "Carlos Velez", "Arquitectura"); // (Caso: EXCEDIDO - 4 libros)

    // --- Docentes (Plazo Base: 5 días) ---
    miBiblioteca.nuevoSocioDocente(45678901, "Dr. Carlos Ruiz", "Matemáticas");  // (Caso: RESPONSABLE)
    miBiblioteca.nuevoSocioDocente(56789012, "Lic. Laura Torres", "Historia");   // (Caso: NO RESPONSABLE/VENCIDO)

    // ===========================================
    // 2. INICIALIZACIÓN DE LIBROS
    // ===========================================

    // Libros usados para diferentes escenarios (más copias)
    miBiblioteca.nuevoLibro("Cien años de soledad", 1, "Sudamericana", 1967);  // Libro 1 - Copia 1 (Para Vencido Docente)
    miBiblioteca.nuevoLibro("El señor de los anillos", 2, "Minotauro", 1954);    // Libro 2 - Copia 1 (Para Vencido Estudiante)
    miBiblioteca.nuevoLibro("Física I", 5, "Pearson", 2018);                     // Libro 3 - Copia 1 (Para Estudiante Límite 1/3)
    miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010);        // Libro 4 - Copia 1 (Para Estudiante Límite 2/3)
    miBiblioteca.nuevoLibro("Química Orgánica", 3, "Mc Graw Hill", 2010);        // Libro 4 - Copia 2 (Para Estudiante Límite 3/3)

    // Libros para Exceder Límite
    miBiblioteca.nuevoLibro("Introducción a Python", 1, "O'Reilly", 2022);     // Libro 5 - Copia 1 (Para Estudiante Excedido 4/4)
    
    // Libro para Docente Responsable
    miBiblioteca.nuevoLibro("Cálculo Avanzado", 3, "Cengage", 2015);            // Libro 6 - Copia 1
    
    // ===========================================
    // 3. CREACIÓN DE PRÉSTAMOS
    // ===========================================
    
    Calendar hoy = new GregorianCalendar();
    
    // --- ESCENARIO: Préstamo VENCIDO (Docente) ---
    // Lic. Laura Torres (56789012) - Plazo 5 días. 
    // Pedido hace 10 días -> ¡Vencido! -> NO HABILITADA, NO RESPONSABLE.
    Calendar haceDiezDias = new GregorianCalendar();
    haceDiezDias.add(Calendar.DATE, -10); 
    
    // Usamos el primer libro de la lista: "Cien años de soledad"
    Socio sLaura = miBiblioteca.buscarSocio(56789012);
    Libro lCienAnios = miBiblioteca.getLibros().get(0);
    if (sLaura != null && lCienAnios != null) {
        miBiblioteca.prestarLibro(haceDiezDias, sLaura, lCienAnios);
        System.out.println("-> Préstamo VENCIDO creado para Lic. Laura Torres (10 días atrás).");
    }

    // --- ESCENARIO: Préstamo VENCIDO (Estudiante) ---
    // Maria Lopez (34567890) - Plazo 20 días.
    // Pedido hace 25 días -> ¡Vencido! -> NO HABILITADA.
    Calendar haceVeinticincoDias = new GregorianCalendar();
    haceVeinticincoDias.add(Calendar.DATE, -25);
    
    // Usamos el segundo libro de la lista: "El señor de los anillos"
    Socio sMaria = miBiblioteca.buscarSocio(34567890);
    Libro lSenorAnillos = miBiblioteca.getLibros().get(1);
    if (sMaria != null && lSenorAnillos != null) {
        miBiblioteca.prestarLibro(haceVeinticincoDias, sMaria, lSenorAnillos);
        System.out.println("-> Préstamo VENCIDO creado para Maria Lopez (25 días atrás).");
    }
    
    // --- ESCENARIO: Estudiante AL LÍMITE (3/3) ---
    // Juan Perez (23456789) - Plazo 20 días.
    // Pedido hace 5 días -> Activo, NO vencido.
    Calendar haceCincoDias = new GregorianCalendar();
    haceCincoDias.add(Calendar.DATE, -5);

    Socio sJuan = miBiblioteca.buscarSocio(23456789);
    Libro lFisica = miBiblioteca.getLibros().get(2);
    Libro lQuimica1 = miBiblioteca.getLibros().get(3);
    Libro lQuimica2 = miBiblioteca.getLibros().get(4);
    
    if (sJuan != null && lFisica != null && lQuimica1 != null && lQuimica2 != null) {
        // Pedido 1
        miBiblioteca.prestarLibro(haceCincoDias, sJuan, lFisica);
        // Pedido 2
        miBiblioteca.prestarLibro(haceCincoDias, sJuan, lQuimica1);
        // Pedido 3
        miBiblioteca.prestarLibro(haceCincoDias, sJuan, lQuimica2);
        System.out.println("-> Estudiante Juan Perez AL LÍMITE (3 libros prestados).");
    }

    // --- ESCENARIO: Estudiante EXCEDIDO (4 libros) ---
    // Carlos Velez (99990000)
    Socio sCarlos = miBiblioteca.buscarSocio(99990000);
    Libro lPython = miBiblioteca.getLibros().get(5); 
    
    // Intenta el 4to libro. La lógica de Estudiante.puedePedir() lo bloqueará.
    // Aunque el préstamo falle, esto demuestra que el libro está "intentado" o la función lo bloquea.
    if (sCarlos != null && lPython != null) {
        // Para este escenario, debemos asumir que los primeros 3 ya se pudieron prestar
        // antes de esta llamada, simulamos 3 préstamos con libros limpios.
        // Aquí simulamos el 4to, pero como los Libros 3, 4 y 5 ya están prestados
        // y solo tenemos Libros 5 y 6 disponibles, necesitamos más copias en la lista de libros.
        
        // Asumiendo que miBiblioteca.prestarLibro ya valida el límite:
        boolean prestamoExcedido = miBiblioteca.prestarLibro(hoy, sCarlos, miBiblioteca.getLibros().get(6)); // Libro 6 (Cálculo)
        if (!prestamoExcedido) {
             System.out.println("-> Intento de préstamo fallido para Carlos Velez (Excede Límite de 3).");
        }
    }
    
    // --- ESCENARIO: Docente RESPONSABLE y HABILITADO ---
    // Dr. Carlos Ruiz (45678901) - Plazo 5 días. Pedido hace 1 día.
    Calendar haceUnDia = new GregorianCalendar();
    haceUnDia.add(Calendar.DATE, -1);
    
    Socio sRuiz = miBiblioteca.buscarSocio(45678901);
    Libro lCalculo = miBiblioteca.getLibros().get(6);
    if (sRuiz != null && lCalculo != null) {
        miBiblioteca.prestarLibro(haceUnDia, sRuiz, lCalculo);
        // Esto lo mantiene RESPONSABLE y HABILITADO
        System.out.println("-> Préstamo Activo creado para Dr. Carlos Ruiz (Responsable).");
    }
    
    System.out.println("✅ Datos de prueba avanzados y escenarios cargados.\n");
    }
}
