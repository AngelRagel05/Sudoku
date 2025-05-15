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
    private boolean modoNotas = false; // false = poner número definitivo, true = notas

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
                boolean esFija = sudoku.celdasFijas[fila][col];
                SudokuCell celda = new SudokuCell(esFija);

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

                celda.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (celda.esFija()) return; // No modificar fija

                        if (modoNotas) {
                            // Toggle nota
                            celda.toggleNota(numeroSeleccionado);
                            // Si tiene número definitivo, borrarlo
                            celda.setNumeroDefinitivo(null);
                        } else {
                            // Poner número definitivo
                            if (sudoku.esMovimientoValido(f, c, numeroSeleccionado)) {
                                sudoku.colocarNumero(f, c, numeroSeleccionado);
                                celda.setNumeroDefinitivo(numeroSeleccionado);
                                celda.clearNotas();
                                actualizarTablero();
                            } else {
                                JOptionPane.showMessageDialog(SudokuGUI.this,
                                        "Movimiento inválido para el número seleccionado.",
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

        JButton btnModoNotas = new JButton("Modo Notas: OFF");
        btnModoNotas.addActionListener(e -> {
            modoNotas = !modoNotas;
            btnModoNotas.setText(modoNotas ? "Modo Notas: ON" : "Modo Notas: OFF");
        });

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
        panelBotones.add(btnModoNotas);
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
        actualizarSelectorNumeros(); // Marca botón 1 por defecto
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
                SudokuCell celda = celdas[fila][col];

                // Sincronizar número definitivo desde sudoku.tablero (por si se reinicia)
                int valor = sudoku.tablero[fila][col];
                if (valor != 0) {
                    celda.setNumeroDefinitivo(valor);
                    celda.clearNotas();
                } else {
                    celda.setNumeroDefinitivo(null);
                    // No borramos notas para no perderlas al actualizar
                }
            }
        }
    }
}