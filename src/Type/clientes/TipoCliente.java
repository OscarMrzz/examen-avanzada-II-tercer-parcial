package Type.clientes;

public enum TipoCliente {
    CONSUMIDOR_FINAL("Consumidor Final"),
    EMPRESA("Empresa");

    private final String displayName;

    TipoCliente(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TipoCliente fromDb(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        for (TipoCliente tipo : values()) {
            if (tipo.name().equalsIgnoreCase(trimmed) || tipo.displayName.equalsIgnoreCase(trimmed)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoCliente inválido desde DB: " + value);
    }
}
