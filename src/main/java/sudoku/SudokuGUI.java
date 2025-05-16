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

        String dificultad = switch (seleccion) {
            case 1 -> "medio";
            case 2 -> "dificil";
            default -> "facil";
        };

        sudoku.generarTablero(dificultad);

        setTitle("Sudoku - Juego");
        setSize(650, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        configurarTecladoNotas();
        actualizarTablero();
    }

    private void configurarTecladoNotas() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("N"), "toggleNotas");

        getRootPane().getActionMap().put("toggleNotas", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoNotas = !modoNotas;
                String estado = modoNotas ? "activado" : "desactivado";
                JOptionPane.showMessageDialog(SudokuGUI.this,
                        "Modo notas " + estado + ".", "Notas", JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
                        char tecla = e.getKeyChar();
                        if (!Character.isDigit(tecla) || tecla == '0') return;

                        int num = Character.getNumericValue(tecla);

                        if (modoNotas) {
                            celda.toggleNota(num);
                        } else {
                            try {
                                sudoku.colocarNumero(f, c, num);
                                celda.setNumeroDefinitivo(num);
                                celda.setEditable(false);
                                sudoku.celdasFijas[f][c] = true;
                            } catch (Exception ex) {
                                celda.setNumeroDefinitivo(null);
                            }
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

    private void actualizarTablero() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell celda = celdas[fila][col];
                int valor = sudoku.tablero[fila][col];

                if (valor != 0) {
                    celda.setNumeroDefinitivo(valor);
                    celda.clearNotas();

                    if (sudoku.celdasFijas[fila][col]) {
                        celda.setEditable(false);
                    } else {
                        boolean valido;
                        try {
                            int original = sudoku.tablero[fila][col];
                            sudoku.tablero[fila][col] = 0;
                            valido = sudoku.esMovimientoValido(fila, col, valor);
                            sudoku.tablero[fila][col] = original;
                        } catch (Exception e) {
                            valido = false;
                        }

                        if (valido) {
                            celda.setNumeroDefinitivo(valor);
                            celda.setEditable(false);
                            sudoku.celdasFijas[fila][col] = true;
                        } else {
                            celda.setNumeroDefinitivo(valor);
                            celda.setEditable(true);
                        }
                    }
                } else {
                    celda.setNumeroDefinitivo(null);
                    celda.clearNotas();

                    if (sudoku.celdasFijas[fila][col]) {
                        celda.setText("");
                        celda.setEditable(false);
                    } else {
                        celda.setEditable(true);
                    }
                }
            }
        }
    }
}
