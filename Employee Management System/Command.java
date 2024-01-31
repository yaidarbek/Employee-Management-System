public interface Command {
    void execute(String[] cmdParts) throws ExAlreadyExists, ExWrongNumberFormat, ExAlreadyAssigned;
}