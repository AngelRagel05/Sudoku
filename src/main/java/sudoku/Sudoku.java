package sudoku;

// Importación de excepciones personalizadas
import sudoku.excepciones.EntradaFueraDeRangoException;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.interfaces.ISudoku;

import java.util.Random;

public class Sudoku implements ISudoku {
    public int[][] tablero;           // Matriz de 9x9 que representa el tablero
    public boolean[][] celdasFijas;   // Matriz que indica qué celdas no se pueden modificar

    // Constructor: inicializa el tablero vacío
    public Sudoku() {
        tablero = new int[9][9];
        celdasFijas = new boolean[9][9];
    }

    // Genera un tablero completo y luego elimina celdas según la dificultad
    public void generarTablero(String dificultad) {
        // Limpia tablero y celdas fijas
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tablero[i][j] = 0;
                celdasFijas[i][j] = false;
            }
        }

        // Genera un tablero Sudoku válido y completo
        if (!generarTableroCompleto()) {
            throw new RuntimeException("Error generando tablero completo");
        }

        // Define cuántas celdas eliminar según la dificultad
        int celdasVacias = switch (dificultad.toLowerCase()) {
            case "medio" -> 40;
            case "dificil" -> 50;
            default -> 30; // Fácil por defecto
        };

        Random rand = new Random();
        int intentos = 1000;

        // Elimina celdas aleatoriamente, verificando que el tablero siga resoluble
        while (celdasVacias > 0 && intentos > 0) {
            int fila = rand.nextInt(9);
            int col = rand.nextInt(9);

            // Ya está vacía, intenta otra
            if (tablero[fila][col] == 0) {
                intentos--;
                continue;
            }

            int backup = tablero[fila][col];
            tablero[fila][col] = 0;

            Sudoku temp = new Sudoku();
            temp.tablero = copiarTablero();

            if (temp.resolver()) {
                celdasVacias--; // Solo se elimina si sigue resolviéndose
            } else {
                tablero[fila][col] = backup;
            }

            intentos--;
        }

        // Marca como fijas las celdas que no fueron vaciadas
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                celdasFijas[i][j] = tablero[i][j] != 0;
            }
        }
    }

    // Verifica si un movimiento es válido según las reglas de Sudoku
    public boolean esMovimientoValido(int fila, int columna, int valor) {
        if (valor < 1 || valor > 9)
            throw new EntradaFueraDeRangoException("Valor debe estar entre 1 y 9.");
        if (fila < 0 || fila > 8 || columna < 0 || columna > 8)
            throw new EntradaFueraDeRangoException("Coordenadas fuera de rango.");
        if (celdasFijas[fila][columna])
            throw new MovimientoInvalidoException("No puedes modificar una celda fija.");

        return cumpleReglasSudoku(fila, columna, valor);
    }

    // Coloca un número en el tablero (o lo borra si valor = 0)
    public void colocarNumero(int fila, int columna, int valor) {
        if (valor == 0) {
            tablero[fila][columna] = 0;
            return;
        }

        if (!esMovimientoValido(fila, columna, valor))
            throw new MovimientoInvalidoException("Movimiento inválido.");

        tablero[fila][columna] = valor;
    }

    // Verifica si el tablero está completamente resuelto y es válido
    public boolean estaResuelto() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int val = tablero[fila][col];
                if (val == 0 || !cumpleReglasSudoku(fila, col, val)) return false;
            }
        }
        return true;
    }

    // Muestra el tablero en consola
    public void mostrarTablero() {
        for (int fila = 0; fila < 9; fila++) {
            if (fila % 3 == 0) System.out.println("+-------+-------+-------+");
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0) System.out.print("| ");
                System.out.print(tablero[fila][col] == 0 ? ". " : tablero[fila][col] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    // Verifica si colocar un valor en una celda respeta las reglas del Sudoku
    private boolean cumpleReglasSudoku(int fila, int columna, int valor) {
        int original = tablero[fila][columna];
        tablero[fila][columna] = 0; // Temporalmente vacía para evitar conflicto consigo misma

        // Revisa fila
        for (int c = 0; c < 9; c++) {
            if (tablero[fila][c] == valor) {
                tablero[fila][columna] = original;
                return false;
            }
        }

        // Revisa columna
        for (int f = 0; f < 9; f++) {
            if (tablero[f][columna] == valor) {
                tablero[fila][columna] = original;
                return false;
            }
        }

        // Revisa subcuadro de 3x3
        int startFila = (fila / 3) * 3;
        int startCol = (columna / 3) * 3;
        for (int f = startFila; f < startFila + 3; f++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (tablero[f][c] == valor) {
                    tablero[fila][columna] = original;
                    return false;
                }
            }
        }

        tablero[fila][columna] = original;
        return true;
    }

    // Copia profunda del tablero actual
    private int[][] copiarTablero() {
        int[][] copia = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(tablero[i], 0, copia[i], 0, 9);
        }
        return copia;
    }

    // Genera un tablero completamente lleno usando backtracking
    private boolean generarTableroCompleto() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    int[] numeros = generarNumerosAleatorios();
                    for (int num : numeros) {
                        if (cumpleReglasSudoku(fila, col, num)) {
                            tablero[fila][col] = num;
                            if (generarTableroCompleto()) return true;
                            tablero[fila][col] = 0; // backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true; // tablero completamente lleno
    }

    // Devuelve un array con los números del 1 al 9 en orden aleatorio
    private int[] generarNumerosAleatorios() {
        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 0; i < numeros.length; i++) {
            int j = rand.nextInt(numeros.length);
            int tmp = numeros[i];
            numeros[i] = numeros[j];
            numeros[j] = tmp;
        }
        return numeros;
    }

    // Punto de entrada para resolver el tablero actual
    public boolean resolver() {
        return resolverInterno();
    }

    // Resuelve el tablero usando backtracking
    private boolean resolverInterno() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (cumpleReglasSudoku(fila, col, num)) {
                            tablero[fila][col] = num;
                            if (resolverInterno()) return true;
                            tablero[fila][col] = 0; // backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
