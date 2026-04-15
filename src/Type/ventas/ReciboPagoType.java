package Type.ventas;

import Type.generales.MetodoPago;
import java.sql.Date;
import java.sql.Timestamp;

public class ReciboPagoType {
    private String idReciboPago;
    private String idFacturaCompraPago;
    private String idProveedorPago;
    private double montoPago;
    private Date fechaPago;
    private MetodoPago metodoPago;
    private String referenciaPago;
    private String observacionesPago;
    private Timestamp createdAt;

    public ReciboPagoType() {
    }

    public ReciboPagoType(String idReciboPago, String idFacturaCompraPago, String idProveedorPago,
            double montoPago, Date fechaPago, MetodoPago metodoPago,
            String referenciaPago, String observacionesPago, Timestamp createdAt) {
        this.idReciboPago = idReciboPago;
        this.idFacturaCompraPago = idFacturaCompraPago;
        this.idProveedorPago = idProveedorPago;
        this.montoPago = montoPago;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.referenciaPago = referenciaPago;
        this.observacionesPago = observacionesPago;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public String getIdReciboPago() {
        return idReciboPago;
    }

    public void setIdReciboPago(String idReciboPago) {
        this.idReciboPago = idReciboPago;
    }

    public String getIdFacturaCompraPago() {
        return idFacturaCompraPago;
    }

    public void setIdFacturaCompraPago(String idFacturaCompraPago) {
        this.idFacturaCompraPago = idFacturaCompraPago;
    }

    public String getIdProveedorPago() {
        return idProveedorPago;
    }

    public void setIdProveedorPago(String idProveedorPago) {
        this.idProveedorPago = idProveedorPago;
    }

    public double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(double montoPago) {
        this.montoPago = montoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public String getObservacionesPago() {
        return observacionesPago;
    }

    public void setObservacionesPago(String observacionesPago) {
        this.observacionesPago = observacionesPago;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ReciboPagoType{" +
                "idReciboPago='" + idReciboPago + '\'' +
                ", idFacturaCompraPago='" + idFacturaCompraPago + '\'' +
                ", idProveedorPago='" + idProveedorPago + '\'' +
                ", montoPago=" + montoPago +
                ", fechaPago=" + fechaPago +
                ", metodoPago=" + metodoPago +
                ", referenciaPago='" + referenciaPago + '\'' +
                ", observacionesPago='" + observacionesPago + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
