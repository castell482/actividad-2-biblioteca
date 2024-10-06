
package actividad2sisitemagestionbiblioteca;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Actividad2SisitemaGestionBiblioteca {

    static class Libro {
        String numeroUnico;
        String nombre;
        String autor;

        Libro(String numeroUnico, String nombre, String autor) {
            this.numeroUnico = numeroUnico;
            this.nombre = nombre;
            this.autor = autor;
        }
    }

    static class Usuario {
        String cedula;
        String nombres;
        String apellidos;

        Usuario(String cedula, String nombres, String apellidos) {
            this.cedula = cedula;
            this.nombres = nombres;
            this.apellidos = apellidos;
        }
    }

    public static void main(String[] args) {
        ArrayList<Libro> libros = new ArrayList<>();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Stack<Libro> librosAlquilados = new Stack<>();

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar menú
            System.out.println("\n=== Sistema de Gestión de Biblioteca ===");
            System.out.println("1. Agregar libro");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Alquilar libro");
            System.out.println("4. Retorno libro");
            System.out.println("5. Mostrar libros disponibles");
            System.out.println("6. Mostrar usuarios registrados");
            System.out.println("7. Mostrar libros alquilados");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el número único del libro: ");
                    String numeroUnico = sc.nextLine().trim();

                    boolean numeroExistente = false;
                    for (Libro libro : libros) {
                        if (libro.numeroUnico.equals(numeroUnico)) {
                            numeroExistente = true;
                            break;
                        }
                    }
                    if (numeroExistente) {
                        System.out.println("El número único '" + numeroUnico + "' ya está registrado. Intenta con otro.");
                        break;
                    }

                    System.out.print("Introduce el nombre del libro: ");
                    String nombreLibro = sc.nextLine().trim().toLowerCase();

                    System.out.print("Introduce el autor del libro: ");
                    String autorLibro = sc.nextLine().trim().toLowerCase();

                    libros.add(new Libro(numeroUnico, nombreLibro, autorLibro));
                    System.out.println("Libro '" + nombreLibro + "' agregado exitosamente.");
                    break;

                case 2:
                    System.out.print("Introduce la cédula del usuario: ");
                    String cedulaUsuario = sc.nextLine().trim();

                    if (!cedulaUsuario.matches("\\d+")) {
                        System.out.println("La cédula solo puede contener números. Inténtalo de nuevo.");
                        break;
                    }

                    System.out.print("Introduce los nombres del usuario: ");
                    String nombresUsuario = sc.nextLine().trim().toLowerCase();

                    System.out.print("Introduce los apellidos del usuario: ");
                    String apellidosUsuario = sc.nextLine().trim().toLowerCase();

                    usuarios.add(new Usuario(cedulaUsuario, nombresUsuario, apellidosUsuario));
                    System.out.println("Usuario '" + nombresUsuario + " " + apellidosUsuario + "' registrado exitosamente.");
                    break;

                case 3:
                    System.out.print("Introduce el número único del libro a alquilar: ");
                    String numUnicoAlquilar = sc.nextLine().trim();

                    Libro libroEncontrado = null;
                    for (Libro libro : libros) {
                        if (libro.numeroUnico.equals(numUnicoAlquilar)) {
                            libroEncontrado = libro;
                            break;
                        }
                    }

                    if (libroEncontrado == null) {
                        System.out.println("No se encontró un libro con el número único '" + numUnicoAlquilar + "'.");
                        break;
                    }

                    System.out.print("Introduce la cédula del usuario que alquila: ");
                    String cedulaAlquilador = sc.nextLine().trim();

                    boolean usuarioExistente = false;
                    for (Usuario usuario : usuarios) {
                        if (usuario.cedula.equals(cedulaAlquilador)) {
                            usuarioExistente = true;
                            break;
                        }
                    }

                    if (!usuarioExistente) {
                        System.out.println("No se puede alquilar el libro porque el usuario con cédula '" + cedulaAlquilador + "' no está registrado.");
                        break;
                    }

                    if (!librosAlquilados.contains(libroEncontrado)) {
                        librosAlquilados.push(libroEncontrado); // Añadir a la pila de libros alquilados
                        System.out.println("El libro '" + libroEncontrado.nombre + "' ha sido alquilado al usuario con cédula '" + cedulaAlquilador + "'.");
                    } else {
                        System.out.println("El libro '" + libroEncontrado.nombre + "' ya está alquilado.");
                    }
                    break;

                case 4:
                    if (!librosAlquilados.isEmpty()) {
                        Libro libroDevuelto = librosAlquilados.pop();  // Sacar el último libro de la pila
                        System.out.println("El libro '" + libroDevuelto.nombre + "' ha sido devuelto.");
                    } else {
                        System.out.println("No hay libros para devolver.");
                    }
                    break;

                case 5:
                    if (libros.isEmpty()) {
                        System.out.println("No hay libros registrados.");
                    } else {
                        System.out.println("\n=== Libros disponibles ===");
                        System.out.printf("%-15s %-30s %-30s%n", "Número Único", "Título", "Autor");
                        System.out.println("------------------------------------------------------------");
                        for (Libro libro : libros) {
                            if (!librosAlquilados.contains(libro)) {  // Mostrar solo los libros no alquilados
                                System.out.printf("%-15s %-30s %-30s%n", libro.numeroUnico, libro.nombre, libro.autor);
                            }
                        }
                    }
                    break;

                case 6:
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        System.out.println("\n=== Usuarios registrados ===");
                        System.out.printf("%-15s %-30s %-30s%n", "Cédula", "Nombres", "Apellidos");
                        System.out.println("------------------------------------------------------------");
                        for (Usuario usuario : usuarios) {
                            System.out.printf("%-15s %-30s %-30s%n", usuario.cedula, usuario.nombres, usuario.apellidos);
                        }
                    }
                    break;

                case 7:
                    if (librosAlquilados.isEmpty()) {
                        System.out.println("No hay libros alquilados.");
                    } else {
                        System.out.println("\n=== Libros alquilados ===");
                        System.out.printf("%-15s %-30s %-30s%n", "Número Único", "Título", "Autor");
                        System.out.println("------------------------------------------------------------");
                        for (Libro libroAlquilado : librosAlquilados) {
                            System.out.printf("%-15s %-30s %-30s%n", libroAlquilado.numeroUnico, libroAlquilado.nombre, libroAlquilado.autor);
                        }
                    }
                    break;

                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no está en la lista del menú, vuelva a seleccionar.");
            }
        } while (opcion != 8);

        sc.close();
    }
}
