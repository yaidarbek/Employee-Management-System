public class ExEmployeeExists extends Exception {

    public ExEmployeeExists() {
        super("Employee already exists!");
    }

    // You can also provide a constructor with a custom message if needed
    public ExEmployeeExists(String message) {
        super(message);
    }
}