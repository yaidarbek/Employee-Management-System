import java.util.ArrayList;

public class Project implements Comparable<Project> {
    private String projectCode;
    private Day startDate;
    private int duration;
    private Team team;
    private Day endDate;

    public Project(String projectCode, String startDate, int duration) {
        this.projectCode = projectCode;
        this.startDate = new Day(startDate);
        this.duration = duration;
        this.team = null;
        this.endDate = calculateEndDate();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public Day getStartDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public Team getTeam() {
        return team;
    }

    public Day getEndDate() {
        return endDate;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    private Day calculateEndDate() {
        Day endDate = startDate.clone();
        for (int i = 0; i < duration - 1; i++) {
            endDate = endDate.getNextDate();
        }

        return endDate;
    }

    public ArrayList<Day> finalStage(){
        Day startOfTheFinalStage = this.startDate.clone();
        int d = duration;
        if(d > 5){
            d = d - 5;
            for(int i = 0; i < d; i++){
                startOfTheFinalStage = startOfTheFinalStage.getNextDate();
                startOfTheFinalStage.toString();
            }
        }
        ArrayList<Day> finalDays = new ArrayList<>();
        finalDays.add(startOfTheFinalStage);
        finalDays.add(endDate);
        return finalDays;
    }

    public static void list(ArrayList<Project> projects) {
        System.out.printf("%-10s%-13s%-13s%-30s\n", "Project", "Start Day", "End Day", "Team");
        for (Project project : projects) {
            System.out.printf("%-10s%-13s%-13s%-30s\n",
                    project.getProjectCode(),
                    project.getStartDate().toString(),
                    project.getEndDate().toString(),
                    (project.getTeam() != null) ? 
                    (project.getTeam().getTeamName() + " (" + project.getTeam().getTeamMembersAsString() + ")") 
                    : "--"
            );
        }
    }

    public boolean isLeaveBetweenTwoDates(String newDate1, String newDate2, String oldDate1, String oldDate2) {
        Day startDate = new Day(oldDate1);
        Day endDate = new Day(oldDate2);
        Day newStartDate = new Day(newDate1);
        Day newEndDate = new Day(newDate2);
        
        // Check if new start date is between existing leave period
        if (newStartDate.compareTo(startDate) >= 0 && newStartDate.compareTo(endDate) <= 0) {
            return false;
        }
    
        // Check if new end date is between existing leave period
        if (newEndDate.compareTo(startDate) >= 0 && newEndDate.compareTo(endDate) <= 0) {
            return false;
        }
    
        // Check if existing leave period is between new dates
        Day currentDate = startDate.clone();
        while (currentDate.compareTo(endDate) <= 0) {
            if (currentDate.compareTo(newStartDate) >= 0 && currentDate.compareTo(newEndDate) <= 0) {
                return false;
            }
            currentDate = currentDate.getNextDate();
        }
    
        return true;
    }

    @Override
    public int compareTo(Project other) {
        return this.getProjectCode().compareTo(other.getProjectCode());
    }
}