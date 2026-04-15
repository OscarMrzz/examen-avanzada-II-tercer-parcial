package Type.generales;

public enum TipoPagoVenta {
    EFECTIVO("Efectivo"),
    TARJETA("Tarjeta"),
    MIXTO("Mixto");

    private final String displayName;

    TipoPagoVenta(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TipoPagoVenta fromDb(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        for (TipoPagoVenta tipo : values()) {
            if (tipo.name().equalsIgnoreCase(trimmed) || tipo.displayName.equalsIgnoreCase(trimmed)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoPagoVenta inválido desde DB: " + value);
    }
}
