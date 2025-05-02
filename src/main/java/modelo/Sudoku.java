package modelo;

public class Sudoku {

    private int sudoku[][];

    public Sudoku() {
        int sudo[][] = {
                {0, 6, 0, 1, 0, 4, 0, 5, 0},
                {0, 0, 8, 3, 0, 5, 6, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 1},
                {8, 0, 0, 4, 0, 7, 0, 0, 6},
                {0, 0, 6, 0, 0, 0, 3, 0, 0},
                {7, 0, 0, 9, 0, 1, 0, 0, 4},
                {5, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 7, 2, 0, 6, 9, 0, 0},
                {0, 4, 0, 5, 0, 8, 0, 7, 0}
        };
        this.sudoku = sudo;
    }

    public void resolverSudoku() {
        for (int i = 0; i < this.sudoku.length; i++) {
            for (int j = 0; j < this.sudoku.length; j++) {
                if (this.sudoku[i][j] == 0) {
                    for (int valor = 1; valor <= 9; valor++) {
                        if (validarFila(i, valor)) {

                        }
                    }
                }
            }
        }
    }


    public Boolean validarFila(int i, int valor) {
        for (int j = 0; j < this.sudoku.length; j++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public Boolean validarColumna(int j, int valor) {
        for (int i = 0; i < this.sudoku.length; i++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public int[][] getSudoku() {
        return this.sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
}
