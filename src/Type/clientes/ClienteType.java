package Type.clientes;

import java.sql.Timestamp;

public class ClienteType {
    private String idCliente;
    private String nombreCliente;
    private String rtnCliente;
    private TipoCliente tipoCliente;
    private String telefonoCliente;
    private String emailCliente;
    private String direccionCliente;
    private Timestamp fechaRegistro;
    private boolean estadoCliente;

    public ClienteType() {
    }

    public ClienteType(String idCliente, String nombreCliente, String rtnCliente, TipoCliente tipoCliente,
            String telefonoCliente, String emailCliente, String direccionCliente,
            Timestamp fechaRegistro, boolean estadoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.rtnCliente = rtnCliente;
        this.tipoCliente = tipoCliente;
        this.telefonoCliente = telefonoCliente;
        this.emailCliente = emailCliente;
        this.direccionCliente = direccionCliente;
        this.fechaRegistro = fechaRegistro;
        this.estadoCliente = estadoCliente;
    }

    // Getters y Setters
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

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    @Override
    public String toString() {
        return "ClienteType{" +
                "idCliente='" + idCliente + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", rtnCliente='" + rtnCliente + '\'' +
                ", tipoCliente=" + tipoCliente +
                ", telefonoCliente='" + telefonoCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", direccionCliente='" + direccionCliente + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estadoCliente=" + estadoCliente +
                '}';
    }
}
