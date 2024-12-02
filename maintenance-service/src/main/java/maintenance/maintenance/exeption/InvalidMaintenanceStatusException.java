package maintenance.maintenance.exeption;

public class InvalidMaintenanceStatusException extends RuntimeException {
    public InvalidMaintenanceStatusException(String message) {
        super(message);
    }
}
