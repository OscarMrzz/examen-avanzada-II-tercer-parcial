package Type;

import java.sql.Date;

public class CompraDetalladaType {
    private String idFacturaCompra;
    private String numeroFactura;
    private Date fechaFacturaCompra;
    private Date fechaVencimientoFactura;
    private String idProveedor;
    private String nombreProveedor;
    private String rtnProveedor;
    private String telefonoProveedor;
    private double totalFacturaCompra;
    private TipoPago tipoPagoFacturaCompra;
    private EstadoFactura estadoFacturaCompra;
    private String condicionFactura;
    private double totalPagado;
    private double saldoPendiente;
    private String estadoRealPago;

    public CompraDetalladaType() {
    }

    public CompraDetalladaType(String idFacturaCompra, String numeroFactura, Date fechaFacturaCompra,
            Date fechaVencimientoFactura, String idProveedor, String nombreProveedor,
            String rtnProveedor, String telefonoProveedor, double totalFacturaCompra,
            TipoPago tipoPagoFacturaCompra, EstadoFactura estadoFacturaCompra,
            String condicionFactura, double totalPagado, double saldoPendiente,
            String estadoRealPago) {
        this.idFacturaCompra = idFacturaCompra;
        this.numeroFactura = numeroFactura;
        this.fechaFacturaCompra = fechaFacturaCompra;
        this.fechaVencimientoFactura = fechaVencimientoFactura;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.rtnProveedor = rtnProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.totalFacturaCompra = totalFacturaCompra;
        this.tipoPagoFacturaCompra = tipoPagoFacturaCompra;
        this.estadoFacturaCompra = estadoFacturaCompra;
        this.condicionFactura = condicionFactura;
        this.totalPagado = totalPagado;
        this.saldoPendiente = saldoPendiente;
        this.estadoRealPago = estadoRealPago;
    }

    // Getters y Setters
    public String getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(String idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaFacturaCompra() {
        return fechaFacturaCompra;
    }

    public void setFechaFacturaCompra(Date fechaFacturaCompra) {
        this.fechaFacturaCompra = fechaFacturaCompra;
    }

    public Date getFechaVencimientoFactura() {
        return fechaVencimientoFactura;
    }

    public void setFechaVencimientoFactura(Date fechaVencimientoFactura) {
        this.fechaVencimientoFactura = fechaVencimientoFactura;
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

    public String getRtnProveedor() {
        return rtnProveedor;
    }

    public void setRtnProveedor(String rtnProveedor) {
        this.rtnProveedor = rtnProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public double getTotalFacturaCompra() {
        return totalFacturaCompra;
    }

    public void setTotalFacturaCompra(double totalFacturaCompra) {
        this.totalFacturaCompra = totalFacturaCompra;
    }

    public TipoPago getTipoPagoFacturaCompra() {
        return tipoPagoFacturaCompra;
    }

    public void setTipoPagoFacturaCompra(TipoPago tipoPagoFacturaCompra) {
        this.tipoPagoFacturaCompra = tipoPagoFacturaCompra;
    }

    public EstadoFactura getEstadoFacturaCompra() {
        return estadoFacturaCompra;
    }

    public void setEstadoFacturaCompra(EstadoFactura estadoFacturaCompra) {
        this.estadoFacturaCompra = estadoFacturaCompra;
    }

    public String getCondicionFactura() {
        return condicionFactura;
    }

    public void setCondicionFactura(String condicionFactura) {
        this.condicionFactura = condicionFactura;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstadoRealPago() {
        return estadoRealPago;
    }

    public void setEstadoRealPago(String estadoRealPago) {
        this.estadoRealPago = estadoRealPago;
    }

    @Override
    public String toString() {
        return "CompraDetalladaType{" +
                "idFacturaCompra='" + idFacturaCompra + '\'' +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", fechaFacturaCompra=" + fechaFacturaCompra +
                ", fechaVencimientoFactura=" + fechaVencimientoFactura +
                ", idProveedor='" + idProveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", rtnProveedor='" + rtnProveedor + '\'' +
                ", telefonoProveedor='" + telefonoProveedor + '\'' +
                ", totalFacturaCompra=" + totalFacturaCompra +
                ", tipoPagoFacturaCompra=" + tipoPagoFacturaCompra +
                ", estadoFacturaCompra=" + estadoFacturaCompra +
                ", condicionFactura='" + condicionFactura + '\'' +
                ", totalPagado=" + totalPagado +
                ", saldoPendiente=" + saldoPendiente +
                ", estadoRealPago='" + estadoRealPago + '\'' +
                '}';
    }
}
