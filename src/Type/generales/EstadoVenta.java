package Type.generales;

public enum EstadoVenta {
    ACTIVA("Activa"),
    CANCELADA("Cancelada");

    private final String displayName;

    EstadoVenta(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EstadoVenta fromDb(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        for (EstadoVenta estado : values()) {
            if (estado.name().equalsIgnoreCase(trimmed) || estado.displayName.equalsIgnoreCase(trimmed)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("EstadoVenta inválido desde DB: " + value);
    }
}
