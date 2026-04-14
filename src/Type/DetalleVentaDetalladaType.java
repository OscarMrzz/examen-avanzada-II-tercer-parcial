package Type;

import java.sql.Timestamp;

public class DetalleVentaDetalladaType {
    private String idDetalleVenta;
    private String idVentaDetalle;
    private String numeroFacturaVenta;
    private Timestamp fechaVenta;
    private String nombreCliente;
    private String idDecoracion;
    private String nombreDecoracion;
    private boolean esColeccionDecoracion;
    private String disenadorDecoracion;
    private String numColeccionDecoracion;
    private Integer anioDecoracion;
    private String nombreProveedor;
    private int cantidadDetalleVenta;
    private double precioUnitarioVenta;
    private double subtotalDetalleVenta;
    private double descuentoDetalle;
    private double precioFinalUnitario;

    public DetalleVentaDetalladaType() {
    }

    public DetalleVentaDetalladaType(String idDetalleVenta, String idVentaDetalle, String numeroFacturaVenta, 
                                   Timestamp fechaVenta, String nombreCliente, String idDecoracion, 
                                   String nombreDecoracion, boolean esColeccionDecoracion, 
                                   String disenadorDecoracion, String numColeccionDecoracion, 
                                   Integer anioDecoracion, String nombreProveedor, int cantidadDetalleVenta, 
                                   double precioUnitarioVenta, double subtotalDetalleVenta, 
                                   double descuentoDetalle, double precioFinalUnitario) {
        this.idDetalleVenta = idDetalleVenta;
        this.idVentaDetalle = idVentaDetalle;
        this.numeroFacturaVenta = numeroFacturaVenta;
        this.fechaVenta = fechaVenta;
        this.nombreCliente = nombreCliente;
        this.idDecoracion = idDecoracion;
        this.nombreDecoracion = nombreDecoracion;
        this.esColeccionDecoracion = esColeccionDecoracion;
        this.disenadorDecoracion = disenadorDecoracion;
        this.numColeccionDecoracion = numColeccionDecoracion;
        this.anioDecoracion = anioDecoracion;
        this.nombreProveedor = nombreProveedor;
        this.cantidadDetalleVenta = cantidadDetalleVenta;
        this.precioUnitarioVenta = precioUnitarioVenta;
        this.subtotalDetalleVenta = subtotalDetalleVenta;
        this.descuentoDetalle = descuentoDetalle;
        this.precioFinalUnitario = precioFinalUnitario;
    }

    // Getters y Setters
    public String getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(String idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public String getIdVentaDetalle() {
        return idVentaDetalle;
    }

    public void setIdVentaDetalle(String idVentaDetalle) {
        this.idVentaDetalle = idVentaDetalle;
    }

    public String getNumeroFacturaVenta() {
        return numeroFacturaVenta;
    }

    public void setNumeroFacturaVenta(String numeroFacturaVenta) {
        this.numeroFacturaVenta = numeroFacturaVenta;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

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

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getCantidadDetalleVenta() {
        return cantidadDetalleVenta;
    }

    public void setCantidadDetalleVenta(int cantidadDetalleVenta) {
        this.cantidadDetalleVenta = cantidadDetalleVenta;
    }

    public double getPrecioUnitarioVenta() {
        return precioUnitarioVenta;
    }

    public void setPrecioUnitarioVenta(double precioUnitarioVenta) {
        this.precioUnitarioVenta = precioUnitarioVenta;
    }

    public double getSubtotalDetalleVenta() {
        return subtotalDetalleVenta;
    }

    public void setSubtotalDetalleVenta(double subtotalDetalleVenta) {
        this.subtotalDetalleVenta = subtotalDetalleVenta;
    }

    public double getDescuentoDetalle() {
        return descuentoDetalle;
    }

    public void setDescuentoDetalle(double descuentoDetalle) {
        this.descuentoDetalle = descuentoDetalle;
    }

    public double getPrecioFinalUnitario() {
        return precioFinalUnitario;
    }

    public void setPrecioFinalUnitario(double precioFinalUnitario) {
        this.precioFinalUnitario = precioFinalUnitario;
    }

    @Override
    public String toString() {
        return "DetalleVentaDetalladaType{" +
                "idDetalleVenta='" + idDetalleVenta + '\'' +
                ", idVentaDetalle='" + idVentaDetalle + '\'' +
                ", numeroFacturaVenta='" + numeroFacturaVenta + '\'' +
                ", fechaVenta=" + fechaVenta +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", idDecoracion='" + idDecoracion + '\'' +
                ", nombreDecoracion='" + nombreDecoracion + '\'' +
                ", esColeccionDecoracion=" + esColeccionDecoracion +
                ", disenadorDecoracion='" + disenadorDecoracion + '\'' +
                ", numColeccionDecoracion='" + numColeccionDecoracion + '\'' +
                ", anioDecoracion=" + anioDecoracion +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", cantidadDetalleVenta=" + cantidadDetalleVenta +
                ", precioUnitarioVenta=" + precioUnitarioVenta +
                ", subtotalDetalleVenta=" + subtotalDetalleVenta +
                ", descuentoDetalle=" + descuentoDetalle +
                ", precioFinalUnitario=" + precioFinalUnitario +
                '}';
    }
}
