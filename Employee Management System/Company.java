import java.util.ArrayList;
import java.util.Collections;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;
    private static Company instance = new Company();

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    public static Company getInstance() {
        return instance;
    }

    public ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }

    public ArrayList<Team> getAllTeams() {
        return allTeams;
    }

    public ArrayList<Project> getAllProjects() {
        return allProjects;
    }

    public void listTeams() {
        Team.list(allTeams);
    }

    public void listEmployees() {
        Employee.list(allEmployees);
    }

    public void listProjects() {
        Project.list(allProjects);
    }

    public Employee createEmployee(String name, int annualLeaves) {
        Employee e = new Employee(name, annualLeaves);
        allEmployees.add(e);
        Collections.sort(allEmployees);
        return e;
    }

    public Team createTeam(String teamName, String headName) {
        Employee e = Employee.searchEmployee(allEmployees, headName);
        Team t = new Team(teamName, e);
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }

    public Project createProject(String projectCode, String startDate, int duration) {
        Project p = new Project(projectCode, startDate, duration);
        allProjects.add(p);
        Collections.sort(allProjects);
        return p;
    }

    public void addEmployee(Employee e) {
        this.allEmployees.add(e);
        Collections.sort(allEmployees);
    }

    public void removeEmployee(Employee e) {
        this.allEmployees.remove(e);
    }

    public void addTeam(Team t) {
        this.allTeams.add(t);
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        this.allTeams.remove(t);
    }
    

    public void addProject(Project p) {
        this.allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeProject(Project p) {
        this.allProjects.remove(p);
    }


    public static Team searchTeam(ArrayList<Team> teams, String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    public static Project searchProject(ArrayList<Project> projects, String projectCode) {
        for (Project project : projects) {
            if (project.getProjectCode().equals(projectCode)) {
                return project;
            }
        }
        return null;
    }

    public Employee searchEmployee(ArrayList<Employee> employees, String name) {
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }
}