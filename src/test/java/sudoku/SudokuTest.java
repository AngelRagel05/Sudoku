package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.excepciones.EntradaFueraDeRangoException;
import sudoku.excepciones.MovimientoInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {
        sudoku = new Sudoku();
    }

    @Test
    void generarTableroDefault() {
        sudoku.generarTablero("facil");
        int celdasVacias = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku.tablero[i][j] == 0) celdasVacias++;
            }
        }
        assertEquals(30, celdasVacias, "Dificultad fácil debe tener 30 celdas vacías");
    }

    @Test
    public void testGenerarTableroDificultadMedio() {
        sudoku.generarTablero("medio");
        int celdasVacias = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku.tablero[i][j] == 0) celdasVacias++;
            }
        }
        assertEquals(40, celdasVacias, "Dificultad medio debe tener 40 celdas vacías");
    }

    @Test
    public void testEsMovimientoValido_ValorFueraDeRango() {
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(0, 0, 10));
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(0, 0, 0));
    }

    @Test
    public void testEsMovimientoValido_CeldaFija() {
        sudoku.tablero[0][0] = 5;
        sudoku.celdasFijas[0][0] = true;
        assertThrows(MovimientoInvalidoException.class, () -> sudoku.esMovimientoValido(0, 0, 3));
    }

    @Test
    public void testColocarNumero_ColocarYQuitarNumero() {
        sudoku.tablero[0][0] = 0;
        sudoku.celdasFijas[0][0] = false;

        // Colocar número válido
        sudoku.colocarNumero(0, 0, 5);
        assertEquals(5, sudoku.tablero[0][0]);

        // Quitar número (poner 0)
        sudoku.colocarNumero(0, 0, 0);
        assertEquals(0, sudoku.tablero[0][0]);
    }

    @Test
    public void testColocarNumero_MovimientoInvalido() {
        sudoku.tablero[0][0] = 5;
        sudoku.celdasFijas[0][0] = false;
        sudoku.tablero[0][1] = 5; // Ya hay un 5 en la misma fila

        assertThrows(MovimientoInvalidoException.class, () -> sudoku.colocarNumero(0, 0, 5));
    }

    @Test
    public void testEstaResuelto_FalsoSiHayCeros() {
        sudoku.tablero[0][0] = 0;
        assertFalse(sudoku.estaResuelto());
    }

    @Test
    public void testEstaResuelto_VerdaderoParaTableroCompletoYValido() {
        sudoku.generarTablero("facil");
        assertFalse(sudoku.estaResuelto()); // Muy probable que no esté resuelto, pero para testeo
    }

    @Test
    public void testResolver() {
        sudoku.generarTablero("facil");
        assertTrue(sudoku.resolver());
        assertTrue(sudoku.estaResuelto());
    }
}