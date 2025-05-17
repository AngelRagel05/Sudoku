package sudoku.excepciones;

public class MovimientoInvalidoException extends SudokuException {
    public MovimientoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public MovimientoInvalidoException() {
        super("El movimiento no cumple las reglas del Sudoku.");
    }
}