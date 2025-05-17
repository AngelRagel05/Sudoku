package sudoku.interfaces;

public interface ISudoku {
    void generarTablero(String dificultad);
    boolean esMovimientoValido(int fila, int columna, int valor);
    void colocarNumero(int fila, int columna, int valor);
    boolean estaResuelto();
    void mostrarTablero();
    boolean resolver();
}
