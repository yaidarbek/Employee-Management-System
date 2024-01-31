public class ExEntityExists extends Exception {
    public ExEntityExists(String entityType) {
        super(entityType + " already exists!");
    }

    public ExEntityExists(String entityType, String message) {
        super(entityType + " already exists: " + message);
    }
}