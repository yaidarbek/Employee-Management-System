import java.util.ArrayList;

public class CmdHire extends RecordedCommand {

    private Employee e;

    @Override
    public void execute(String[] cmdParts) throws ExAlreadyExists, ExWrongNumberFormat {
        Company c = Company.getInstance();
        String employeeName = cmdParts[1];

        String annualLeavesStr = cmdParts[2];

        int annualLeaves;

        try {
            annualLeaves = Integer.parseInt(annualLeavesStr);
        } catch (NumberFormatException e) {
            throw new ExWrongNumberFormat("Wrong number format for annual leaves!");
        }


        ArrayList<Employee> allEmployees = c.getAllEmployees();

        // Check if the employee already exists
        if (c.searchEmployee(allEmployees, employeeName)!=null) {
            throw new ExAlreadyExists("Employee already exists!");
        }

        e = c.createEmployee(employeeName, annualLeaves);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeEmployee(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addEmployee(e);
        addUndoCommand(this);
    }
}