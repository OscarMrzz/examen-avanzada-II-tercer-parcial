package Type;

import java.sql.Date;
import java.sql.Timestamp;

public class FacturaCompraType {
    private String idFacturaCompra;
    private String idProveedorFacturaCompra;
    private String numeroFactura;
    private double totalFacturaCompra;
    private TipoPago tipoPagoFacturaCompra;
    private EstadoFactura estadoFacturaCompra;
    private Date fechaFacturaCompra;
    private Date fechaVencimientoFactura;
    private String condicionFactura;
    private Timestamp createdAt;

    public FacturaCompraType() {
    }

    public FacturaCompraType(String idFacturaCompra, String idProveedorFacturaCompra, String numeroFactura, 
                           double totalFacturaCompra, TipoPago tipoPagoFacturaCompra, 
                           EstadoFactura estadoFacturaCompra, Date fechaFacturaCompra, 
                           Date fechaVencimientoFactura, String condicionFactura, Timestamp createdAt) {
        this.idFacturaCompra = idFacturaCompra;
        this.idProveedorFacturaCompra = idProveedorFacturaCompra;
        this.numeroFactura = numeroFactura;
        this.totalFacturaCompra = totalFacturaCompra;
        this.tipoPagoFacturaCompra = tipoPagoFacturaCompra;
        this.estadoFacturaCompra = estadoFacturaCompra;
        this.fechaFacturaCompra = fechaFacturaCompra;
        this.fechaVencimientoFactura = fechaVencimientoFactura;
        this.condicionFactura = condicionFactura;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public String getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(String idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    public String getIdProveedorFacturaCompra() {
        return idProveedorFacturaCompra;
    }

    public void setIdProveedorFacturaCompra(String idProveedorFacturaCompra) {
        this.idProveedorFacturaCompra = idProveedorFacturaCompra;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
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

    public String getCondicionFactura() {
        return condicionFactura;
    }

    public void setCondicionFactura(String condicionFactura) {
        this.condicionFactura = condicionFactura;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FacturaCompraType{" +
                "idFacturaCompra='" + idFacturaCompra + '\'' +
                ", idProveedorFacturaCompra='" + idProveedorFacturaCompra + '\'' +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", totalFacturaCompra=" + totalFacturaCompra +
                ", tipoPagoFacturaCompra=" + tipoPagoFacturaCompra +
                ", estadoFacturaCompra=" + estadoFacturaCompra +
                ", fechaFacturaCompra=" + fechaFacturaCompra +
                ", fechaVencimientoFactura=" + fechaVencimientoFactura +
                ", condicionFactura='" + condicionFactura + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
