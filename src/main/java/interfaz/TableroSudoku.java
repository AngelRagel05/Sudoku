package interfaz;

import javax.swing.*;
import java.awt.*;

public class TableroSudoku extends JPanel {

//    Atributos
    private JTextField[][] listatxt;
    private int txtAncho;
    private int txtAltura;
    private int txtMargen;
    private int txtTamañoLetra;
    private Color panelFondo;

//    Colores por defecto
    private Color txtFondo1;
    private Color txtColorLetra1;

//    Colores para cambio color casillas seleccionadas
    private Color txtFondo2;
    private Color txtColorLetra2;

//    Colores para casillas seleccionadas
    private Color txtFondo3;
    private Color txtColorLetra3;

    public TableroSudoku() {
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        listatxt = new JTextField[9][9];
        txtAncho = 35;
        txtAltura = 36;
        txtMargen = 4;
        txtTamañoLetra = 27;
        panelFondo = Color.BLACK;
        txtFondo1 = Color.WHITE;
        txtColorLetra1 = Color.BLACK;
        txtFondo2 = Color.WHITE;
        txtColorLetra2 = Color.BLACK;
        txtFondo3 = Color.WHITE;
        txtColorLetra3 = Color.BLACK;
    }

    public void crearSudoku() {
        this.setLayout(null);
        this.setSize(txtAncho * 9 + (txtMargen * 4), txtAltura * 9 + (txtMargen * 4));
        this.setBackground(panelFondo);
    }

    public JTextField[][] getListatxt() {
        return this.listatxt;
    }

    public void setListatxt(JTextField[][] listatxt) {
        this.listatxt = listatxt;
    }

    public int getTxtAncho() {
        return this.txtAncho;
    }

    public void setTxtAncho(int txtAncho) {
        this.txtAncho = txtAncho;
    }

    public int getTxtAltura() {
        return this.txtAltura;
    }

    public void setTxtAltura(int txtAltura) {
        this.txtAltura = txtAltura;
    }

    public int getTxtMargen() {
        return this.txtMargen;
    }

    public void setTxtMargen(int txtMargen) {
        this.txtMargen = txtMargen;
    }

    public int getTxtTamañoLetra() {
        return this.txtTamañoLetra;
    }

    public void setTxtTamañoLetra(int txtTamañoLetra) {
        this.txtTamañoLetra = txtTamañoLetra;
    }

    public Color getPanelFondo() {
        return this.panelFondo;
    }

    public void setPanelFondo(Color panelFondo) {
        this.panelFondo = panelFondo;
    }

    public Color getTxtFondo1() {
        return this.txtFondo1;
    }

    public void setTxtFondo1(Color txtFondo1) {
        this.txtFondo1 = txtFondo1;
    }

    public Color getTxtColorLetra1() {
        return this.txtColorLetra1;
    }

    public void setTxtColorLetra1(Color txtColorLetra1) {
        this.txtColorLetra1 = txtColorLetra1;
    }

    public Color getTxtFondo2() {
        return this.txtFondo2;
    }

    public void setTxtFondo2(Color txtFondo2) {
        this.txtFondo2 = txtFondo2;
    }

    public Color getTxtColorLetra2() {
        return this.txtColorLetra2;
    }

    public void setTxtColorLetra2(Color txtColorLetra2) {
        this.txtColorLetra2 = txtColorLetra2;
    }

    public Color getTxtFondo3() {
        return this.txtFondo3;
    }

    public void setTxtFondo3(Color txtFondo3) {
        this.txtFondo3 = txtFondo3;
    }

    public Color getTxtColorLetra3() {
        return this.txtColorLetra3;
    }

    public void setTxtColorLetra3(Color txtColorLetra3) {
        this.txtColorLetra3 = txtColorLetra3;
    }
}
