package Type;

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
}
