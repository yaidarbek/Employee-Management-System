import java.util.ArrayList;

public class CmdSuggestProject implements Command{

    private Project p;
        @Override
    public void execute(String[] cmdParts){
        Company c = Company.getInstance();
        ArrayList<Project> allProjects = c.getAllProjects();
        ArrayList<Team> allTeams = c.getAllTeams();
        String projectName = cmdParts[1];
        p = c.searchProject(allProjects, projectName);

        Day startDate = p.getStartDate();
        String sd = startDate.toString();
        Day endDate = p.getEndDate();
        String ed = endDate.toString();
        int duration = p.getDuration();

        ArrayList<Double> manPowerInts = new ArrayList<>();



        for(Team t:allTeams){
            ArrayList<Employee> allMembers = t.getAllMembers();            
            Day end = endDate.clone();
            Double manPower = 0.0;

            for(Employee e:allMembers){
                
                ArrayList<ArrayList<String>> allLeaves = e.getAllLeaves();
                if(allLeaves.size()==0){
                    manPower += 1.0;
                }
                ArrayList<Integer> allDurations = e.getAllPreviousDurations();
                int counter = 0;
                Day start = startDate.clone();
                while(start.compareTo(end) <= 0){
                    String s = start.toString();
                    for(ArrayList<String> leaves : allLeaves){
                        if(e.isLeaveBetweenTwoDates(s, s, leaves.get(0), leaves.get(1)) == true){
                            counter++;
                            break;
                        }
                    }


                    start = start.getNextDate();
                }
                manPower = manPower + counter / (double) duration;
                
            }
            t.setManPower(manPower);
        }

        
        for(Team t: allTeams){
            double projectPower = 0;
            for(Project p: allProjects){
                int counter = 0;
                if(p.getTeam()==null){
                    continue;
                }
                Team temp = p.getTeam();
                if(temp.equals(t)){
                    Day start = startDate.clone();
                    while(start.compareTo(endDate) <= 0){
                        String s = start.toString();

                        if(p.isLeaveBetweenTwoDates(s, s, p.getStartDate().toString(), p.getEndDate().toString()) == false){
                            counter++;
                        }
                        start = start.getNextDate();
                    }
                    projectPower = projectPower + counter / (double) duration;
                }
                
            }
            t.setProjectPower(projectPower);
        }

        double l = 1000000000.0;
        Team result = new Team(ed, null);
        for(Team t: allTeams){
            if(t.getLoadingFactor() < l){
                result = t;
                l = t.getLoadingFactor();
            }
        }

        System.out.println("During the period of project "+
         p.getProjectCode() +
         " (" + p.getStartDate() + " to " + p.getEndDate() + "):");
        System.out.println("  Average manpower (m) and count of existing projects (p) of each team:");
        for(Team t:allTeams){
            System.out.print(String.format("    %s: m=%.2f workers, p=%.2f projects\n",
            t.getTeamName(), t.getManPower(), t.getProjectPower()));
        }
        System.out.printf("  Projected loading factor when a team takes this project %s:\n",p.getProjectCode());
        for (Team t : allTeams) {
            System.out.printf("    %s: (p+1)/m = %.2f\n", t.getTeamName(), t.getLoadingFactor());
        }
        System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading\n",p.getProjectCode(), result.getTeamName());
    }
}


