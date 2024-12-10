package maintenance.maintenance.exeption;

public class InvalidPermission extends RuntimeException {
    public InvalidPermission(String message) {
        super(message);
    }
}
