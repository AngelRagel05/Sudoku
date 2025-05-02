package interfaz;

import javax.swing.*;
import java.awt.*;

public class FormaSudoku1 extends JFrame {

    private TableroSudoku tableroSudoku;

    public void iniciarComponentes() {
        tableroSudoku = new TableroSudoku();
        tableroSudoku.setTxtAltura(36);
        tableroSudoku.setTxtAncho(36);
        tableroSudoku.setTxtTamañoLetra(27);

        tableroSudoku.setPanelFondo(Color.BLUE);
        tableroSudoku.setTxtFondo1(Color.BLACK);
        tableroSudoku.setTxtColorLetra1(Color.BLACK);

        tableroSudoku.setTxtFondo2(Color.BLACK);
        tableroSudoku.setTxtColorLetra2(Color.WHITE);

        tableroSudoku.setTxtFondo3(Color.BLACK);
        tableroSudoku.setTxtColorLetra3(Color.WHITE);
    }

    public FormaSudoku1() {

//        Titulo
        setTitle("Forma Sudoku");

//        Tamaño pantalla
        setSize(520, 420);

//        Color de fondo negro
        getContentPane().setBackground(Color.BLACK);

//        Que se cierre al darle al Close o a la cruz
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Que se centre en el centro de la pantalla
        setLocationRelativeTo(null);

//        Que se vea
        setVisible(true);
    }
}
