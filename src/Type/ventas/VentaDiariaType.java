package Type.ventas;

import java.sql.Date;

public class VentaDiariaType {
    private Date fechaVenta;
    private int cantidadVentas;
    private double totalVentasDia;
    private double subtotalDia;
    private double totalImpuestosDia;
    private double totalDescuentosDia;
    private double promedioVenta;
    private int cantidadClientes;

    public VentaDiariaType() {
    }

    public VentaDiariaType(Date fechaVenta, int cantidadVentas, double totalVentasDia,
            double subtotalDia, double totalImpuestosDia, double totalDescuentosDia,
            double promedioVenta, int cantidadClientes) {
        this.fechaVenta = fechaVenta;
        this.cantidadVentas = cantidadVentas;
        this.totalVentasDia = totalVentasDia;
        this.subtotalDia = subtotalDia;
        this.totalImpuestosDia = totalImpuestosDia;
        this.totalDescuentosDia = totalDescuentosDia;
        this.promedioVenta = promedioVenta;
        this.cantidadClientes = cantidadClientes;
    }

    // Getters y Setters
    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public double getTotalVentasDia() {
        return totalVentasDia;
    }

    public void setTotalVentasDia(double totalVentasDia) {
        this.totalVentasDia = totalVentasDia;
    }

    public double getSubtotalDia() {
        return subtotalDia;
    }

    public void setSubtotalDia(double subtotalDia) {
        this.subtotalDia = subtotalDia;
    }

    public double getTotalImpuestosDia() {
        return totalImpuestosDia;
    }

    public void setTotalImpuestosDia(double totalImpuestosDia) {
        this.totalImpuestosDia = totalImpuestosDia;
    }

    public double getTotalDescuentosDia() {
        return totalDescuentosDia;
    }

    public void setTotalDescuentosDia(double totalDescuentosDia) {
        this.totalDescuentosDia = totalDescuentosDia;
    }

    public double getPromedioVenta() {
        return promedioVenta;
    }

    public void setPromedioVenta(double promedioVenta) {
        this.promedioVenta = promedioVenta;
    }

    public int getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(int cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    @Override
    public String toString() {
        return "VentaDiariaType{" +
                "fechaVenta=" + fechaVenta +
                ", cantidadVentas=" + cantidadVentas +
                ", totalVentasDia=" + totalVentasDia +
                ", subtotalDia=" + subtotalDia +
                ", totalImpuestosDia=" + totalImpuestosDia +
                ", totalDescuentosDia=" + totalDescuentosDia +
                ", promedioVenta=" + promedioVenta +
                ", cantidadClientes=" + cantidadClientes +
                '}';
    }
}
