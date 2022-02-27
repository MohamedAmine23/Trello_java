package mvvm;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Board;
import model.Card;
import model.Column;
import mvvm.Command.*;
import view.ColumnView;


public final class BoardViewModel extends NameableViewModel {

    private final Board board;
    private final ObservableList<Column> columnList = FXCollections.observableArrayList();
    private final ObservableList<Card> cardList = FXCollections.observableArrayList();
    private final SimpleIntegerProperty columnSelected = new SimpleIntegerProperty();
    private final ObservableList<Column>deleteCol = FXCollections.observableArrayList();
    private final BooleanProperty selectedToDelete=new SimpleBooleanProperty(true);




    public BoardViewModel(Board board) {
        super(board);
        this.board = board;
        configData();
        configColumnSelected();
        configbindingselected();
    }

    public void configbindingselected(){
        selectedToDelete.setValue(deleteCol.isEmpty());
    }

   private void configData() {
        this.name.setValue(board.getName());
        setColumnList();
        setDeleteCol();
        for(int i=0; i<board.getColumns().size();i++){
            cardList.setAll(board.getColumn(i).getCards());
        }

    }

    public void undo(){
        managerCommand.undo();
        configData();
    }

    public void redo(){
        managerCommand.redo();
        configData();
    }

    private void configColumnSelected() {
        columnSelected.addListener((obs,oldval,newval) ->
                board.getColumn((int)newval));
    }

     Column getColumn() {
        int index = columnSelected.get();
        return index == -1 ? null : columnList.get(index);
    }

     void setColumnList(){
        columnList.setAll(board.getColumns());

    }
    void setDeleteCol(){
        deleteCol.setAll(board.getDeleteCol());
       // System.out.println("etat "+deleteCol.isEmpty());
    }



     void removeColumnFromBoard(Column column) {

        Column c = getColumn();
        if (c != null && board != null && managerCommand.executeCommand(new RemoveColumnCommand(board,c))) {
            setColumnList();
        }
    }
    public void removeColumnSelected(){
        if(board != null && managerCommand.executeCommand(new RemoveSelectedColumnCommand(board)));
            setColumnList();
            setDeleteCol();
            configbindingselected();
    }

    public void createColumnAndAddToBoard() {

        Column col = new Column("Column "+(Column.getInstCol()+1),board);
        if (board != null && managerCommand.executeCommand(new AddColumnCommand(board,col))) {
            setColumnList();
        }
    }
    public boolean removeSelectedColumn(){

        return board.getDeleteCol().isEmpty();
    }

    // Getter de propriétés pour le binding externe

    public BooleanProperty selectedToDeleteProperty() {
       return selectedToDelete;
    }
    public SimpleListProperty<Column> columnsProperty(){

        return new SimpleListProperty<>(columnList);
    }

    //biding
    public void selectedColumnBinding(ReadOnlyIntegerProperty integerProperty){
        columnSelected.bind(integerProperty);
    }



}
