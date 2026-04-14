package Type;

import java.sql.Timestamp;

public class UsuarioType {
    private String idUsuario;
    private String nombreUsuario;
    private String userUsuario;
    private String passUsuario;
    private PrivilegioUsuario privilegioUsuario;
    private String fotoUsuario;
    private Timestamp fechaCreacion;
    private boolean estadoUsuario;

    public UsuarioType() {
    }

    public UsuarioType(String idUsuario, String nombreUsuario, String userUsuario, String passUsuario,
            PrivilegioUsuario privilegioUsuario, String fotoUsuario, Timestamp fechaCreacion,
            boolean estadoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.userUsuario = userUsuario;
        this.passUsuario = passUsuario;
        this.privilegioUsuario = privilegioUsuario;
        this.fotoUsuario = fotoUsuario;
        this.fechaCreacion = fechaCreacion;
        this.estadoUsuario = estadoUsuario;
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUserUsuario() {
        return userUsuario;
    }

    public void setUserUsuario(String userUsuario) {
        this.userUsuario = userUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }

    public PrivilegioUsuario getPrivilegioUsuario() {
        return privilegioUsuario;
    }

    public void setPrivilegioUsuario(PrivilegioUsuario privilegioUsuario) {
        this.privilegioUsuario = privilegioUsuario;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(boolean estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioType{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", userUsuario='" + userUsuario + '\'' +
                ", passUsuario='" + passUsuario + '\'' +
                ", privilegioUsuario=" + privilegioUsuario +
                ", fotoUsuario='" + fotoUsuario + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estadoUsuario=" + estadoUsuario +
                '}';
    }
}
