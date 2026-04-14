package Type;

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
}
