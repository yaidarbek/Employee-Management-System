import java.util.ArrayList;
public class CmdCreateProject extends RecordedCommand {
 
    private Project p;
 
    @Override
    public void execute(String[] cmdParts) throws ExAlreadyExists, ExWrongNumberFormat {
        Company c = Company.getInstance();
        String projectCode = cmdParts[1];
        String startDate = cmdParts[2];
        int duration;

        try {
            duration = Integer.parseInt(cmdParts[3]);
        } catch (NumberFormatException e) {
            throw new ExWrongNumberFormat("Wrong number format for project duration!");
        }
        ArrayList<Project> progects= c.getAllProjects();
        if(c.searchProject(progects, projectCode)!=null){
            throw new ExAlreadyExists("Project already exists!");
        }


 
        p = c.createProject(projectCode, startDate, duration);

        addUndoCommand(this);
        clearRedoList();
         
        System.out.println("Done.");

    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeProject(p);
    }
 
    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addProject(p);
    }

}