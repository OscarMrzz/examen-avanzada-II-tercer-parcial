package Type.compras;

public class DetalleCompraType {
    private String idDetalleCompra;
    private String idFacturaCompraDetalle;
    private String idDecoracionDetalle;
    private int cantidadDetalleCompra;
    private double precioCostoCompra;
    private double precioVentaCompra;
    private double subtotalDetalleCompra;

    public DetalleCompraType() {
    }

    public DetalleCompraType(String idDetalleCompra, String idFacturaCompraDetalle, String idDecoracionDetalle,
            int cantidadDetalleCompra, double precioCostoCompra, double precioVentaCompra,
            double subtotalDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
        this.idFacturaCompraDetalle = idFacturaCompraDetalle;
        this.idDecoracionDetalle = idDecoracionDetalle;
        this.cantidadDetalleCompra = cantidadDetalleCompra;
        this.precioCostoCompra = precioCostoCompra;
        this.precioVentaCompra = precioVentaCompra;
        this.subtotalDetalleCompra = subtotalDetalleCompra;
    }

    // Getters y Setters
    public String getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(String idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public String getIdFacturaCompraDetalle() {
        return idFacturaCompraDetalle;
    }

    public void setIdFacturaCompraDetalle(String idFacturaCompraDetalle) {
        this.idFacturaCompraDetalle = idFacturaCompraDetalle;
    }

    public String getIdDecoracionDetalle() {
        return idDecoracionDetalle;
    }

    public void setIdDecoracionDetalle(String idDecoracionDetalle) {
        this.idDecoracionDetalle = idDecoracionDetalle;
    }

    public int getCantidadDetalleCompra() {
        return cantidadDetalleCompra;
    }

    public void setCantidadDetalleCompra(int cantidadDetalleCompra) {
        this.cantidadDetalleCompra = cantidadDetalleCompra;
    }

    public double getPrecioCostoCompra() {
        return precioCostoCompra;
    }

    public void setPrecioCostoCompra(double precioCostoCompra) {
        this.precioCostoCompra = precioCostoCompra;
    }

    public double getPrecioVentaCompra() {
        return precioVentaCompra;
    }

    public void setPrecioVentaCompra(double precioVentaCompra) {
        this.precioVentaCompra = precioVentaCompra;
    }

    public double getSubtotalDetalleCompra() {
        return subtotalDetalleCompra;
    }

    public void setSubtotalDetalleCompra(double subtotalDetalleCompra) {
        this.subtotalDetalleCompra = subtotalDetalleCompra;
    }

    @Override
    public String toString() {
        return "DetalleCompraType{" +
                "idDetalleCompra='" + idDetalleCompra + '\'' +
                ", idFacturaCompraDetalle='" + idFacturaCompraDetalle + '\'' +
                ", idDecoracionDetalle='" + idDecoracionDetalle + '\'' +
                ", cantidadDetalleCompra=" + cantidadDetalleCompra +
                ", precioCostoCompra=" + precioCostoCompra +
                ", precioVentaCompra=" + precioVentaCompra +
                ", subtotalDetalleCompra=" + subtotalDetalleCompra +
                '}';
    }
}
