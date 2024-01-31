import java.util.ArrayList;

public class CmdListTeamMembers implements Command{
    @Override
    public void execute(String[] cmdParts){
        Company company = Company.getInstance();
        ArrayList<Team> teams = company.getAllTeams();
        String teamName = cmdParts[1];
        Team team = Team.searchTeam(teams, teamName);
        ArrayList<Employee> teamMembers = team.getAllMembers();

        System.out.printf("%-30s%-10s%-13s\n", "Role", "Name", "Current / coming leaves");
        for (Employee e : teamMembers) {
            System.out.printf("%-30s%-10s%-13s\n", e.getRole(), e.getName(), e.listLeaves());
        }
        
    }
    
}
