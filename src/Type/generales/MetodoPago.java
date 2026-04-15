package Type.generales;

public enum MetodoPago {
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia"),
    CHEQUE("Cheque");

    private final String displayName;

    MetodoPago(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MetodoPago fromDb(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        for (MetodoPago metodo : values()) {
            if (metodo.name().equalsIgnoreCase(trimmed) || metodo.displayName.equalsIgnoreCase(trimmed)) {
                return metodo;
            }
        }
        throw new IllegalArgumentException("MetodoPago inválido desde DB: " + value);
    }
}
