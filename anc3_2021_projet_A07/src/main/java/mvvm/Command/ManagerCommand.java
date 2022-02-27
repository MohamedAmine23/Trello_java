package mvvm.Command;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Stack;


public final class ManagerCommand {

    private static ManagerCommand instance=null;
    private Stack<Command> undoStack = new Stack<Command>();
    private Stack<Command> redoStack = new Stack<Command>();
    private final SimpleBooleanProperty disabledUndo = new SimpleBooleanProperty(isUndoAvailable());
    private final SimpleBooleanProperty disabledRedo = new SimpleBooleanProperty(isRedoAvailable());
    private final StringProperty undoName= new SimpleStringProperty(getUndoName());
    private final StringProperty redoName= new SimpleStringProperty(getRedoName());


    public static ManagerCommand getInstance(){
        if(instance==null){
            instance= new ManagerCommand();
        }
        return instance;
    }

    public boolean executeCommand(Command command) {
           Boolean b= command.execute();
            undoStack.push(command);
            redoStack.clear();
            configActionnableButtons();
            configName();
        return b;
    }



    public void undo() {
        if (!undoStack.isEmpty()) {
                Command command = undoStack.pop();
                command.undo();
                redoStack.push(command);
                configActionnableButtons();
                configName();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
                Command command = redoStack.pop();
                command.redo();
                undoStack.push(command);
                configActionnableButtons();
                configName();
        }
    }

    public boolean isUndoAvailable() {
        return undoStack.isEmpty();
    }
    public boolean isRedoAvailable() {
        return redoStack.isEmpty();
    }

    public String getUndoName() {
        if (!isUndoAvailable()) {
            return "Undo  " + undoStack.peek().getName() + " Ctrl+Z";
        }
        return "Undo";
    }

    public String getRedoName() {
        if (!isRedoAvailable()) {
            return "Redo  " + redoStack.peek().getName() + " Ctrl+Y";
        }
        return "Redo";
    }
    private void configActionnableButtons() {
        disabledRedo.setValue(isRedoAvailable());
        disabledUndo.setValue(isUndoAvailable());
    }

    private void configName(){
        undoName.setValue(getUndoName());
        redoName.set(getRedoName());
    }
    public SimpleBooleanProperty disabledUndoProperty(){

        return disabledUndo;
    }
    public SimpleBooleanProperty disabledRedoProperty(){

        return disabledRedo;
    }

    public StringProperty redoNameProperty(){
        return redoName;
    }
    public StringProperty undoNameProperty(){
        return undoName;
    }
}

