package sudoku;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SudokuCellTest {

    @Test
    void testCeldaEditablePorDefecto() {
        SudokuCell cell = new SudokuCell(false);

        assertNull(cell.getNumeroDefinitivo());
        assertTrue(cell.isEditable());
        assertEquals(Color.WHITE, cell.getBackground());
    }

    @Test
    void testCeldaFijaOriginal() {
        SudokuCell cell = new SudokuCell(true);

        assertFalse(cell.isEditable());
        assertEquals(new Color(220, 220, 220), cell.getBackground());
    }

    @Test
    void testMarcarFijaUsuario() {
        SudokuCell cell = new SudokuCell(false);

        cell.marcarComoFijaPorUsuario();

        assertFalse(cell.isEditable());
        assertTrue(cell.isFijaUsuario());
        assertEquals(new Color(180, 255, 180), cell.getBackground());
    }

    @Test
    void testSetCeldaFijaCombinada() {
        SudokuCell cell = new SudokuCell(false);

        cell.setCeldaFija(true, true);  // Ambas fijas

        assertFalse(cell.isEditable());
        assertEquals(new Color(220, 220, 220), cell.getBackground());  // Prioridad fija original
    }

    @Test
    void testSetYGetNumeroDefinitivo() {
        SudokuCell cell = new SudokuCell(false);

        cell.setNumeroDefinitivo(7);
        assertEquals(7, cell.getNumeroDefinitivo());
        assertEquals("7", cell.getText());

        cell.setNumeroDefinitivo(null);
        assertNull(cell.getNumeroDefinitivo());
        assertEquals("", cell.getText());
    }
}