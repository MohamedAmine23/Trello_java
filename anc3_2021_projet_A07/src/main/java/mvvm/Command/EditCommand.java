package mvvm.Command;


import model.Nameable;

public final class EditCommand extends Command{
    private final String newName;
    private final String oldName;
    private Nameable nameable;


    public EditCommand(String name, Nameable nameable){
        this.nameable=nameable;
        this.newName=name;
        this.oldName=nameable.getName();
    }

    @Override
    boolean execute() {
        nameable.setName(newName);
      return true;
    }

    @Override
    void redo() {
        execute();
    }

    @Override
    void undo() {
        nameable.setName(oldName);
    }

    @Override
    String getName() {
        return "edit title " +oldName;
    }

}


