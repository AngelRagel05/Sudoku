package sudoku;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI extends JFrame {

    private Sudoku sudoku;
    private JTextField celdas[][] = new JTextField[9][9];
    private JPanel panelTablero;

    public SudokuGUI() {
        sudoku = new Sudoku();
        sudoku.generarTablero("facil");

        setTitle("Sudoku - Juego");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        actualizarTablero();
    }

    private void initComponents() {
        panelTablero = new JPanel(new GridLayout(9, 9));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField celda = new JTextField();
                celda.setHorizontalAlignment(JTextField.CENTER);
                celda.setFont(font);
                celda.setBorder(new LineBorder(Color.BLACK, 1));

                final int f = fila;
                final int c = col;

                celda.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char ch = e.getKeyChar();

                        if (ch < '1' || ch > '9') {
                            e.consume();
                            return;
                        }

                        int num = ch - '0';

                        try {
                            if (!sudoku.esMovimientoValido(f, c, num)) {
                                e.consume();
                                JOptionPane.showMessageDialog(SudokuGUI.this,
                                        "Movimiento inválido: no puedes poner " + num +
                                                " en fila " + (f + 1) + ", columna " + (c + 1),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);

                            } else {
                                sudoku.colocarNumero(f, c, num);
                                actualizarTablero();
                            }

                        } catch (Exception ex) {
                            e.consume();
                            JOptionPane.showMessageDialog(SudokuGUI.this,
                                    ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (celda.getText().isEmpty()) {
                            sudoku.colocarNumero(f, c, 0);
                        }
                    }
                });

                celdas[fila][col] = celda;
                panelTablero.add(celda);
            }
        }

        JButton btnCheck = new JButton("Comprobar solución");
        btnCheck.addActionListener(e -> {
            if (sudoku.estaResuelto()) {
                JOptionPane.showMessageDialog(this, "¡Felicidades! Sudoku resuelto correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "El Sudoku no está completo o tiene errores.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnReiniciar = new JButton("Reiniciar tablero");
        btnReiniciar.addActionListener(e -> {
            sudoku.generarTablero("facil"); // Puedes poner diálogo para elegir dificultad
            actualizarTablero();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCheck);
        panelBotones.add(btnReiniciar);

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(panelTablero, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarTablero() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = sudoku.tablero[fila][col];
                JTextField celda = celdas[fila][col];
                if (valor != 0) {
                    celda.setText(String.valueOf(valor));
                } else {
                    celda.setText("");
                }

                if (sudoku.celdasFijas[fila][col]) {
                    celda.setEditable(false);
                    celda.setForeground(Color.DARK_GRAY);
                    celda.setBackground(new Color(230, 230, 230));
                } else {
                    celda.setEditable(true);
                    celda.setForeground(Color.BLACK);
                    celda.setBackground(Color.WHITE);
                }
            }
        }
    }
}