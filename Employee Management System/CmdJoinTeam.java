import java.util.ArrayList;

public class CmdJoinTeam extends RecordedCommand {
    private Team team;
    private String employeeName;
    private ArrayList<Employee> previousTeamMembers; // Field to store the previous team members

    private Team isEmployeeAlreadyInTeam(String employeeName, ArrayList<Team> teams) {
        for (Team team : teams) {
            if (team.isEmployeeInTeam(employeeName)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        ArrayList<Team> teams = company.getAllTeams();
        ArrayList<Employee> employees = company.getAllEmployees();
        
        employeeName = cmdParts[1];
        String teamName = cmdParts[2];
    
        // Check if the employee has already joined another team
        if (isEmployeeAlreadyInTeam(employeeName, teams)!=null) {
            System.out.println(employeeName + " has already joined another team: " + isEmployeeAlreadyInTeam(employeeName, teams).getTeamName());
            return;
        }
    
        // Check if the employee exists
        Employee employee = Employee.searchEmployee(employees, employeeName);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
    
        // Check if the team exists
        team = Team.searchTeam(teams, teamName);
        if (team == null) {
            System.out.println("Team not found!");
            return;
        }
    
        previousTeamMembers = new ArrayList<>(team.getAllMembers()); // Store the previous team members
    
        team.addTeamMember(employeeName);
    

        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        team.setTeamMembers(previousTeamMembers);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        team.addTeamMember(employeeName);
        addUndoCommand(this);
    }
}