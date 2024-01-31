public class ExAlreadyAssigned extends Exception {
    public ExAlreadyAssigned() {
        super("Employee has already been assigned to another team.");
    }

    public ExAlreadyAssigned(String message) {
        super(message);
    }
}