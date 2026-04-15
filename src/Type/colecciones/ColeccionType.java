package Type.colecciones;

import java.sql.Timestamp;

public class ColeccionType {
    private String idColeccion;
    private String nombreColeccion;
    private String disenadorColeccion;
    private String numColeccionColeccion;
    private int anioColeccion;
    private String descripcionColeccion;
    private Timestamp fechaCreacion;
    private boolean estadoColeccion;

    public ColeccionType() {
    }

    public ColeccionType(String idColeccion, String nombreColeccion, String disenadorColeccion,
            String numColeccionColeccion, int anioColeccion, String descripcionColeccion,
            Timestamp fechaCreacion, boolean estadoColeccion) {
        this.idColeccion = idColeccion;
        this.nombreColeccion = nombreColeccion;
        this.disenadorColeccion = disenadorColeccion;
        this.numColeccionColeccion = numColeccionColeccion;
        this.anioColeccion = anioColeccion;
        this.descripcionColeccion = descripcionColeccion;
        this.fechaCreacion = fechaCreacion;
        this.estadoColeccion = estadoColeccion;
    }

    // Getters y Setters
    public String getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(String idColeccion) {
        this.idColeccion = idColeccion;
    }

    public String getNombreColeccion() {
        return nombreColeccion;
    }

    public void setNombreColeccion(String nombreColeccion) {
        this.nombreColeccion = nombreColeccion;
    }

    public String getDisenadorColeccion() {
        return disenadorColeccion;
    }

    public void setDisenadorColeccion(String disenadorColeccion) {
        this.disenadorColeccion = disenadorColeccion;
    }

    public String getNumColeccionColeccion() {
        return numColeccionColeccion;
    }

    public void setNumColeccionColeccion(String numColeccionColeccion) {
        this.numColeccionColeccion = numColeccionColeccion;
    }

    public int getAnioColeccion() {
        return anioColeccion;
    }

    public void setAnioColeccion(int anioColeccion) {
        this.anioColeccion = anioColeccion;
    }

    public String getDescripcionColeccion() {
        return descripcionColeccion;
    }

    public void setDescripcionColeccion(String descripcionColeccion) {
        this.descripcionColeccion = descripcionColeccion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstadoColeccion() {
        return estadoColeccion;
    }

    public void setEstadoColeccion(boolean estadoColeccion) {
        this.estadoColeccion = estadoColeccion;
    }

    @Override
    public String toString() {
        return nombreColeccion + " (" + numColeccionColeccion + ")";
    }
}
