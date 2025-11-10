import java.util.*; // Calendar, GregorianCalendar, ArrayList, HashMap, Map, Iterator, Set, LinkedHashSet
import java.io.Serializable;
/**
 * Clase Biblioteca. Implementa la clase biblioteca con sus respectivos métodos y correcto funcionamiento.
 * @author Fernandez, Gabriel Elías - González, Rodrigo Exequiel - Franco, Axel Damián.
 * @version 04/11/2025
 */
/**
public class Biblioteca implements java.io.Serializable{

    // ================= Atributos
    private String nombre;
    private HashMap<Integer, Socio> socios; // (guía) socios por DNI
    private ArrayList<Libro> libros;

    // ================= Constructores

    /**
     * Constructor que instancia los atributos del objeto de la clase Biblioteca
     *
     * @param p_nombre es el nombre de la biblioteca
     */
    public Biblioteca(String p_nombre) {
        this.setNombre(p_nombre);
        this.setSocios(new HashMap<>());
        this.setLibros(new ArrayList<>());
    }

    /**
     * Constructor que instancia los atributos del objeto de la clase Biblioteca
     *
     * @param p_nombre nombre
     * @param p_socios mapa de socios (dni->socio)
     * @param p_libros lista de libros
     */
    public Biblioteca(String p_nombre, HashMap<Integer, Socio> p_socios, ArrayList<Libro> p_libros) {
        this.setNombre(p_nombre);
        this.setSocios(p_socios);
        this.setLibros(p_libros);
    }

    // ================= Setters (privados como en tu guía)
    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    private void setSocios(HashMap<Integer, Socio> p_Socios) {
        this.socios = p_Socios;
    }

    private void setLibros(ArrayList<Libro> p_libros) {
        this.libros = p_libros;
    }

    // ================= Getters
    public String getNombre() {
        return this.nombre;
    }

    public HashMap<Integer, Socio> getSocios() {
        return this.socios;
    }

    public ArrayList<Libro> getLibros() {
        return this.libros;
    }

    // ================= Métodos de gestión

    /**
     * Agrega un socio al mapa (clave: DNI)
     *
     * @param p_socio socio a agregar
     */
    public void agregarSocio(Socio p_socio) {
        if (p_socio == null) return;
        // CAMBIO: en vez de containsValue (O(n) y depende de equals),
        // usamos la clave DNI para evitar duplicados de forma determinística.
        if (this.getSocios().containsKey(p_socio.getDniSocio())) {
            System.out.println("El socio ya se encuentra registrado.");
        } else {
            this.getSocios().put(p_socio.getDniSocio(), p_socio);
            System.out.println("El socio fue registrado correctamente.");
        }
    }

    /**
     * Quita un socio del mapa
     *
     * @param p_socio socio a quitar
     */
    public void quitarSocio(Socio p_socio) {
        if (p_socio == null) return;
        // CAMBIO: eliminamos por clave DNI directamente.
        if (this.getSocios().remove(p_socio.getDniSocio()) != null) {
            System.out.println("Se dio de baja al socio: " + p_socio.getNombre());
        } else {
            System.out.println("El socio que desea eliminar no existe.");
        }
    }

    /**
     * Agrega un libro a la lista
     */
    private void agregarLibro(Libro p_libro) {
        if (p_libro == null) return;
        this.getLibros().add(p_libro);
        System.out.println("El libro fue agregado correctamente a la biblioteca.");
    }

    /**
     * Quita un libro de la lista (si coincide por campos y NO está prestado)
     */
    public void quitarLibro(Libro libroAEliminar) {
        if (libroAEliminar == null) return;

        boolean libroEncontrado = false;
        // CAMBIO: usamos Iterator.remove() para evitar ConcurrentModificationException
        Iterator<Libro> it = this.getLibros().iterator();
        while (it.hasNext()) {
            Libro libro = it.next();
            if (libro.getTitulo().equals(libroAEliminar.getTitulo()) &&
                    libro.getEdicion() == libroAEliminar.getEdicion() &&
                    libro.getEditorial().equals(libroAEliminar.getEditorial()) &&
                    libro.getAnio() == libroAEliminar.getAnio() &&
                    !libro.prestado()) {
                it.remove();
                System.out.println("Libro eliminado correctamente");
                libroEncontrado = true;
                break;
            }
        }
        if (!libroEncontrado) {
            System.out.println("El libro que desea eliminar no se encuentra en la biblioteca.");
        }
    }

    // ================= Préstamos / Devoluciones

    /**
     * Verifica si el libro no ha sido prestado y si el socio puede pedir; si es así, realiza el préstamo
     *
     * @return true si se concretó el préstamo
     */
    public boolean prestarLibro(Calendar p_FechaRetiro, Socio p_Socio, Libro p_libro) {
        if (p_FechaRetiro == null || p_Socio == null || p_libro == null) return false;

        // Obtener las referencias "oficiales" del mapa/lista
        Socio socioPrestar = this.getSocios().get(p_Socio.getDniSocio());
        Libro libroPrestar = null;
        int idx = this.getLibros().indexOf(p_libro);
        if (idx >= 0) libroPrestar = this.getLibros().get(idx);

        if (socioPrestar == null || libroPrestar == null) return false;
        if (!socioPrestar.puedePedir() || libroPrestar.prestado()) return false;

        Prestamo unPrestamo = new Prestamo(p_FechaRetiro, socioPrestar, libroPrestar);
        // Si el .jar hace auto-registro del préstamo, estas llamadas pueden ser innecesarias;
        // las dejamos por compatibilidad:
        libroPrestar.agregarPrestamo(unPrestamo);
        socioPrestar.agregarPrestamo(unPrestamo);
        return true;
    }

    /**
     * Asigna la fecha de devolución del préstamo con la fecha actual
     */
    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (p_libro == null) return;
        Calendar fechaActual = Calendar.getInstance();

        if (!p_libro.prestado()) {
            // CAMBIO: mensaje alineado a la consigna usual (sin dos puntos tras "El libro")
            throw new LibroNoPrestadoException(
                    "El libro " + p_libro.getTitulo() + " no se puede devolver ya que se encuentra en la biblioteca"
            );
        } else {
            p_libro.ultimoPrestamo().registrarFechaDevolucion(fechaActual);
        }
    }

    // ================= Consultas

    /**
     * Devuelve la cantidad de socios por tipo (e.g., "Estudiante", "Docente")
     * (tu guía lo llamaba 'cantidadDeSociosPortipo' con 't' minúscula; dejamos ambos)
     */
    public int cantidadDeSociosPorTipo(String p_Objeto) { // CAMBIO: nombre "PorTipo" (alias principal)
        if (p_Objeto == null) return 0;
        int contador = 0;
        for (Map.Entry<Integer, Socio> socio : this.getSocios().entrySet()) {
            String tipo = socio.getValue().soyDeLaClase();
            if (tipo != null && tipo.equalsIgnoreCase(p_Objeto)) contador++;
        }
        return contador;
    }

    /**
     * Devuelve una colección con los préstamos vencidos al día de la fecha
     */
    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> prestamosVencidos = new ArrayList<>();
        Calendar hoy = new GregorianCalendar(); // fecha "hoy"
        for (Map.Entry<Integer, Socio> e : this.getSocios().entrySet()) {
            Socio s = e.getValue();
            if (s == null || s.getPrestamos() == null) continue;
            for (Prestamo pr : s.getPrestamos()) {
                if (pr != null && pr.vencido(hoy)) {
                    prestamosVencidos.add(pr);
                }
            }
        }
        return prestamosVencidos;
    }

    /**
     * Devuelve una colección con los docentes responsables
     */
    public ArrayList<Docente> docentesResponsables() {
        ArrayList<Docente> docentesResponsables = new ArrayList<>();
        for (Map.Entry<Integer, Socio> e : this.getSocios().entrySet()) {
            Socio s = e.getValue();
            // CAMBIO: usamos 'instanceof' en lugar de getClass()==Docente.class para permitir herencia
            if (s instanceof Docente) {
                Docente d = (Docente) s;
                if (d.esResponsable()) docentesResponsables.add(d);
            }
        }
        return docentesResponsables;
    }

    /**
     * Indica quién tiene el libro; si no está prestado, lanza excepción
     */
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException {
        //if (p_libro == null) throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        Prestamo ultimoPrestamo = p_libro.ultimoPrestamo();
        for (Libro libro : this.getLibros()) {
            if (libro.getTitulo().equalsIgnoreCase(p_libro.getTitulo()) &&
                    libro.getEdicion() == p_libro.getEdicion() &&
                    libro.getEditorial().equalsIgnoreCase(p_libro.getEditorial()) &&
                    libro.getAnio() == p_libro.getAnio()) {

                if (libro.prestado()) {
                    Prestamo ultimoPrestamo1 = p_libro.ultimoPrestamo();
                    return "El libro está en posesión de: " + ultimoPrestamo1.getSocio().getNombre()
                            + p_libro.getTitulo();
                } else {
                    throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
                }
            }
        }
        return ultimoPrestamo.getSocio().getNombre() + p_libro.getTitulo();
    }

    /**
     * Devuelve el Socio con el DNI indicado o null si no existe
     */
    public Socio buscarSocio(int p_dni) {
        // CAMBIO: aprovechamos autoboxing (no crear new Integer(p_dni))
        return this.getSocios().get(p_dni);
    }

    // ================= Listados (respetando tu formato base, con ajustes)

    /**
     * Devuelve la lista y cantidad de socios
     */
    public String listaDeSocios() {
        if (this.getSocios().isEmpty()) {
            return "No hay socios en la biblioteca";
        } else {
            int nroSocio = 0;
            String lista = "Lista de Socios: \n";
            for (Map.Entry<Integer, Socio> e : this.getSocios().entrySet()) {
                Socio s = e.getValue();
                lista = lista + s.toString() + "\n";
            }
            lista = lista
                    + "******\n"
                    + "Cant. Socios tipo Estudiante: " + this.cantidadDeSociosPorTipo("Estudiante") + "\n"
                    + "Cant. Socios tipo Docente: " + this.cantidadDeSociosPorTipo("Docente") + "\n"
                    + "******";
            return lista;
        }
    }


    /**
     * Devuelve la lista de libros
     */
    public String listaDeLibros() {
        String lista = "";
        int i = 0;
        for (Libro unLibro : this.getLibros()) {
            lista = lista
                    + (++i) + ") Titulo: " + unLibro.getTitulo()
                    + " || " + "Prestado: (" + (unLibro.prestado() ? "Si" : "No") + ")"
                    + "\n";
        }
        return lista;
    }


    /**
     * Devuelve una lista de títulos SIN repetir
     */
    public String listaDeTitulos() {
        // Usamos LinkedHashSet para no repetir y conservar orden de aparición
        Set<String> set = new LinkedHashSet<>();
        for (Libro l : this.getLibros()) {
            set.add(l.getTitulo());
        }
        String titulos = "";
        for (String t : set) {
            titulos = titulos + t + "\n";
        }
        return titulos;
    }


    /**
     * Devuelve un listado de docentes responsables
     */
    public String listaDeDocentesResponsables() {
        String docentes = "";
        for (Docente d : this.docentesResponsables()) {
            docentes += d.toString() + "\n";
        }
        return docentes;
    }


    // ================= Altas "convenience" (respetando tu guía)

    /**
     * Añade un nuevo libro a la biblioteca
     */
    public void nuevoLibro(String p_Titulo, int p_Edicion, String p_Editorial, int p_Anio) {
        this.agregarLibro(new Libro(p_Titulo, p_Edicion, p_Editorial, p_Anio, new ArrayList<>())); // no sé si va o no el new ArrayList
    }

    /**
     * Añade un nuevo socio de tipo estudiante
     */
    public void nuevoSocioEstudiante(int p_DniSocio, String p_Nombre, String p_Carrera) {
        this.agregarSocio(new Estudiante(p_DniSocio, p_Nombre, p_Carrera));
    }

    /**
     * Añade un nuevo socio de tipo docente
     */
    public void nuevoSocioDocente(int p_DniSocio, String p_Nombre, String p_Area) {
        this.agregarSocio(new Docente(p_DniSocio, p_Nombre, p_Area));
    }
}
