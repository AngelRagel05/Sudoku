package sudoku.excepciones;

public class SudokuException extends RuntimeException {
    public SudokuException(String mensaje) {
        super(mensaje);
    }
}
