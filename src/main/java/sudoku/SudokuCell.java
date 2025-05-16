package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SudokuCell extends JTextField {

    private Integer numeroDefinitivo = null;
    private Set<Integer> notas = new HashSet<>();
    private boolean celdaFija = false;

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

    public void clearNotas() {
        notas.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (numeroDefinitivo != null) return;

        if (!notas.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            int size = getWidth() / 3;

            for (int n = 1; n <= 9; n++) {
                if (notas.contains(n)) {
                    int x = ((n - 1) % 3) * size + 5;
                    int y = ((n - 1) / 3) * size + 15;
                    g2.drawString(String.valueOf(n), x, y);
                }
            }
        }
    }
}
