package interfaz;

import javax.swing.*;
import java.awt.*;

public class FormaSudoku extends JFrame {

    public FormaSudoku () {

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

    public static void main(String[] args) {
        new FormaSudoku();
    }
}
