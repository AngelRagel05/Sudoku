package sudoku;

import sudoku.interfaces.IJuegoSudoku;

import java.io.PrintStream;
import java.util.Scanner;

public class JuegoSudoku implements IJuegoSudoku {

    // Instancia del juego de Sudoku
    private Sudoku sudoku;
    // Objeto para leer la entrada del usuario
    private Scanner sc;
    // Objeto para imprimir la salida (puede ser consola u otro destino)
    private PrintStream out;

    // Constructor por defecto, usa la entrada estándar (teclado) y la salida estándar (consola)
    public JuegoSudoku() {
        this(new Scanner(System.in), System.out);
    }

    // Constructor con inyección de dependencias: permite usar diferentes entradas y salidas (útil para tests)
    public JuegoSudoku(Scanner sc, PrintStream out) {
        sudoku = new Sudoku(); // Crea un nuevo tablero de Sudoku
        this.sc = sc;
        this.out = out;
    }

    // Metodo principal que inicia el juego
    public void iniciar() {
        out.println("=== Juego Sudoku ===");
        out.println("Elige dificultad (fácil, medio, difícil): ");
        String dificultad = sc.nextLine().trim().toLowerCase();

        // Validación de la dificultad
        if (!dificultad.equals("facil") && !dificultad.equals("medio") && !dificultad.equals("dificil")) {
            out.println("Dificultad no válida, se usará 'fácil' por defecto.");
            dificultad = "facil";
        }

        // Genera el tablero según la dificultad elegida
        sudoku.generarTablero(dificultad);

        out.println("Tablero Generado:");
        sudoku.mostrarTablero(); // Muestra el tablero (este metodo debería modificarse para usar 'out')

        // Bucle principal del juego
        while (true) {
            out.println("\nIntroduce tu movimiento (fila columna valor), usa 0 para borrar o 'salir' para terminar:");
            String linea = sc.nextLine().trim();

            // Permite salir del juego
            if (linea.equalsIgnoreCase("salir")) {
                out.println("Gracias por jugar.");
                break;
            }

            // Divide la entrada del usuario en partes separadas por espacios
            String[] partes = linea.split("\\s+");

            // Validación de formato de entrada
            if (partes.length != 3) {
                out.println("Entrada incorrecta. Formato: fila(1-9) columna(1-9) valor(0-9). Utiliza espacios.");
                continue;
            }

            try {
                // Convierte las entradas a enteros y ajusta a índice base 0
                int fila = Integer.parseInt(partes[0]) - 1;
                int columna = Integer.parseInt(partes[1]) - 1;
                int valor = Integer.parseInt(partes[2]);

                // Intenta colocar el número en el tablero
                sudoku.colocarNumero(fila, columna, valor);

                // Muestra el tablero actualizado
                mostrarTableroPersonalizado();

                // Verifica si el tablero ya está resuelto
                if (sudoku.estaResuelto()) {
                    out.println("¡Felicidades! Has completado el Sudoku.");
                    break;
                }

            } catch (NumberFormatException e) {
                // Captura errores al ingresar datos no numéricos
                out.println("Debes ingresar números válidos.");
            } catch (Exception e) {
                // Captura errores de lógica u otros fallos
                out.println("Error: " + e.getMessage());
            }
        }

        out.println("Juego terminado.");
    }

    // Metodo privado para mostrar el tablero con el formato de consola y usando 'out'
    private void mostrarTableroPersonalizado() {
        for (int fila = 0; fila < 9; fila++) {
            // Imprime líneas horizontales cada 3 filas para separar los bloques
            if (fila % 3 == 0) out.println("+-------+-------+-------+");

            StringBuilder linea = new StringBuilder();

            for (int col = 0; col < 9; col++) {
                // Imprime líneas verticales cada 3 columnas para separar los bloques
                if (col % 3 == 0) linea.append("| ");
                // Muestra un punto si la celda está vacía (0), o el número correspondiente
                linea.append(sudoku.tablero[fila][col] == 0 ? ". " : sudoku.tablero[fila][col] + " ");
            }

            linea.append("|");
            out.println(linea); // Imprime la línea de la fila actual
        }

        out.println("+-------+-------+-------+"); // Línea final del tablero
    }
}
