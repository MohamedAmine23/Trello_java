package mvvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Nameable;
import mvvm.Command.EditCommand;
import mvvm.Command.ManagerCommand;

public class NameableViewModel {

    ManagerCommand managerCommand=ManagerCommand.getInstance();
    private final Nameable nameable;
    protected final StringProperty name= new SimpleStringProperty();

    public NameableViewModel(Nameable nameable) {
        this.nameable = nameable;
     this.name.setValue(nameable.getName());
    }

    public void editName(String newval){
      managerCommand.executeCommand(new EditCommand(newval,nameable));
    }

    public StringProperty nameProperty(){
        return name;
    }
}
