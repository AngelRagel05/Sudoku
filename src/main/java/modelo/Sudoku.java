package modelo;

public class Sudoku {

//    Atributos
    private int sudoku[][];

//    Constructor
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

//    Metodo para resolver sudoku
    public boolean resolverSudoku() {

//        Recorro el tablero
        for (int i = 0; i < this.sudoku.length; i++) {
            for (int j = 0; j < this.sudoku.length; j++) {

//                Si la posición en la que esta es un cero (sin valor / valor por defecto)
                if (this.sudoku[i][j] == 0) {

//                    Valida la fila, columna y el uadrante
                    for (int valor = 1; valor <= 9; valor++) {

//                        Si los tres metodos me retornan un true añado el numero ya que es posible que ese numero sea el correcto
                        if (validarFila(i, valor) && validarColumna(j, valor) && validarCuadrante(i, j ,valor)) {
                            sudoku[i][j] = valor;

//                            Aqui uso recursividad para validar la entrada del número, si retorna false es numero no es correcto, si retorna true lo es.
//                            Vuelvo a llamar al metodo para volver a hacer lo mismo (Backtraking)
                            if (resolverSudoku()) return true;
                            sudoku[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

//    Validar Cuadrante
    public boolean validarCuadrante(int i, int j, int valor) {
        int posI = subCuadranteActual(i);
        int posJ = subCuadranteActual(j);

        for (int k = posI-3; k < posI; k++) {
            for (int l = posJ-3; l < posJ; l++) {
                if (sudoku[k][l] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    public int subCuadranteActual (int pos) {
        if (pos <= 2) return 3;
        else if (pos <= 5) return 6;
        else return 9;
    }

//    Validar fila
    public Boolean validarFila(int i, int valor) {
        for (int j = 0; j < this.sudoku.length; j++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

//    Validar Columna
    public Boolean validarColumna(int j, int valor) {
        for (int i = 0; i < this.sudoku.length; i++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public void mostrarSudoku() {
        resolverSudoku();
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                System.out.print(sudoku[i][j]);
                if (!(j == sudoku[0].length-1)) System.out.print(" - ");
            }
            System.out.println();
        }
    }

//    Get y Set
    public int[][] getSudoku() {
        return this.sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
}