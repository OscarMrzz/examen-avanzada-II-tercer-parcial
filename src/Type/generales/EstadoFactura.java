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

    public static EstadoFactura fromDb(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        for (EstadoFactura estado : values()) {
            if (estado.name().equalsIgnoreCase(trimmed) || estado.displayName.equalsIgnoreCase(trimmed)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("EstadoFactura inválido desde DB: " + value);
    }
}
