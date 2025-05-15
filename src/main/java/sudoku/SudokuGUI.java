package sudoku;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI extends JFrame {

    private Sudoku sudoku;
    private JTextField celdas[][] = new JTextField[9][9];
    private JPanel panelTablero;

    public SudokuGUI() {
        sudoku = new Sudoku();
        String opciones[] = {"Fácil", "Medio", "Difícil"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Selecciona la dificultad:",
                "Dificultad del Sudoku",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        String dificultad;
        switch (seleccion) {
            case 1 -> dificultad = "medio";
            case 2 -> dificultad = "dificil";
            default -> dificultad = "facil";
        }

        sudoku.generarTablero(dificultad);

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

//                Bordes en los cuadrados 3x3
                int top = (fila % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (fila == 8) ? 3 : 1;
                int right = (col == 8) ? 3 : 1;

                Color bordeGrueso = Color.BLACK;
                Color bordeDelicado = new Color(200, 200, 200);

                Border bordeExterior = BorderFactory.createMatteBorder(top, left, bottom, right, bordeGrueso);
                Border bordeInterior = BorderFactory.createMatteBorder(1, 1, 1, 1, bordeDelicado);

                CompoundBorder compoundBorder = new CompoundBorder(bordeExterior, bordeInterior);

                celda.setBorder(compoundBorder);

                final int f = fila;
                final int c = col;

                celda.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        String texto = celda.getText().trim();

                        if (texto.isEmpty()) {
                            sudoku.colocarNumero(f, c, 0);
                            actualizarTablero();
                            return;
                        }

                        try {
                            int num = Integer.parseInt(texto);
                            if (num < 1 || num > 9) {
                                throw new NumberFormatException();
                            }

                            if (!sudoku.esMovimientoValido(f, c, num)) {
                                JOptionPane.showMessageDialog(SudokuGUI.this,
                                        "Movimiento inválido: no puedes poner " + num +
                                                " en fila " + (f + 1) + ", columna " + (c + 1),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                celda.setText(""); // limpiar celda
                                sudoku.colocarNumero(f, c, 0);
                            } else {
                                sudoku.colocarNumero(f, c, num);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(SudokuGUI.this,
                                    "Introduce un número del 1 al 9.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            celda.setText("");
                            sudoku.colocarNumero(f, c, 0);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(SudokuGUI.this,
                                    ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            celda.setText("");
                            sudoku.colocarNumero(f, c, 0);
                        }

                        actualizarTablero();
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
            String[] opciones = {"Fácil", "Medio", "Difícil"};
            int seleccion = JOptionPane.showOptionDialog(
                    this,
                    "Selecciona la dificultad:",
                    "Reiniciar Sudoku",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            String dificultad;
            switch (seleccion) {
                case 1 -> dificultad = "medio";
                case 2 -> dificultad = "dificil";
                default -> dificultad = "facil";
            }

            sudoku.generarTablero(dificultad);
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

                // Establecer texto
                celda.setText(valor != 0 ? String.valueOf(valor) : "");

                if (sudoku.celdasFijas[fila][col]) {
                    // Celdas fijas (no modificables)
                    celda.setEditable(false);
                    celda.setForeground(Color.BLACK);
                    celda.setBackground(new Color(220, 220, 220)); // gris claro
                    celda.setFont(new Font("SansSerif", Font.BOLD, 20));
                } else {
                    // Celdas libres (modificables)
                    celda.setEditable(true);
                    celda.setForeground(Color.BLACK);
                    celda.setBackground(new Color(255, 255, 200)); // amarillo pálido
                    celda.setFont(new Font("SansSerif", Font.PLAIN, 20));
                }
            }
        }
    }

}