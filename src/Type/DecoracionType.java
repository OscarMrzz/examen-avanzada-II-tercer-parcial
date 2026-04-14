package Type;

import java.sql.Timestamp;

public class DecoracionType {
    private String idDecoracion;
    private String nombreDecoracion;
    private int stockDecoracion;
    private int stockMinimoDecoracion;
    private int stockMaximoDecoracion;
    private double precioCostoDecoracion;
    private double precioVentaDecoracion;
    private String idProveedorDecoracion;
    private String imagenDecoracion;
    private boolean esColeccionDecoracion;
    private String disenadorDecoracion;
    private String numColeccionDecoracion;
    private Integer anioDecoracion;
    private String descripcionDecoracion;
    private Timestamp fechaCreacion;
    private boolean estadoDecoracion;

    public DecoracionType() {
    }

    public DecoracionType(String idDecoracion, String nombreDecoracion, int stockDecoracion, 
                         int stockMinimoDecoracion, int stockMaximoDecoracion, double precioCostoDecoracion, 
                         double precioVentaDecoracion, String idProveedorDecoracion, String imagenDecoracion, 
                         boolean esColeccionDecoracion, String disenadorDecoracion, String numColeccionDecoracion, 
                         Integer anioDecoracion, String descripcionDecoracion, Timestamp fechaCreacion, 
                         boolean estadoDecoracion) {
        this.idDecoracion = idDecoracion;
        this.nombreDecoracion = nombreDecoracion;
        this.stockDecoracion = stockDecoracion;
        this.stockMinimoDecoracion = stockMinimoDecoracion;
        this.stockMaximoDecoracion = stockMaximoDecoracion;
        this.precioCostoDecoracion = precioCostoDecoracion;
        this.precioVentaDecoracion = precioVentaDecoracion;
        this.idProveedorDecoracion = idProveedorDecoracion;
        this.imagenDecoracion = imagenDecoracion;
        this.esColeccionDecoracion = esColeccionDecoracion;
        this.disenadorDecoracion = disenadorDecoracion;
        this.numColeccionDecoracion = numColeccionDecoracion;
        this.anioDecoracion = anioDecoracion;
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

    public double getPrecioCostoDecoracion() {
        return precioCostoDecoracion;
    }

    public void setPrecioCostoDecoracion(double precioCostoDecoracion) {
        this.precioCostoDecoracion = precioCostoDecoracion;
    }

    public double getPrecioVentaDecoracion() {
        return precioVentaDecoracion;
    }

    public void setPrecioVentaDecoracion(double precioVentaDecoracion) {
        this.precioVentaDecoracion = precioVentaDecoracion;
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

    public boolean isEsColeccionDecoracion() {
        return esColeccionDecoracion;
    }

    public void setEsColeccionDecoracion(boolean esColeccionDecoracion) {
        this.esColeccionDecoracion = esColeccionDecoracion;
    }

    public String getDisenadorDecoracion() {
        return disenadorDecoracion;
    }

    public void setDisenadorDecoracion(String disenadorDecoracion) {
        this.disenadorDecoracion = disenadorDecoracion;
    }

    public String getNumColeccionDecoracion() {
        return numColeccionDecoracion;
    }

    public void setNumColeccionDecoracion(String numColeccionDecoracion) {
        this.numColeccionDecoracion = numColeccionDecoracion;
    }

    public Integer getAnioDecoracion() {
        return anioDecoracion;
    }

    public void setAnioDecoracion(Integer anioDecoracion) {
        this.anioDecoracion = anioDecoracion;
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
                ", precioCostoDecoracion=" + precioCostoDecoracion +
                ", precioVentaDecoracion=" + precioVentaDecoracion +
                ", idProveedorDecoracion='" + idProveedorDecoracion + '\'' +
                ", imagenDecoracion='" + imagenDecoracion + '\'' +
                ", esColeccionDecoracion=" + esColeccionDecoracion +
                ", disenadorDecoracion='" + disenadorDecoracion + '\'' +
                ", numColeccionDecoracion='" + numColeccionDecoracion + '\'' +
                ", anioDecoracion=" + anioDecoracion +
                ", descripcionDecoracion='" + descripcionDecoracion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estadoDecoracion=" + estadoDecoracion +
                '}';
    }
}
