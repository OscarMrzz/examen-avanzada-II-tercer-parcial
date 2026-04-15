package Type.generales;

public enum EstadoFactura {
    PAGADA("Pagada"),
    PENDIENTE("Pendiente");

    private final String displayName;

    EstadoFactura(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
