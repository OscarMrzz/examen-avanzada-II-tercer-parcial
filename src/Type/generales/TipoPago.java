package Type.generales;

public enum TipoPago {
    CONTADO("Contado"),
    CREDITO("Crédito");

    private final String displayName;

    TipoPago(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TipoPago fromDb(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        for (TipoPago tipo : values()) {
            if (tipo.name().equalsIgnoreCase(trimmed) || tipo.displayName.equalsIgnoreCase(trimmed)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoPago inválido desde DB: " + value);
    }
}
