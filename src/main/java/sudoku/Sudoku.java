package sudoku;

import sudoku.excepciones.EntradaFueraDeRangoException;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.interfaces.ISudoku;

import java.util.Random;

public class Sudoku implements ISudoku {
    public int[][] tablero;
    public boolean[][] celdasFijas;

    public Sudoku() {
        tablero = new int[9][9];
        celdasFijas = new boolean[9][9];
    }

    public void generarTablero(String dificultad) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tablero[i][j] = 0;
                celdasFijas[i][j] = false;
            }
        }

        if (!generarTableroCompleto()) {
            throw new RuntimeException("Error generando tablero completo");
        }

        int celdasVacias = switch (dificultad.toLowerCase()) {
            case "medio" -> 40;
            case "dificil" -> 50;
            default -> 30;
        };

        Random rand = new Random();
        int intentos = 1000;

        while (celdasVacias > 0 && intentos > 0) {
            int fila = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (tablero[fila][col] == 0) {
                intentos--;
                continue;
            }

            int backup = tablero[fila][col];
            tablero[fila][col] = 0;

            Sudoku temp = new Sudoku();
            temp.tablero = copiarTablero();

            if (temp.resolver()) {
                celdasVacias--;
            } else {
                tablero[fila][col] = backup;
            }

            intentos--;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                celdasFijas[i][j] = tablero[i][j] != 0;
            }
        }
    }

    public boolean esMovimientoValido(int fila, int columna, int valor) {
        if (valor < 1 || valor > 9)
            throw new EntradaFueraDeRangoException("Valor debe estar entre 1 y 9.");
        if (fila < 0 || fila > 8 || columna < 0 || columna > 8)
            throw new EntradaFueraDeRangoException("Coordenadas fuera de rango.");
        if (celdasFijas[fila][columna])
            throw new MovimientoInvalidoException("No puedes modificar una celda fija.");

        return cumpleReglasSudoku(fila, columna, valor);
    }

    public void colocarNumero(int fila, int columna, int valor) {
        if (valor == 0) {
            tablero[fila][columna] = 0;
            return;
        }

        if (!esMovimientoValido(fila, columna, valor))
            throw new MovimientoInvalidoException("Movimiento inválido.");

        tablero[fila][columna] = valor;
    }

    public boolean estaResuelto() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int val = tablero[fila][col];
                if (val == 0 || !cumpleReglasSudoku(fila, col, val)) return false;
            }
        }
        return true;
    }

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

    private boolean cumpleReglasSudoku(int fila, int columna, int valor) {
        int original = tablero[fila][columna];
        tablero[fila][columna] = 0;

        for (int c = 0; c < 9; c++) {
            if (tablero[fila][c] == valor) {
                tablero[fila][columna] = original;
                return false;
            }
        }

        for (int f = 0; f < 9; f++) {
            if (tablero[f][columna] == valor) {
                tablero[fila][columna] = original;
                return false;
            }
        }

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

    private int[][] copiarTablero() {
        int[][] copia = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(tablero[i], 0, copia[i], 0, 9);
        }
        return copia;
    }

    private boolean generarTableroCompleto() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    int[] numeros = generarNumerosAleatorios();
                    for (int num : numeros) {
                        if (cumpleReglasSudoku(fila, col, num)) {
                            tablero[fila][col] = num;
                            if (generarTableroCompleto()) return true;
                            tablero[fila][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

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

    public boolean resolver() {
        return resolverInterno();
    }

    private boolean resolverInterno() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (cumpleReglasSudoku(fila, col, num)) {
                            tablero[fila][col] = num;
                            if (resolverInterno()) return true;
                            tablero[fila][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
