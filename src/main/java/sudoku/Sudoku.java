package sudoku;

import sudoku.excepciones.EntradaFueraDeRangoException;
import sudoku.excepciones.MovimientoInvalidoException;

import java.util.Random;

public class Sudoku {

    public int tablero[][];
    public boolean celdasFijas[][];

    public Sudoku() {
        tablero = new int[9][9]; // Tablero 9x9
        celdasFijas = new boolean[9][9];
    }

    //  METODOS PUBLICOS PRINCIPALES DEL JUEGO
//  Genera un tablero válido según la dificultad
    public void generarTablero(String dificultad) {
        // Limpiar tablero y celdas fijas
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tablero[i][j] = 0;
                celdasFijas[i][j] = false;
            }
        }

        // Generar tablero completo con backtracking
        if (!generarTableroCompleto()) {
            throw new RuntimeException("Error generando tablero completo");
        }

        // Quitar números según dificultad
        int celdasVacias;
        switch (dificultad.toLowerCase()) {
            case "facil":
                celdasVacias = 30;
                break;
            case "medio":
                celdasVacias = 40;
                break;
            case "dificil":
                celdasVacias = 50;
                break;
            default:
                celdasVacias = 30;
        }

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

            int[][] copia = copiarTablero();
            if (!resolver()) {
                // Si no se puede resolver, restauramos la celda
                tablero[fila][col] = backup;
            } else {
                celdasVacias--;
            }

            tablero = copia; // restauramos el tablero original sin solución propuesta
            intentos--;
        }


        // Marcar celdas fijas (las que NO están vacías)
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                celdasFijas[i][j] = tablero[i][j] != 0;
            }
        }
    }

    //  Valida si el movimiento es válido según las reglas Sudoku
    public boolean esMovimientoValido(int fila, int columna, int valor) {

//        Compruebo que el número añadido esté entre el 1 y 9
        if (valor < 1 || valor > 9) {
            throw new EntradaFueraDeRangoException("Valor debe estar entre 1 y 9.");
        }
        if (fila < 0 || fila > 8 || columna < 0 || columna > 8) {
            throw new EntradaFueraDeRangoException("Coordenadas fuera de rango.");
        }

//        Compruebo que no esté en una celda que ya esté fijada
        if (celdasFijas[fila][columna]) {
            throw new MovimientoInvalidoException("No puedes modificar una celda fija.");
        }

//        Guardo el valor
        int valorOriginal = tablero[fila][columna];
        tablero[fila][columna] = 0;

        // Revisar fila
        for (int c = 0; c < 9; c++) {
            if (tablero[fila][c] == valor && c != columna) return false;
        }
        // Revisar columna
        for (int f = 0; f < 9; f++) {
            if (tablero[f][columna] == valor && f != fila) return false;
        }
        // Revisar bloque 3x3
        int startFila = (fila / 3) * 3;
        int startCol = (columna / 3) * 3;
        for (int f = startFila; f < startFila + 3; f++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (tablero[f][c] == valor && !(f == fila && c == columna)) return false;
            }
        }

//        Le vuelvo a dar el valor
        tablero[fila][columna] = valorOriginal;
        return true;
    }

    //  Coloca un número en el tablero si es válido, 0 borra la celda
    public void colocarNumero(int fila, int columna, int valor) {
        if (valor == 0) {
            tablero[fila][columna] = 0;
            return;
        }

        if (!esMovimientoValido(fila, columna, valor)) {
            throw new MovimientoInvalidoException("Movimiento inválido.");
        }
        tablero[fila][columna] = valor;
    }

    //  Verifica si el sudoku está resuelto (no hay celdas vacías y tódo valido)
    public boolean estaResuelto() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int val = tablero[fila][col];
                if (val == 0) return false;
                if (!cumpleReglasSudoku(fila, col, val)) return false;
            }
        }
        return true;
    }

    //  Imprime el tablero en consola (para debug)
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

    public void colocarNumeroSinValidar(int fila, int columna, int valor) {
        tablero[fila][columna] = valor;
    }



    //  METODOS AUXILIARES DE VALIDACION INTERNA
    private boolean cumpleReglasSudoku(int fila, int columna, int valor) {
        int original = tablero[fila][columna];
        tablero[fila][columna] = 0;

        // Validar fila
        for (int c = 0; c < 9; c++) {
            if (tablero[fila][c] == valor) {
                tablero[fila][columna] = original;
                return false;
            }
        }

        // Validar columna
        for (int f = 0; f < 9; f++) {
            if (tablero[f][columna] == valor) {
                tablero[fila][columna] = original;
                return false;
            }
        }

        // Validar bloque 3x3
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


    //  METODOS INTERNOS DEL GENERADOR Y RESOLUCION
//  Genera un tablero completo válido (backtracking)
    private boolean generarTableroCompleto() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    int[] numeros = generarNumerosAleatorios();
                    for (int num : numeros) {
                        if (esMovimientoValido(fila, col, num)) {
                            tablero[fila][col] = num;
                            if (generarTableroCompleto()) {
                                return true;
                            } else {
                                tablero[fila][col] = 0;
                            }
                        }
                    }
                    return false; // ningún número válido, retroceder
                }
            }
        }
        return true; // tablero completo
    }

    //  Metodo para generar numeros random
    private int[] generarNumerosAleatorios() {
        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();

        for (int i = 0; i < numeros.length; i++) {
            int j = rand.nextInt(numeros.length);
            int temp = numeros[i];
            numeros[i] = numeros[j];
            numeros[j] = temp;
        }
        return numeros;
    }

    //  Resuelve el Sudoku
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
                            if (resolverInterno()) {
                                return true;
                            } else {
                                tablero[fila][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}