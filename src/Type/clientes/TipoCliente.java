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
}
