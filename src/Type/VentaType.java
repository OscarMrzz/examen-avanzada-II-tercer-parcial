package Type;

import java.sql.Timestamp;

public class VentaType {
    private String idVenta;
    private String idClienteVenta;
    private String numeroFacturaVenta;
    private double subtotalVenta;
    private double impuestoVenta;
    private double descuentoVenta;
    private double totalVenta;
    private TipoPagoVenta tipoPagoVenta;
    private Double montoEfectivo;
    private Double montoTarjeta;
    private double cambioVenta;
    private Timestamp fechaVenta;
    private EstadoVenta estadoVenta;
    private String idUsuarioVendedor;

    public VentaType() {
    }

    public VentaType(String idVenta, String idClienteVenta, String numeroFacturaVenta, 
                    double subtotalVenta, double impuestoVenta, double descuentoVenta, 
                    double totalVenta, TipoPagoVenta tipoPagoVenta, Double montoEfectivo, 
                    Double montoTarjeta, double cambioVenta, Timestamp fechaVenta, 
                    EstadoVenta estadoVenta, String idUsuarioVendedor) {
        this.idVenta = idVenta;
        this.idClienteVenta = idClienteVenta;
        this.numeroFacturaVenta = numeroFacturaVenta;
        this.subtotalVenta = subtotalVenta;
        this.impuestoVenta = impuestoVenta;
        this.descuentoVenta = descuentoVenta;
        this.totalVenta = totalVenta;
        this.tipoPagoVenta = tipoPagoVenta;
        this.montoEfectivo = montoEfectivo;
        this.montoTarjeta = montoTarjeta;
        this.cambioVenta = cambioVenta;
        this.fechaVenta = fechaVenta;
        this.estadoVenta = estadoVenta;
        this.idUsuarioVendedor = idUsuarioVendedor;
    }

    // Getters y Setters
    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdClienteVenta() {
        return idClienteVenta;
    }

    public void setIdClienteVenta(String idClienteVenta) {
        this.idClienteVenta = idClienteVenta;
    }

    public String getNumeroFacturaVenta() {
        return numeroFacturaVenta;
    }

    public void setNumeroFacturaVenta(String numeroFacturaVenta) {
        this.numeroFacturaVenta = numeroFacturaVenta;
    }

    public double getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(double subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }

    public double getImpuestoVenta() {
        return impuestoVenta;
    }

    public void setImpuestoVenta(double impuestoVenta) {
        this.impuestoVenta = impuestoVenta;
    }

    public double getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(double descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public TipoPagoVenta getTipoPagoVenta() {
        return tipoPagoVenta;
    }

    public void setTipoPagoVenta(TipoPagoVenta tipoPagoVenta) {
        this.tipoPagoVenta = tipoPagoVenta;
    }

    public Double getMontoEfectivo() {
        return montoEfectivo;
    }

    public void setMontoEfectivo(Double montoEfectivo) {
        this.montoEfectivo = montoEfectivo;
    }

    public Double getMontoTarjeta() {
        return montoTarjeta;
    }

    public void setMontoTarjeta(Double montoTarjeta) {
        this.montoTarjeta = montoTarjeta;
    }

    public double getCambioVenta() {
        return cambioVenta;
    }

    public void setCambioVenta(double cambioVenta) {
        this.cambioVenta = cambioVenta;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public String getIdUsuarioVendedor() {
        return idUsuarioVendedor;
    }

    public void setIdUsuarioVendedor(String idUsuarioVendedor) {
        this.idUsuarioVendedor = idUsuarioVendedor;
    }

    @Override
    public String toString() {
        return "VentaType{" +
                "idVenta='" + idVenta + '\'' +
                ", idClienteVenta='" + idClienteVenta + '\'' +
                ", numeroFacturaVenta='" + numeroFacturaVenta + '\'' +
                ", subtotalVenta=" + subtotalVenta +
                ", impuestoVenta=" + impuestoVenta +
                ", descuentoVenta=" + descuentoVenta +
                ", totalVenta=" + totalVenta +
                ", tipoPagoVenta=" + tipoPagoVenta +
                ", montoEfectivo=" + montoEfectivo +
                ", montoTarjeta=" + montoTarjeta +
                ", cambioVenta=" + cambioVenta +
                ", fechaVenta=" + fechaVenta +
                ", estadoVenta=" + estadoVenta +
                ", idUsuarioVendedor='" + idUsuarioVendedor + '\'' +
                '}';
    }
}
