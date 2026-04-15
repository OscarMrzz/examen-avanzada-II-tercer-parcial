package Type.decoraciones;

public class DecoracionMasVendidaType {
    private String idDecoracion;
    private String nombreDecoracion;
    private boolean esColeccionDecoracion;
    private String disenadorDecoracion;
    private String numColeccionDecoracion;
    private Integer anioDecoracion;
    private String nombreProveedor;
    private int totalUnidadesVendidas;
    private double totalVentasGeneradas;
    private int cantidadVentas;

    public DecoracionMasVendidaType() {
    }

    public DecoracionMasVendidaType(String idDecoracion, String nombreDecoracion, boolean esColeccionDecoracion,
            String disenadorDecoracion, String numColeccionDecoracion,
            Integer anioDecoracion, String nombreProveedor, int totalUnidadesVendidas,
            double totalVentasGeneradas, int cantidadVentas) {
        this.idDecoracion = idDecoracion;
        this.nombreDecoracion = nombreDecoracion;
        this.esColeccionDecoracion = esColeccionDecoracion;
        this.disenadorDecoracion = disenadorDecoracion;
        this.numColeccionDecoracion = numColeccionDecoracion;
        this.anioDecoracion = anioDecoracion;
        this.nombreProveedor = nombreProveedor;
        this.totalUnidadesVendidas = totalUnidadesVendidas;
        this.totalVentasGeneradas = totalVentasGeneradas;
        this.cantidadVentas = cantidadVentas;
    }

    // Getters y Setters
    public String getIdDecoracion() {
        return idDecoracion;
    }

    public void setIdDecoracion(String idDecoracion) {
        this.idDecoracion = idDecoracion;
    }

    public String getNombreDecoracion() {
        return nombreDecoracion;
    }

    public void setNombreDecoracion(String nombreDecoracion) {
        this.nombreDecoracion = nombreDecoracion;
    }

    public boolean isEsColeccionDecoracion() {
        return esColeccionDecoracion;
    }

    public void setEsColeccionDecoracion(boolean esColeccionDecoracion) {
        this.esColeccionDecoracion = esColeccionDecoracion;
    }

    public String getDisenadorDecoracion() {
        return disenadorDecoracion;
    }

    public void setDisenadorDecoracion(String disenadorDecoracion) {
        this.disenadorDecoracion = disenadorDecoracion;
    }

    public String getNumColeccionDecoracion() {
        return numColeccionDecoracion;
    }

    public void setNumColeccionDecoracion(String numColeccionDecoracion) {
        this.numColeccionDecoracion = numColeccionDecoracion;
    }

    public Integer getAnioDecoracion() {
        return anioDecoracion;
    }

    public void setAnioDecoracion(Integer anioDecoracion) {
        this.anioDecoracion = anioDecoracion;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getTotalUnidadesVendidas() {
        return totalUnidadesVendidas;
    }

    public void setTotalUnidadesVendidas(int totalUnidadesVendidas) {
        this.totalUnidadesVendidas = totalUnidadesVendidas;
    }

    public double getTotalVentasGeneradas() {
        return totalVentasGeneradas;
    }

    public void setTotalVentasGeneradas(double totalVentasGeneradas) {
        this.totalVentasGeneradas = totalVentasGeneradas;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    @Override
    public String toString() {
        return "DecoracionMasVendidaType{" +
                "idDecoracion='" + idDecoracion + '\'' +
                ", nombreDecoracion='" + nombreDecoracion + '\'' +
                ", esColeccionDecoracion=" + esColeccionDecoracion +
                ", disenadorDecoracion='" + disenadorDecoracion + '\'' +
                ", numColeccionDecoracion='" + numColeccionDecoracion + '\'' +
                ", anioDecoracion=" + anioDecoracion +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", totalUnidadesVendidas=" + totalUnidadesVendidas +
                ", totalVentasGeneradas=" + totalVentasGeneradas +
                ", cantidadVentas=" + cantidadVentas +
                '}';
    }
}
