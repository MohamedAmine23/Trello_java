package mvvm.Command;



public abstract class Command {

    abstract boolean execute();
    abstract void redo();
    abstract void undo();
    abstract String getName();

}
