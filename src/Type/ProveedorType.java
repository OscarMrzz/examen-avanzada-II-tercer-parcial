package Type;

import java.sql.Timestamp;

public class ProveedorType {
    private String idProveedor;
    private String nombreProveedor;
    private String rtnProveedor;
    private String telefonoProveedor;
    private String emailProveedor;
    private String direccionProveedor;
    private String contactoProveedor;
    private Timestamp fechaRegistro;
    private boolean estadoProveedor;

    public ProveedorType() {
    }

    public ProveedorType(String idProveedor, String nombreProveedor, String rtnProveedor, 
                        String telefonoProveedor, String emailProveedor, String direccionProveedor, 
                        String contactoProveedor, Timestamp fechaRegistro, boolean estadoProveedor) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.rtnProveedor = rtnProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.emailProveedor = emailProveedor;
        this.direccionProveedor = direccionProveedor;
        this.contactoProveedor = contactoProveedor;
        this.fechaRegistro = fechaRegistro;
        this.estadoProveedor = estadoProveedor;
    }

    // Getters y Setters
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

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getContactoProveedor() {
        return contactoProveedor;
    }

    public void setContactoProveedor(String contactoProveedor) {
        this.contactoProveedor = contactoProveedor;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstadoProveedor() {
        return estadoProveedor;
    }

    public void setEstadoProveedor(boolean estadoProveedor) {
        this.estadoProveedor = estadoProveedor;
    }

    @Override
    public String toString() {
        return "ProveedorType{" +
                "idProveedor='" + idProveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", rtnProveedor='" + rtnProveedor + '\'' +
                ", telefonoProveedor='" + telefonoProveedor + '\'' +
                ", emailProveedor='" + emailProveedor + '\'' +
                ", direccionProveedor='" + direccionProveedor + '\'' +
                ", contactoProveedor='" + contactoProveedor + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estadoProveedor=" + estadoProveedor +
                '}';
    }
}
