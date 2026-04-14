package Type;

public class DetalleVentaType {
    private String idDetalleVenta;
    private String idVentaDetalle;
    private String idDecoracionDetalle;
    private int cantidadDetalleVenta;
    private double precioUnitarioVenta;
    private double subtotalDetalleVenta;
    private double descuentoDetalle;

    public DetalleVentaType() {
    }

    public DetalleVentaType(String idDetalleVenta, String idVentaDetalle, String idDecoracionDetalle, 
                           int cantidadDetalleVenta, double precioUnitarioVenta, 
                           double subtotalDetalleVenta, double descuentoDetalle) {
        this.idDetalleVenta = idDetalleVenta;
        this.idVentaDetalle = idVentaDetalle;
        this.idDecoracionDetalle = idDecoracionDetalle;
        this.cantidadDetalleVenta = cantidadDetalleVenta;
        this.precioUnitarioVenta = precioUnitarioVenta;
        this.subtotalDetalleVenta = subtotalDetalleVenta;
        this.descuentoDetalle = descuentoDetalle;
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

    public String getIdDecoracionDetalle() {
        return idDecoracionDetalle;
    }

    public void setIdDecoracionDetalle(String idDecoracionDetalle) {
        this.idDecoracionDetalle = idDecoracionDetalle;
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

    @Override
    public String toString() {
        return "DetalleVentaType{" +
                "idDetalleVenta='" + idDetalleVenta + '\'' +
                ", idVentaDetalle='" + idVentaDetalle + '\'' +
                ", idDecoracionDetalle='" + idDecoracionDetalle + '\'' +
                ", cantidadDetalleVenta=" + cantidadDetalleVenta +
                ", precioUnitarioVenta=" + precioUnitarioVenta +
                ", subtotalDetalleVenta=" + subtotalDetalleVenta +
                ", descuentoDetalle=" + descuentoDetalle +
                '}';
    }
}
