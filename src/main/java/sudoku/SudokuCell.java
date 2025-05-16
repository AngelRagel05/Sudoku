package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuCell extends JTextField {

    private Integer numeroDefinitivo = null;
    private boolean celdaFija = false;
    private boolean fijaOriginal = false;
    private boolean fijaUsuario = false;

    public SudokuCell(boolean fijaOriginal) {
        this.fijaOriginal = fijaOriginal;
        this.celdaFija = fijaOriginal;
        actualizarColorYEstado();
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setHorizontalAlignment(JTextField.CENTER);
    }

    public boolean esFija() {
        return celdaFija;
    }

    public boolean isFijaUsuario() {
        return fijaUsuario;
    }

    public void setCeldaFija(boolean fijaOriginal, boolean fijaUsuario) {
        this.fijaOriginal = fijaOriginal;
        this.fijaUsuario = fijaUsuario;
        this.celdaFija = fijaOriginal || fijaUsuario;
        actualizarColorYEstado();
    }

    public void fijarPorUsuario() {
        this.fijaUsuario = true;
        this.celdaFija = true;
        actualizarColorYEstado();
    }

    private void actualizarColorYEstado() {
        setEditable(!celdaFija);
        setFocusable(!celdaFija);

        if (fijaOriginal) {
            setBackground(new Color(220, 220, 220)); // gris claro
        } else if (fijaUsuario) {
            setBackground(new Color(180, 255, 180)); // verde claro
        } else {
            setBackground(Color.WHITE);
        }
    }

    public void setNumeroDefinitivo(Integer num) {
        this.numeroDefinitivo = num;
        if (num != null) {
            setText(String.valueOf(num));
        } else {
            setText("");
        }
    }

    public Integer getNumeroDefinitivo() {
        return numeroDefinitivo;
    }
}
