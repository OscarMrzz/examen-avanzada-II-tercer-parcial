package Type;

public class DetalleCompraType {
    private String idDetalleCompra;
    private String idFacturaCompraDetalle;
    private String idDecoracionDetalle;
    private int cantidadDetalleCompra;
    private double precioUnitarioCompra;
    private double subtotalDetalleCompra;

    public DetalleCompraType() {
    }

    public DetalleCompraType(String idDetalleCompra, String idFacturaCompraDetalle, String idDecoracionDetalle, 
                           int cantidadDetalleCompra, double precioUnitarioCompra, double subtotalDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
        this.idFacturaCompraDetalle = idFacturaCompraDetalle;
        this.idDecoracionDetalle = idDecoracionDetalle;
        this.cantidadDetalleCompra = cantidadDetalleCompra;
        this.precioUnitarioCompra = precioUnitarioCompra;
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

    public double getPrecioUnitarioCompra() {
        return precioUnitarioCompra;
    }

    public void setPrecioUnitarioCompra(double precioUnitarioCompra) {
        this.precioUnitarioCompra = precioUnitarioCompra;
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
                ", precioUnitarioCompra=" + precioUnitarioCompra +
                ", subtotalDetalleCompra=" + subtotalDetalleCompra +
                '}';
    }
}
