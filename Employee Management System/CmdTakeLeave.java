import java.util.ArrayList;

public class CmdTakeLeave extends RecordedCommand {
    private Employee employee;
    private String startDate;
    private String endDate;
    private int duration;
    private ArrayList<Integer> previousDurations;
    private ArrayList<ArrayList<String>> previousLeaves;



    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        ArrayList<Employee> employees = company.getAllEmployees();
        ArrayList<Project> projects = company.getAllProjects();
        ArrayList<Team> teams = company.getAllTeams();
        String employeeName = cmdParts[1];
        startDate = cmdParts[2];
        endDate = cmdParts[3];
    
        employee = Employee.searchEmployee(employees, employeeName);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        previousLeaves = new ArrayList<>(employee.getAllLeaves());
        previousDurations = new ArrayList<>(employee.getAllPreviousDurations());
        Day startDateDay = new Day(startDate);
        Day endDateDay = new Day(endDate);



        Team t2 = null;
        Project p2 = null;
        for(Project p : projects){
            for(Team t : teams){
                if(t.isEmployeeInTeam(employeeName)==true){
                    t2 = t; // Employee in this team
                }
            }
            if(p.getTeam() == t2){
                p2 = p; // employee is in this project
            }
            if(p2 != null && t2 != null){
            ArrayList<Day> finalStage = p2.finalStage();
            if(!employee.isLeaveBetweenTwoDates(startDate, endDate, finalStage.get(0).toString(), finalStage.get(1).toString())){
                System.out.println("The leave is invalid.  Reason: Project " + 
                p2.getProjectCode() +
                " will be in its final stage during " +
                finalStage.get(0).toString() +
                " to " +
                finalStage.get(1).toString()+
                "."
                );
                return;
            }
        }
        }

        if (startDate.startsWith("0")) {
            startDate = startDate.substring(1);
        }
        if (endDate.startsWith("0")) {
            endDate = endDate.substring(1);
        }
        ArrayList<String> conflict1 = employee.canAddNewLeave(startDateDay, endDateDay);
        if (conflict1 != null) {
            System.out.println("Leave overlapped: The leave period " + conflict1.get(0).toString() + " to " + conflict1.get(1).toString() + " is found!");
            return;
        }
    
        int duration = employee.calculateLeaveDuration(startDateDay, endDateDay);
        previousDurations.add(duration);
    
        if (employee.getAnnualLeaves() >= duration + 1) {
            employee.decreaseAnnualLeaves(duration + 1);
            employee.setLeave(startDate, endDate);
            System.out.println("Done. " +
                    employeeName +
                    "'s remaining annual leave: " +
                    employee.getAnnualLeaves() +
                    " days");
        } else {
            System.out.println("Insufficient balance of annual leave. " + employee.getAnnualLeaves() + " days left only!");
            return;
        }
        addUndoCommand(this);
        clearRedoList();
    }
    public void undoMe() {
        // Revert the leaves to the previous state
        employee.setAllLeaves(previousLeaves);
        
        employee.setAllPreviousDurations(previousDurations);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        employee.setLeave(startDate, endDate);
        employee.increaseAnnualLeaves(previousDurations.get(previousDurations.size() - 1) - 1);
        addUndoCommand(this);
    }
}