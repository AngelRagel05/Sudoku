package sudoku;

import java.util.Scanner;

public class JuegoSudoku {

    private Sudoku sudoku;
    private Scanner sc;
    private boolean check = false;

    public JuegoSudoku() {
        sudoku = new Sudoku();
        sc = new Scanner(System.in);
    }

    public void iniciar() {

//        Pido al usuario la dificultad
        System.out.println("=== Juego Sudoku ===");
        System.out.println("Elige dificultad (fácil, medio, difícil): ");
        String dificultad = sc.nextLine().trim().toLowerCase();

//        Genero el tablero
        sudoku.generarTablero(dificultad);
        System.out.println("Tablero Generado:");

        while (!check) {

//            Le pido al usuario que introduzca su movimiento
            System.out.println("\nIntroduce tu movimiento (fila columna valor) o 'salir' para terminar:");
            String linea = sc.nextLine().trim();

//            Si el usuario introduce 'salir' se acaba la aplicación
            if (linea.equalsIgnoreCase("salir")) {
                System.out.println("Gracias por jugar.");
                check = true;
                continue;
            }

//            Divido el movimiento del usuario
            String partes[] = linea.split("\\s+");

//            Miro que haya metido bien el formato
            if (partes.length != 3) {
                System.err.println("Entrada incorrecta. Formato: fila(1-9) columna (1-9) valor (1-9).");
                continue;
            }

            try {

//                Parseo los número de String a Int
                int fila = Integer.parseInt(partes[0]) - 1;
                int columna = Integer.parseInt(partes[1]) - 1;
                int valor = Integer.parseInt(partes[2]);

//                Valido si es correcto o no
                if (!sudoku.esMovimientoValido(fila, columna, valor)) {
                    System.err.println("Movimiento inválido según reglas del Sudoku.");
                    continue;
                }

//                Coloco el número y muestro el tablero
                sudoku.colocarNumero(fila, columna, valor);
                sudoku.mostrarTablero();

//                Si el tablero está resuelto felicito al usuario y acaba el programa
                if (sudoku.estaResuelto()) {
                    System.out.println("¡Felicidades! Has completado el Sudoku.");
                    check = true;
                }

            } catch (NumberFormatException e) {
                System.err.println("Debes ingresar números válidos.");

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
