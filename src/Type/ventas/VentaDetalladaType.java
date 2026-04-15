package Type.ventas;

import Type.clientes.TipoCliente;
import Type.generales.TipoPagoVenta;
import Type.generales.EstadoVenta;
import java.sql.Timestamp;

public class VentaDetalladaType {
    private String idVenta;
    private String numeroFacturaVenta;
    private Timestamp fechaVenta;
    private String idCliente;
    private String nombreCliente;
    private String rtnCliente;
    private TipoCliente tipoCliente;
    private String telefonoCliente;
    private String idUsuario;
    private String vendedor;
    private double subtotalVenta;
    private double impuestoVenta;
    private double descuentoVenta;
    private double totalVenta;
    private TipoPagoVenta tipoPagoVenta;
    private Double montoEfectivo;
    private Double montoTarjeta;
    private double cambioVenta;
    private EstadoVenta estadoVenta;
    private int cantidadProductos;

    public VentaDetalladaType() {
    }

    public VentaDetalladaType(String idVenta, String numeroFacturaVenta, Timestamp fechaVenta,
            String idCliente, String nombreCliente, String rtnCliente,
            TipoCliente tipoCliente, String telefonoCliente, String idUsuario,
            String vendedor, double subtotalVenta, double impuestoVenta,
            double descuentoVenta, double totalVenta, TipoPagoVenta tipoPagoVenta,
            Double montoEfectivo, Double montoTarjeta, double cambioVenta,
            EstadoVenta estadoVenta, int cantidadProductos) {
        this.idVenta = idVenta;
        this.numeroFacturaVenta = numeroFacturaVenta;
        this.fechaVenta = fechaVenta;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.rtnCliente = rtnCliente;
        this.tipoCliente = tipoCliente;
        this.telefonoCliente = telefonoCliente;
        this.idUsuario = idUsuario;
        this.vendedor = vendedor;
        this.subtotalVenta = subtotalVenta;
        this.impuestoVenta = impuestoVenta;
        this.descuentoVenta = descuentoVenta;
        this.totalVenta = totalVenta;
        this.tipoPagoVenta = tipoPagoVenta;
        this.montoEfectivo = montoEfectivo;
        this.montoTarjeta = montoTarjeta;
        this.cambioVenta = cambioVenta;
        this.estadoVenta = estadoVenta;
        this.cantidadProductos = cantidadProductos;
    }

    // Getters y Setters
    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
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

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getRtnCliente() {
        return rtnCliente;
    }

    public void setRtnCliente(String rtnCliente) {
        this.rtnCliente = rtnCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
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

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    @Override
    public String toString() {
        return "VentaDetalladaType{" +
                "idVenta='" + idVenta + '\'' +
                ", numeroFacturaVenta='" + numeroFacturaVenta + '\'' +
                ", fechaVenta=" + fechaVenta +
                ", idCliente='" + idCliente + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", rtnCliente='" + rtnCliente + '\'' +
                ", tipoCliente=" + tipoCliente +
                ", telefonoCliente='" + telefonoCliente + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", vendedor='" + vendedor + '\'' +
                ", subtotalVenta=" + subtotalVenta +
                ", impuestoVenta=" + impuestoVenta +
                ", descuentoVenta=" + descuentoVenta +
                ", totalVenta=" + totalVenta +
                ", tipoPagoVenta=" + tipoPagoVenta +
                ", montoEfectivo=" + montoEfectivo +
                ", montoTarjeta=" + montoTarjeta +
                ", cambioVenta=" + cambioVenta +
                ", estadoVenta=" + estadoVenta +
                ", cantidadProductos=" + cantidadProductos +
                '}';
    }
}
