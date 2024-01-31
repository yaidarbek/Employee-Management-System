public class CmdStartNewDay extends RecordedCommand {
    private Day previousSystemDate;
 
    @Override
    public void execute(String[] cmdParts) {
        SystemDate currentSystemDate = SystemDate.getInstance();
         
        if (currentSystemDate.toString().equals(cmdParts[1])) {
            System.out.println("The specified date is the same as the current system date.");
            return;
        }
 
        previousSystemDate = currentSystemDate.clone();
        currentSystemDate.set(cmdParts[1]); // Update the current system date
 
        addUndoCommand(this);
        clearRedoList();
 
        System.out.println("Done.");
    }
 
    @Override
    public void undoMe() {
        SystemDate.getInstance().set(previousSystemDate.toString()); // Restore the previous system date
    }
 
    @Override
    public void redoMe() {
        SystemDate.getInstance().set(SystemDate.getInstance().getNextDate().toString()); // Set the next day
    }
}