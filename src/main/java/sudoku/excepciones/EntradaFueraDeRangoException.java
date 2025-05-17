package sudoku.excepciones;

public class EntradaFueraDeRangoException extends SudokuException {
    public EntradaFueraDeRangoException(String mensaje) {
        super(mensaje);
    }

    public EntradaFueraDeRangoException() {
        super("La entrada está fuera del rango permitido.");
    }
}