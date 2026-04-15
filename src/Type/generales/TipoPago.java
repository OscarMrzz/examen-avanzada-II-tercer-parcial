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
}
