package sudoku.excepciones;

public class SudokuException extends RuntimeException {
    public SudokuException(String mensaje) {
        super(mensaje);
    }

    public SudokuException() {
        super("Ha ocurrido un error en el juego Sudoku.");
    }
}