package sudoku;

import sudoku.interfaces.IJuegoSudoku;

import java.util.Scanner;

public class JuegoSudoku implements IJuegoSudoku {

    private Sudoku sudoku;
    private Scanner sc;

    public JuegoSudoku() {
        sudoku = new Sudoku();
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== Juego Sudoku ===");
        System.out.println("Elige dificultad (fácil, medio, difícil): ");
        String dificultad = sc.nextLine().trim().toLowerCase();

        if (!dificultad.equals("facil") && !dificultad.equals("medio") && !dificultad.equals("dificil")) {
            System.out.println("Dificultad no válida, se usará 'fácil' por defecto.");
            dificultad = "facil";
        }

        sudoku.generarTablero(dificultad);
        System.out.println("Tablero Generado:");
        sudoku.mostrarTablero();

        while (true) {
            System.out.println("\nIntroduce tu movimiento (fila columna valor), usa 0 para borrar o 'salir' para terminar:");
            String linea = sc.nextLine().trim();

            if (linea.equalsIgnoreCase("salir")) {
                System.out.println("Gracias por jugar.");
                break;
            }

            String[] partes = linea.split("\\s+");

            if (partes.length != 3) {
                System.err.println("Entrada incorrecta. Formato: fila(1-9) columna(1-9) valor(0-9). Utiliza espacios.");
                continue;
            }

            try {
                int fila = Integer.parseInt(partes[0]) - 1;
                int columna = Integer.parseInt(partes[1]) - 1;
                int valor = Integer.parseInt(partes[2]);

                sudoku.colocarNumero(fila, columna, valor);
                sudoku.mostrarTablero();

                if (sudoku.estaResuelto()) {
                    System.out.println("¡Felicidades! Has completado el Sudoku.");
                    break;
                }

            } catch (NumberFormatException e) {
                System.err.println("Debes ingresar números válidos.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Juego terminado.");
    }
}
