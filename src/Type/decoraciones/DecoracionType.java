package Type.decoraciones;

import java.sql.Timestamp;

public class DecoracionType {
    private String idDecoracion;
    private String nombreDecoracion;
    private int stockDecoracion;
    private int stockMinimoDecoracion;
    private int stockMaximoDecoracion;
    private String idProveedorDecoracion;
    private String imagenDecoracion;
    private String idColeccionDecoracion;
    private String descripcionDecoracion;
    private Timestamp fechaCreacion;
    private boolean estadoDecoracion;

    public DecoracionType() {
    }

    public DecoracionType(String idDecoracion, String nombreDecoracion, int stockDecoracion,
            int stockMinimoDecoracion, int stockMaximoDecoracion, String idProveedorDecoracion, String imagenDecoracion,
            String idColeccionDecoracion, String descripcionDecoracion, Timestamp fechaCreacion,
            boolean estadoDecoracion) {
        this.idDecoracion = idDecoracion;
        this.nombreDecoracion = nombreDecoracion;
        this.stockDecoracion = stockDecoracion;
        this.stockMinimoDecoracion = stockMinimoDecoracion;
        this.stockMaximoDecoracion = stockMaximoDecoracion;
        this.idProveedorDecoracion = idProveedorDecoracion;
        this.imagenDecoracion = imagenDecoracion;
        this.idColeccionDecoracion = idColeccionDecoracion;
        this.descripcionDecoracion = descripcionDecoracion;
        this.fechaCreacion = fechaCreacion;
        this.estadoDecoracion = estadoDecoracion;
    }

    // Getters y Setters
    public String getIdDecoracion() {
        return idDecoracion;
    }

    public void setIdDecoracion(String idDecoracion) {
        this.idDecoracion = idDecoracion;
    }

    public String getNombreDecoracion() {
        return nombreDecoracion;
    }

    public void setNombreDecoracion(String nombreDecoracion) {
        this.nombreDecoracion = nombreDecoracion;
    }

    public int getStockDecoracion() {
        return stockDecoracion;
    }

    public void setStockDecoracion(int stockDecoracion) {
        this.stockDecoracion = stockDecoracion;
    }

    public int getStockMinimoDecoracion() {
        return stockMinimoDecoracion;
    }

    public void setStockMinimoDecoracion(int stockMinimoDecoracion) {
        this.stockMinimoDecoracion = stockMinimoDecoracion;
    }

    public int getStockMaximoDecoracion() {
        return stockMaximoDecoracion;
    }

    public void setStockMaximoDecoracion(int stockMaximoDecoracion) {
        this.stockMaximoDecoracion = stockMaximoDecoracion;
    }

    public String getIdProveedorDecoracion() {
        return idProveedorDecoracion;
    }

    public void setIdProveedorDecoracion(String idProveedorDecoracion) {
        this.idProveedorDecoracion = idProveedorDecoracion;
    }

    public String getImagenDecoracion() {
        return imagenDecoracion;
    }

    public void setImagenDecoracion(String imagenDecoracion) {
        this.imagenDecoracion = imagenDecoracion;
    }

    public String getIdColeccionDecoracion() {
        return idColeccionDecoracion;
    }

    public void setIdColeccionDecoracion(String idColeccionDecoracion) {
        this.idColeccionDecoracion = idColeccionDecoracion;
    }

    public String getDescripcionDecoracion() {
        return descripcionDecoracion;
    }

    public void setDescripcionDecoracion(String descripcionDecoracion) {
        this.descripcionDecoracion = descripcionDecoracion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstadoDecoracion() {
        return estadoDecoracion;
    }

    public void setEstadoDecoracion(boolean estadoDecoracion) {
        this.estadoDecoracion = estadoDecoracion;
    }

    @Override
    public String toString() {
        return "DecoracionType{" +
                "idDecoracion='" + idDecoracion + '\'' +
                ", nombreDecoracion='" + nombreDecoracion + '\'' +
                ", stockDecoracion=" + stockDecoracion +
                ", stockMinimoDecoracion=" + stockMinimoDecoracion +
                ", stockMaximoDecoracion=" + stockMaximoDecoracion +
                ", idProveedorDecoracion='" + idProveedorDecoracion + '\'' +
                ", imagenDecoracion='" + imagenDecoracion +
                "', idColeccionDecoracion='" + idColeccionDecoracion + '\'' +
                ", descripcionDecoracion='" + descripcionDecoracion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estadoDecoracion=" + estadoDecoracion +
                '}';
    }
}
