package sudoku.excepciones;

public class EntradaFueraDeRangoException extends SudokuException{
    public EntradaFueraDeRangoException(String mensaje) {
        super(mensaje);
    }
}
