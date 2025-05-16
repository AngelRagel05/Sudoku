package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SudokuCell extends JTextField {

    private Integer numeroDefinitivo = null;
    private Set<Integer> notas = new HashSet<>();
    private boolean celdaFija = false;
    private boolean mostrarError = false;
    private boolean mostrarCorrecto = false;

    public SudokuCell(boolean fija) {
        this.celdaFija = fija;
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setHorizontalAlignment(JTextField.CENTER);
        setFocusable(true);
    }

    public boolean esFija() {
        return celdaFija;
    }

    public void setCeldaFija(boolean fija) {
        this.celdaFija = fija;
        setEditable(!fija);
        repaint();
    }

    public void setNumeroDefinitivo(Integer num) {
        this.numeroDefinitivo = num;
        if (num != null) {
            setText(String.valueOf(num));
            notas.clear();
        } else {
            setText("");
        }
    }

    public Integer getNumeroDefinitivo() {
        return numeroDefinitivo;
    }

    public void toggleNota(int nota) {
        if (numeroDefinitivo != null) return;
        if (notas.contains(nota)) {
            notas.remove(nota);
        } else {
            notas.add(nota);
        }
        repaint();
    }

    public Set<Integer> getNotas() {
        return new HashSet<>(notas);
    }

    public void clearNotas() {
        notas.clear();
        repaint();
    }

    public void setMostrarError(boolean mostrar) {
        this.mostrarError = mostrar;
        repaint();
    }

    public void setMostrarCorrecto(boolean mostrar) {
        this.mostrarCorrecto = mostrar;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (celdaFija) {
            setBackground(new Color(220, 220, 220));
        } else if (mostrarCorrecto) {
            setBackground(new Color(180, 255, 180));
        } else if (mostrarError) {
            setBackground(new Color(255, 180, 180));
        } else {
            setBackground(Color.WHITE);
        }
        super.paintComponent(g);
    }
}
