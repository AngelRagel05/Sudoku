package sudoku;

import sudoku.interfaces.IJuegoSudoku;

import java.io.PrintStream;
import java.util.Scanner;

public class JuegoSudoku implements IJuegoSudoku {

    private Sudoku sudoku;
    private Scanner sc;
    private PrintStream out;

    // Constructor por defecto (mantener para uso normal)
    public JuegoSudoku() {
        this(new Scanner(System.in), System.out);
    }

    // Constructor para inyección de dependencias (entrada y salida)
    public JuegoSudoku(Scanner sc, PrintStream out) {
        sudoku = new Sudoku();
        this.sc = sc;
        this.out = out;
    }

    public void iniciar() {
        out.println("=== Juego Sudoku ===");
        out.println("Elige dificultad (fácil, medio, difícil): ");
        String dificultad = sc.nextLine().trim().toLowerCase();

        if (!dificultad.equals("facil") && !dificultad.equals("medio") && !dificultad.equals("dificil")) {
            out.println("Dificultad no válida, se usará 'fácil' por defecto.");
            dificultad = "facil";
        }

        sudoku.generarTablero(dificultad);
        out.println("Tablero Generado:");
        sudoku.mostrarTablero(); // Aquí querrías modificar mostrarTablero para que use 'out'

        while (true) {
            out.println("\nIntroduce tu movimiento (fila columna valor), usa 0 para borrar o 'salir' para terminar:");
            String linea = sc.nextLine().trim();

            if (linea.equalsIgnoreCase("salir")) {
                out.println("Gracias por jugar.");
                break;
            }

            String[] partes = linea.split("\\s+");

            if (partes.length != 3) {
                out.println("Entrada incorrecta. Formato: fila(1-9) columna(1-9) valor(0-9). Utiliza espacios.");
                continue;
            }

            try {
                int fila = Integer.parseInt(partes[0]) - 1;
                int columna = Integer.parseInt(partes[1]) - 1;
                int valor = Integer.parseInt(partes[2]);

                sudoku.colocarNumero(fila, columna, valor);

                // Mostrar tablero (modificaremos método)
                mostrarTableroPersonalizado();

                if (sudoku.estaResuelto()) {
                    out.println("¡Felicidades! Has completado el Sudoku.");
                    break;
                }

            } catch (NumberFormatException e) {
                out.println("Debes ingresar números válidos.");
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            }
        }
        out.println("Juego terminado.");
    }

    // Nuevo metodo que imprime el tablero usando 'out' para que sea testeable
    private void mostrarTableroPersonalizado() {
        for (int fila = 0; fila < 9; fila++) {
            if (fila % 3 == 0) out.println("+-------+-------+-------+");
            StringBuilder linea = new StringBuilder();
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0) linea.append("| ");
                linea.append(sudoku.tablero[fila][col] == 0 ? ". " : sudoku.tablero[fila][col] + " ");
            }
            linea.append("|");
            out.println(linea);
        }
        out.println("+-------+-------+-------+");
    }
}
