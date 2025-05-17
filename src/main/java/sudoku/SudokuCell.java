package sudoku;

import sudoku.interfaces.ISudokuCell;

import javax.swing.*;
import java.awt.*;

public class SudokuCell extends JTextField implements ISudokuCell {

    private Integer numeroDefinitivo = null;
    private boolean fijaOriginal = false;
    private boolean fijaUsuario = false;

    public SudokuCell(boolean fijaOriginal) {
        this.fijaOriginal = fijaOriginal;
        actualizarColorYEstado();
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setHorizontalAlignment(JTextField.CENTER);
    }

    public boolean isFijaUsuario() {
        return fijaUsuario;
    }

    public void setCeldaFija(boolean fijaOriginal, boolean fijaUsuario) {
        this.fijaOriginal = fijaOriginal;
        this.fijaUsuario = fijaUsuario;
        actualizarColorYEstado();
    }

    public void marcarComoFijaPorUsuario() {
        this.fijaUsuario = true;
        actualizarColorYEstado();
    }

    private void actualizarColorYEstado() {
        boolean esFija = fijaOriginal || fijaUsuario;
        setEditable(!esFija);
        setFocusable(!esFija);

        if (fijaOriginal) {
            setBackground(new Color(220, 220, 220));
        } else if (fijaUsuario) {
            setBackground(new Color(180, 255, 180));
        } else {
            setBackground(Color.WHITE);
        }
    }

    public void setNumeroDefinitivo(Integer num) {
        this.numeroDefinitivo = num;
        setText(num != null ? String.valueOf(num) : "");
    }

    public Integer getNumeroDefinitivo() {
        return numeroDefinitivo;
    }
}
