package Type;

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
}
