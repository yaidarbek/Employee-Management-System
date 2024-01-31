public class CmdAssign extends RecordedCommand {
    private Project project;
    private Team team;
    private Team previousTeam; // Field to store the previous team

    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        
        // Search for the project
        project = company.searchProject(company.getAllProjects(), cmdParts[1]);
        if (project == null) {
            System.out.println("Project not found!");
            return;
        }
        
        // Search for the team
        team = company.searchTeam(company.getAllTeams(), cmdParts[2]);
        if (team == null) {
            System.out.println("Team not found!");
            return;
        }

        // Store the previous team
        previousTeam = project.getTeam();
        
        // Assign the team to the project
        project.setTeam(team);
        
        addUndoCommand(this);
        clearRedoList();
        
        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        // Retrieve the Company instance and unassign the team from the project
        project.setTeam(previousTeam);
    }

    @Override
    public void redoMe() {
        // Retrieve the Company instance and reassign the team to the project
        project.setTeam(team);
    }
}