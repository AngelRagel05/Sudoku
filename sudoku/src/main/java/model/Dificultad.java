package model;

public enum Dificultad {

    FACIL("No sabía que eras un cagado."),
    MEDIO("Me sorprende que tengas valentía."),
    DIFICIL("Eres honrado por los Dioses.");

    private final String descripcion;

    private Dificultad (String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
