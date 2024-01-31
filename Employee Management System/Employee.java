import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private int entitledAnnualLeaves;
    private Day startDate;
    private Day endDate;
    private int duration;
    private String Role = "Member";
    private ArrayList<Integer> previousDurations;
    private ArrayList<ArrayList<String>> allLeaves;

 
    public Employee(String name, int annualLeaves) {
        this.name = name;
        this.annualLeaves = annualLeaves;
        this.entitledAnnualLeaves = annualLeaves;
        allLeaves = new ArrayList<>();
        previousDurations = new ArrayList<>();
    }
 
    public String getName() {
        return name;
    }
    public int getEntitledAnnualLeaves(){
        return entitledAnnualLeaves;
    }
    public String getRole() {
        return Role;
    }
    public void setRole(String Role){
        this.Role = Role;
    }
 
    public int getAnnualLeaves() {
        return annualLeaves;
    }
    public ArrayList<ArrayList<String>> getAllLeaves(){
        return allLeaves;
    }
    public ArrayList<Integer> getAllPreviousDurations(){
        return previousDurations;
    }

    public int calculateLeaveDuration(Day startDate, Day endDate) {
        int duration = 0;
        Day currentDay = startDate.clone();

        while (currentDay.compareTo(endDate) < 0) {
            currentDay = currentDay.getNextDate();
            duration++;
        }

        return duration;
    }

    public void decreaseAnnualLeaves(int leaveCount) {
        if (annualLeaves >= leaveCount) {
            annualLeaves -= leaveCount;
        } else {
            System.out.println("Insufficient balance of annual leave. Only " + annualLeaves + " days left!");
        }
    }
    public void increaseAnnualLeaves(int leaveCount) {
        annualLeaves += leaveCount;
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
    
    public ArrayList<String> canAddNewLeave(Day newStartDate, Day newEndDate) {
        String oldDate1, oldDate2;
        for (ArrayList<String> leaves : allLeaves) {
            ArrayList<String> conflict = new ArrayList<>();
            oldDate1 = leaves.get(0);
            oldDate2 = leaves.get(1);
            if (!isLeaveBetweenTwoDates(newStartDate.toString(), newEndDate.toString(), oldDate1, oldDate2)) {
                conflict.add(oldDate1);
                conflict.add(oldDate2);
                return conflict;
            }
        }
    
        return null;
    }

    public void setLeave(String startDate, String endDate) {
        Day newStartDate = new Day(startDate);
        Day newEndDate = new Day(endDate);
        this.startDate = newStartDate;
        this.endDate = newEndDate;
        ArrayList<String> onePair = new ArrayList<>();
        onePair.add(startDate);
        onePair.add(endDate);
        allLeaves.add(onePair);
        sortAllLeavesByDate();

    }

    public void cancelLeave(String startDate, String endDate) {
        for (int i = 0; i < allLeaves.size(); i++) {
            ArrayList<String> leave = allLeaves.get(i);
            String leaveStartDate = leave.get(0);
            String leaveEndDate = leave.get(1);
            if (leaveStartDate.equals(startDate) && leaveEndDate.equals(endDate)) {
                allLeaves.remove(i);
                return;
            }
        }
    }

    public void checkAndRemoveExpiredLeaves() {
        Day systemDate = SystemDate.getInstance();
        ArrayList<ArrayList<String>> expiredLeaves = new ArrayList<>();
        for (ArrayList<String> leave : allLeaves) {
            String leaveEndDate = leave.get(1);
            if (systemDate.compareTo(new Day(leaveEndDate)) > 0) {
                expiredLeaves.add(leave);
            }
        }
        allLeaves.removeAll(expiredLeaves);
    }

    public String listLeaves() {
        StringBuilder leavesString = new StringBuilder();
        if (allLeaves.isEmpty()) {
            leavesString.append("--");
        } else {
            int numLeaves = allLeaves.size();
            for (int i = 0; i < numLeaves; i++) {
                ArrayList<String> leave = allLeaves.get(i);
                String startDate = leave.get(0);
                String endDate = leave.get(1);
    
                // Remove leading zero if present
                if (startDate.startsWith("0")) {
                    startDate = startDate.substring(1);
                }
                if (endDate.startsWith("0")) {
                    endDate = endDate.substring(1);
                }
    
                leavesString.append(startDate).append(" to ").append(endDate);
                if (i < numLeaves - 1) {
                    leavesString.append(", ");
                }
            }
        }
        return leavesString.toString();
    }
 
    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }
 
    public static Employee searchEmployee(ArrayList<Employee> employees, String name) {
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }
 
    public static void list(ArrayList<Employee> allEmployees) {
        for (Employee e : allEmployees) {
            System.out.printf("%s (Entitled Annual Leaves: %d days)\n",
                    e.getName(), e.getEntitledAnnualLeaves());
        }
    }

    public void sortAllLeavesByDate() {
        Collections.sort(allLeaves, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> list1, ArrayList<String> list2) {
                String date1 = list1.get(0);
                String date2 = list2.get(0);
    
                Day day1 = new Day(date1);
                Day day2 = new Day(date2);
    
                return day1.compareTo(day2);
            }
        });
    }
    public void setAllLeaves(ArrayList<ArrayList<String>> leaves) {

        allLeaves.clear();


        allLeaves.addAll(leaves);
    }
    public void setAllPreviousDurations(ArrayList<Integer> durations){
        previousDurations.clear();
        previousDurations.addAll(durations);
    }
}
