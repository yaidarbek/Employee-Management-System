import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private String teamLeader;
    private ArrayList<Employee> teamMembers;
    private double manPower;
    private double porjectPower;
    private double loadingFactor;

    Company company = Company.getInstance();
    ArrayList<Employee> employees = company.getAllEmployees();
 
    public Team(String teamName, Employee head) {
        this.teamName = teamName;
        this.head = head;
        this.dateSetup = SystemDate.getInstance().clone();
        this.teamMembers = new ArrayList<>();
    }
 
    public static void list(ArrayList<Team> list) {
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team team : list) {
            System.out.printf("%-30s%-10s%-13s\n", team.teamName, team.head.getName(), team.dateSetup.toString());
        }
    }

    public void setManPower(double manPower) {
        this.manPower = manPower;
    }
    public void setProjectPower(double projectPower){
        this.porjectPower = projectPower;
    }
    public double getManPower(){
        return manPower;
    }
    public double getProjectPower(){
        return porjectPower;
    }
    public double getLoadingFactor(){
        return (porjectPower+1)/manPower;
    }

    public ArrayList<Employee> getAllMembers() {
        return teamMembers;
    }

    
    public static Team searchTeam(ArrayList<Team> allTeams, String teamName){
        for (Team team: allTeams){
            if(team.getTeamName().equals(teamName)){
                return team;
            }
        }
        return null;
    }

    public String getTeamName(){
        return teamName;
    }

    public void setTeamLeader(String name){
        this.teamLeader = name;
        Employee employee = Employee.searchEmployee(employees, name);
        this.head = employee;
        teamMembers.add(head);
    }

    public void addTeamMember(String name){
        Employee employee = Employee.searchEmployee(employees, name);
        teamMembers.add(employee);
        sortTeamMembers();
    }
    public void sortTeamMembers() {
        // Sort the team members except for the team leader
        Collections.sort(teamMembers, new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                if (emp1.equals(head)) {
                    return -1; // Move the team leader to the beginning
                } else if (emp2.equals(head)) {
                    return 1;
                } else {
                    return emp1.getName().compareTo(emp2.getName());
                }
            }
        });
    }
    public void removeTeamMember(String name) {
        teamMembers.remove(name);
    }

    public String getTeamLeaderName(){
        return teamLeader;
    }

    public void setTeamMembers(ArrayList<Employee> teamMembers) {
        this.teamMembers.clear();
        this.teamMembers.addAll(teamMembers);
    }

    public int getTeamMembersCount() {
        return teamMembers.size();
    }
 
    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }
     
    public void setDateSetup(Day dateSetup) {
        this.dateSetup = dateSetup;
    }

    public boolean isEmployeeInTeam(String employeeName) {
        for (Employee member : teamMembers) {
            if (member.getName().equals(employeeName)) {
                return true;
            }
        }
        return false;
    }
    public String getTeamMembersAsString() {
        StringBuilder membersString = new StringBuilder();
        for (Employee member : teamMembers) {
            membersString.append(member.getName()).append(", ");
        }
        // Remove the trailing comma and space if there are team members
        if (membersString.length() > 0) {
            membersString.setLength(membersString.length() - 2);
        }
        return membersString.toString();
    }
    
}