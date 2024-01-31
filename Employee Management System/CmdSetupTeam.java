import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class CmdSetupTeam extends RecordedCommand {
    private Team team;
    private static Map<String, String> assignedEmployees = new HashMap<>();

    @Override
    public void execute(String[] cmdParts) throws ExAlreadyAssigned, ExAlreadyExists {
        if (cmdParts.length < 3) {
            System.out.println("Insufficient command arguments!");
            return;
        }

        Company c = Company.getInstance();
        Employee leader = Employee.searchEmployee(c.getAllEmployees(), cmdParts[2]);
        if (leader == null) {
            System.out.println("Employee not found!");
            return;
        }

        ArrayList<Team> teams = c.getAllTeams();
        if (c.searchTeam(teams, cmdParts[1]) != null) {
            throw new ExAlreadyExists("Team already exists!");
        }

        if (assignedEmployees.containsValue(leader.getName())) {
            String assignedTeam = null;
            for (Map.Entry<String, String> entry : assignedEmployees.entrySet()) {
                if (entry.getValue().equals(leader.getName())) {
                    assignedTeam = entry.getKey();
                    break;
                }
            }
            throw new ExAlreadyAssigned(leader.getName() + " has already joined another team: " + assignedTeam);
        }

        team = c.createTeam(cmdParts[1], leader.getName());
        team.setDateSetup(SystemDate.getInstance().clone());
        team.setTeamLeader(cmdParts[2]);
        leader.setRole("Leader");
        assignedEmployees.put(cmdParts[1], leader.getName());

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeTeam(team);
        addRedoCommand(this);
        assignedEmployees.remove(team.getTeamName());
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addTeam(team);
        addUndoCommand(this);
        assignedEmployees.put(team.getTeamName(), team.getTeamLeaderName());
    }
}