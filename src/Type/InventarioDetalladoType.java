package Type;

public class InventarioDetalladoType {
    private String idDecoracion;
    private String nombreDecoracion;
    private int stockDecoracion;
    private int stockMinimoDecoracion;
    private int stockMaximoDecoracion;
    private double precioCostoDecoracion;
    private double precioVentaDecoracion;
    private boolean esColeccionDecoracion;
    private String disenadorDecoracion;
    private String numColeccionDecoracion;
    private Integer anioDecoracion;
    private String descripcionDecoracion;
    private boolean estadoDecoracion;
    private String idProveedor;
    private String nombreProveedor;
    private String telefonoProveedor;
    private double margenUtilidadPorcentaje;
    private double valorTotalInventario;
    private String estadoStock;

    public InventarioDetalladoType() {
    }

    public InventarioDetalladoType(String idDecoracion, String nombreDecoracion, int stockDecoracion, 
                                  int stockMinimoDecoracion, int stockMaximoDecoracion, 
                                  double precioCostoDecoracion, double precioVentaDecoracion, 
                                  boolean esColeccionDecoracion, String disenadorDecoracion, 
                                  String numColeccionDecoracion, Integer anioDecoracion, 
                                  String descripcionDecoracion, boolean estadoDecoracion, 
                                  String idProveedor, String nombreProveedor, String telefonoProveedor, 
                                  double margenUtilidadPorcentaje, double valorTotalInventario, 
                                  String estadoStock) {
        this.idDecoracion = idDecoracion;
        this.nombreDecoracion = nombreDecoracion;
        this.stockDecoracion = stockDecoracion;
        this.stockMinimoDecoracion = stockMinimoDecoracion;
        this.stockMaximoDecoracion = stockMaximoDecoracion;
        this.precioCostoDecoracion = precioCostoDecoracion;
        this.precioVentaDecoracion = precioVentaDecoracion;
        this.esColeccionDecoracion = esColeccionDecoracion;
        this.disenadorDecoracion = disenadorDecoracion;
        this.numColeccionDecoracion = numColeccionDecoracion;
        this.anioDecoracion = anioDecoracion;
        this.descripcionDecoracion = descripcionDecoracion;
        this.estadoDecoracion = estadoDecoracion;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.margenUtilidadPorcentaje = margenUtilidadPorcentaje;
        this.valorTotalInventario = valorTotalInventario;
        this.estadoStock = estadoStock;
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

    public boolean isEstadoDecoracion() {
        return estadoDecoracion;
    }

    public void setEstadoDecoracion(boolean estadoDecoracion) {
        this.estadoDecoracion = estadoDecoracion;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public double getMargenUtilidadPorcentaje() {
        return margenUtilidadPorcentaje;
    }

    public void setMargenUtilidadPorcentaje(double margenUtilidadPorcentaje) {
        this.margenUtilidadPorcentaje = margenUtilidadPorcentaje;
    }

    public double getValorTotalInventario() {
        return valorTotalInventario;
    }

    public void setValorTotalInventario(double valorTotalInventario) {
        this.valorTotalInventario = valorTotalInventario;
    }

    public String getEstadoStock() {
        return estadoStock;
    }

    public void setEstadoStock(String estadoStock) {
        this.estadoStock = estadoStock;
    }

    @Override
    public String toString() {
        return "InventarioDetalladoType{" +
                "idDecoracion='" + idDecoracion + '\'' +
                ", nombreDecoracion='" + nombreDecoracion + '\'' +
                ", stockDecoracion=" + stockDecoracion +
                ", stockMinimoDecoracion=" + stockMinimoDecoracion +
                ", stockMaximoDecoracion=" + stockMaximoDecoracion +
                ", precioCostoDecoracion=" + precioCostoDecoracion +
                ", precioVentaDecoracion=" + precioVentaDecoracion +
                ", esColeccionDecoracion=" + esColeccionDecoracion +
                ", disenadorDecoracion='" + disenadorDecoracion + '\'' +
                ", numColeccionDecoracion='" + numColeccionDecoracion + '\'' +
                ", anioDecoracion=" + anioDecoracion +
                ", descripcionDecoracion='" + descripcionDecoracion + '\'' +
                ", estadoDecoracion=" + estadoDecoracion +
                ", idProveedor='" + idProveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", telefonoProveedor='" + telefonoProveedor + '\'' +
                ", margenUtilidadPorcentaje=" + margenUtilidadPorcentaje +
                ", valorTotalInventario=" + valorTotalInventario +
                ", estadoStock='" + estadoStock + '\'' +
                '}';
    }
}
