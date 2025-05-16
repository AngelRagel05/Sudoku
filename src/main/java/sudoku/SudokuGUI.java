package sudoku;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI extends JFrame {

    private Sudoku sudoku;
    private SudokuCell[][] celdas = new SudokuCell[9][9];
    private JPanel panelTablero;
    private int numeroSeleccionado = 1;
    private JButton[] botonesNumeros = new JButton[9];
    private boolean modoNotas = false;

    public SudokuGUI() {
        sudoku = new Sudoku();
        String[] opciones = {"Fácil", "Medio", "Difícil"};
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
        setSize(650, 800);
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
                SudokuCell celda = new SudokuCell(sudoku.celdasFijas[fila][col]);
                celda.setHorizontalAlignment(JTextField.CENTER);
                celda.setFont(font);
                celda.setEditable(!sudoku.celdasFijas[fila][col]);

                // Bordes para bloques 3x3
                int top = (fila % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (fila == 8) ? 3 : 1;
                int right = (col == 8) ? 3 : 1;

                MatteBorder bordeExterior = new MatteBorder(top, left, bottom, right, Color.BLACK);
                MatteBorder bordeInterior = new MatteBorder(1, 1, 1, 1, new Color(200, 200, 200));
                celda.setBorder(new CompoundBorder(bordeExterior, bordeInterior));

                final int f = fila;
                final int c = col;

                celda.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        String texto = celda.getText().trim();

                        if (texto.isEmpty()) {
                            sudoku.colocarNumeroSinValidar(f, c, 0);
                            actualizarTablero();
                            return;
                        }

                        if (texto.length() > 1 || !Character.isDigit(texto.charAt(0))) {
                            celda.setText("");
                            sudoku.colocarNumeroSinValidar(f, c, 0);
                            actualizarTablero();
                            return;
                        }

                        int num = Character.getNumericValue(texto.charAt(0));
                        if (num >= 1 && num <= 9) {
                            sudoku.colocarNumeroSinValidar(f, c, num);
                        } else {
                            celda.setText("");
                            sudoku.colocarNumeroSinValidar(f, c, 0);
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

            String dificultad = switch (seleccion) {
                case 1 -> "medio";
                case 2 -> "dificil";
                default -> "facil";
            };

            sudoku.generarTablero(dificultad);

            // Actualizar el estado de celdas fijas en la GUI
            for (int fila = 0; fila < 9; fila++) {
                for (int col = 0; col < 9; col++) {
                    celdas[fila][col].setCeldaFija(sudoku.celdasFijas[fila][col]);
                }
            }

            actualizarTablero();
        });

        JButton btnResolver = new JButton("Resolver Sudoku");
        btnResolver.addActionListener(e -> {
            if (sudoku.resolver()) {
                actualizarTablero();
                JOptionPane.showMessageDialog(this, "Sudoku resuelto correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo resolver el Sudoku.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCheck);
        panelBotones.add(btnReiniciar);
        panelBotones.add(btnResolver);

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(panelTablero, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarSelectorNumeros() {
        for (int i = 0; i < botonesNumeros.length; i++) {
            if (i == numeroSeleccionado - 1) {
                botonesNumeros[i].setBackground(Color.CYAN);
            } else {
                botonesNumeros[i].setBackground(null);
            }
        }
    }

    private void actualizarTablero() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell celda = celdas[fila][col];
                int valor = sudoku.tablero[fila][col];

                // Mostrar número si existe
                if (valor != 0) {
                    celda.setNumeroDefinitivo(valor);
                    celda.clearNotas();

                    if (sudoku.celdasFijas[fila][col]) {
                        // Fija: deshabilitada y gris
                        celda.setEnabled(false);
                        celda.setBackground(new Color(220, 220, 220));
                    } else {
                        // Editable: comprobar validez
                        boolean valido = false;
                        try {
                            int original = sudoku.tablero[fila][col];
                            sudoku.tablero[fila][col] = 0;
                            valido = sudoku.esMovimientoValido(fila, col, valor);
                            sudoku.tablero[fila][col] = original;
                        } catch (Exception e) {
                            valido = false;
                        }

                        celda.setEnabled(true);
                        celda.setBackground(valido
                                ? new Color(180, 255, 180)  // verde si válido
                                : new Color(255, 180, 180)); // rojo si inválido
                    }
                } else {
                    // Celda vacía
                    celda.setNumeroDefinitivo(null);
                    celda.clearNotas();

                    if (sudoku.celdasFijas[fila][col]) {
                        celda.setText(""); // asegurarse que no hay basura
                        celda.setEnabled(false);
                        celda.setBackground(new Color(220, 220, 220));
                    } else {
                        celda.setEnabled(true);
                        celda.setBackground(Color.WHITE);
                    }
                }
            }
        }
    }
}
