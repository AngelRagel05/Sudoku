package model;

public enum Dificultad {

    FACIL("¿Modo fácil? Bueno, al menos no se te caerán los dedos."),
    MEDIO("Buen nivel. No es para cualquiera."),
    DIFICIL("Honor y gloria te esperan, valiente.");


    private final String descripcion;

    private Dificultad (String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
