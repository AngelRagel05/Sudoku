package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SudokuCell extends JPanel {

    private Integer numeroDefinitivo = null;
    private Set<Integer> notas = new HashSet<>();
    private boolean celdaFija;

    public SudokuCell(boolean fija) {
        this.celdaFija = fija;
        setPreferredSize(new Dimension(50, 50));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public boolean esFija() {
        return celdaFija;
    }

    public void setNumeroDefinitivo(Integer num) {
        this.numeroDefinitivo = num;
        if (num != null) notas.clear(); // Limpiar notas si hay número definitivo
        repaint();
    }

    public Integer getNumeroDefinitivo() {
        return numeroDefinitivo;
    }

    public void toggleNota(int nota) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo según si es fija o editable
        if (celdaFija) {
            setBackground(new Color(220, 220, 220));
        } else {
            setBackground(Color.WHITE);
        }

        // Dibuja número definitivo grande si hay
        if (numeroDefinitivo != null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 24));
            FontMetrics fm = g.getFontMetrics();
            String texto = numeroDefinitivo.toString();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;
            g.drawString(texto, x, y);
        } else {
            // Dibuja notas pequeñas en grilla 3x3
            g.setColor(Color.GRAY);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            int cellW = getWidth() / 3;
            int cellH = getHeight() / 3;

            for (int i = 1; i <= 9; i++) {
                int row = (i - 1) / 3;
                int col = (i - 1) % 3;
                int x = col * cellW + 4;
                int y = row * cellH + cellH - 4;
                if (notas.contains(i)) {
                    g.drawString(String.valueOf(i), x, y);
                }
            }
        }
    }
}
