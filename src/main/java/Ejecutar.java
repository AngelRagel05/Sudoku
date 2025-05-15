import sudoku.JuegoSudoku;
import sudoku.SudokuGUI;


public class Ejecutar {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI();
            gui.setVisible(true);
        });
//        JuegoSudoku juego = new JuegoSudoku();
//        juego.iniciar();
    }
}
