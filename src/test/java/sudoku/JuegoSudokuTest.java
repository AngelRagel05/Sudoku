package sudoku;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class JuegoSudokuTest {

    @Test
    public void testIniciar_FacilYSalir() {
        String input = "facil\nsalir\n";
        Scanner scSimulado = new Scanner(input);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream printSimulado = new PrintStream(salidaCapturada);

        JuegoSudoku juego = new JuegoSudoku(scSimulado, printSimulado);
        juego.iniciar();

        String salida = salidaCapturada.toString();

        assertTrue(salida.contains("=== Juego Sudoku ==="));
        assertTrue(salida.contains("Tablero Generado:"));
        assertTrue(salida.contains("Juego terminado."));
    }
}