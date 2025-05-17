package sudoku.interfaces;

public interface ISudokuCell {
    void setNumeroDefinitivo(Integer num);
    Integer getNumeroDefinitivo();
    void setCeldaFija(boolean fijaOriginal, boolean fijaUsuario);
    boolean isFijaUsuario();
}
