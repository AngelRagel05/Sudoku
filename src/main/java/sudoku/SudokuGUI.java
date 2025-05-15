package sudoku;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI extends JFrame {

    private Sudoku sudoku;
    private JTextField[][] celdas = new JTextField[9][9];
    private JPanel panelTablero;
    private int numeroSeleccionado = 1; // Número seleccionado por defecto
    private JButton[] botonesNumeros = new JButton[9];

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
        setSize(600, 750);
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

                // Bordes con CompoundBorder para bloques 3x3
                int top = (fila % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (fila == 8) ? 3 : 1;
                int right = (col == 8) ? 3 : 1;

                MatteBorder bordeExterior = new MatteBorder(top, left, bottom, right, Color.BLACK);
                MatteBorder bordeInterior = new MatteBorder(1, 1, 1, 1, new Color(200, 200, 200));
                CompoundBorder compoundBorder = new CompoundBorder(bordeExterior, bordeInterior);
                celda.setBorder(compoundBorder);

                final int f = fila;
                final int c = col;

                celda.setEditable(false); // No editable, se usa click para poner número

                celda.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!sudoku.celdasFijas[f][c]) {
                            try {
                                if (sudoku.esMovimientoValido(f, c, numeroSeleccionado)) {
                                    sudoku.colocarNumero(f, c, numeroSeleccionado);
                                    actualizarTablero();
                                } else {
                                    JOptionPane.showMessageDialog(SudokuGUI.this,
                                            "Movimiento inválido para el número seleccionado.",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(SudokuGUI.this,
                                        ex.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                celdas[fila][col] = celda;
                panelTablero.add(celda);
            }
        }

        JPanel panelNumeros = crearSelectorNumeros();

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
        getContentPane().add(panelNumeros, BorderLayout.NORTH);
        getContentPane().add(panelTablero, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearSelectorNumeros() {
        JPanel panelNumeros = new JPanel(new GridLayout(1, 9, 5, 5));
        Font fontBotones = new Font("SansSerif", Font.BOLD, 18);

        for (int i = 1; i <= 9; i++) {
            int num = i;
            JButton btn = new JButton(String.valueOf(num));
            btn.setFont(fontBotones);
            btn.addActionListener(e -> {
                numeroSeleccionado = num;
                actualizarSelectorNumeros();
            });
            botonesNumeros[i - 1] = btn;
            panelNumeros.add(btn);
        }
        actualizarSelectorNumeros(); // Marca el botón 1 por defecto
        return panelNumeros;
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
                    celda.setEditable(false);
                    celda.setForeground(Color.BLACK);

                    // Si el valor es válido para esta posición, verde, si no, amarillo
                    try {
                        if (valor != 0 && sudoku.esMovimientoValido(fila, col, valor)) {
                            celda.setBackground(new Color(180, 255, 180)); // verde claro
                        } else {
                            celda.setBackground(new Color(255, 255, 200)); // amarillo pálido
                        }
                    } catch (Exception e) {
                        celda.setBackground(new Color(255, 255, 200)); // amarillo pálido
                    }

                    celda.setFont(new Font("SansSerif", Font.PLAIN, 20));
                }
            }
        }
    }
}