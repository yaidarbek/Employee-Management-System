import java.util.ArrayList;

public class CmdListLeaves implements Command {
    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        ArrayList<Employee> employees = company.getAllEmployees();

        if (cmdParts.length > 1) {
            String employeeName = cmdParts[1];
            Employee employee = Employee.searchEmployee(employees, employeeName);
            employee.checkAndRemoveExpiredLeaves();
            if (employee != null) {
                System.out.println(employee.getName() + ": " + employee.listLeaves());
                return;
            }

        }

        for (Employee e : employees) {
            e.checkAndRemoveExpiredLeaves();
            e.listLeaves();
            System.out.println(e.getName() + ": " + e.listLeaves());
        }
    }
}