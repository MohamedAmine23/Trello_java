package mvvm;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Card;
import model.Column;
import model.MyModel;
import mvvm.Command.AddCardCommand;
import mvvm.Command.MoveCardCommand;
import mvvm.Command.MoveColumnCommand;
import mvvm.Command.RemoveCardCommand;


public final class ColumnViewModel extends NameableViewModel {

    public Column getColumn() {
        return column;
    }
    private SimpleBooleanProperty selectedToDeleteProp= new SimpleBooleanProperty();
    private final Column column;
    private final BoardViewModel boardViewModel;
    private final ObservableList<Card> cardList = FXCollections.observableArrayList();
    private final SimpleIntegerProperty cardSelected = new SimpleIntegerProperty();
    private final SimpleBooleanProperty disableColumnLeft = new SimpleBooleanProperty(),
            disableColumnRight = new SimpleBooleanProperty(),selection= new SimpleBooleanProperty();



    public ColumnViewModel( Column column, BoardViewModel boardViewModel) {
        super(column);
        this.column = column;
      configListener();
        this.boardViewModel=boardViewModel;
        setCardList();
        configAppLogic();

    }
    public void configListener(){
        this.selectedToDeleteProp.addListener((source, oldVal, newVal) -> {
            if(newVal){
                column.getMyBoard().getDeleteCol().add(column);
                    boardViewModel.setDeleteCol();boardViewModel.configbindingselected();
            }
            else {
                column.getMyBoard().getDeleteCol().remove(column);
                boardViewModel.setDeleteCol();
                if(column.getMyBoard().getDeleteCol().isEmpty()){
                    boardViewModel.configbindingselected();
                }
            }
        });
    }
    void setCardList() {
        cardList.setAll(column.getCards());
    }

    private void configAppLogic(){
        configCardSelected();
        configDisableButtons();
    }

    private void configCardSelected() {
        cardSelected.addListener((obs,oldval,newval) ->
                column.getCard((int)newval));
    }


    // Move column
    public boolean moveColumnToLeft(){
        Boolean move=managerCommand.executeCommand(new MoveColumnCommand(column.getMyBoard(),column, MyModel.ToMove.LEFT));
        boardViewModel.setColumnList();
        return move;
    }

    public boolean moveColumnToRight(){
        Boolean move = managerCommand.executeCommand(new MoveColumnCommand(column.getMyBoard(),column, MyModel.ToMove.RIGHT));
        boardViewModel.setColumnList();
        return move;
    }

    //move card from a column to another
     boolean moveCardToRight(Card card){
        Boolean moving = managerCommand.executeCommand(new MoveCardCommand(column,card,MyModel.ToMove.RIGHT));
        setCardList();
        boardViewModel.setColumnList();
        return moving;
    }
     boolean moveCardToLeft(Card card){
        Boolean moving = managerCommand.executeCommand(new MoveCardCommand(column,card,MyModel.ToMove.LEFT));
        setCardList();
        boardViewModel.setColumnList();
        return moving;
    }

    // Button disabled
    private Boolean btnLeftHasToBeDisabled(){

        return (column == null ) || column.getMyBoard().NoMoveLeftOrUp(column);
    }

    private Boolean btnRightHasToBeDisabled(){

        return (column == null || column.getMyBoard().NoMoveRightOrDown(column));
    }

    public Card getCard() {
        int index = cardSelected.get();
        return index == -1 ? null : cardList.get(index);
    }

    public void createCardAndAddToColumn() {
        Card c = new Card(" Card "+(Card.getInstCard()+1),column);
        if (column != null && managerCommand.executeCommand(new AddCardCommand(column,c))) {
            setCardList();
        }
    }

    public void remove(){
        boardViewModel.removeColumnFromBoard(column);
    }

     void removeCardFromColumn(Card card) {
        Card c = getCard();
        if (c != null && column != null && managerCommand.executeCommand(new RemoveCardCommand(c,column))) {
            setCardList();
        }
    }

    // Getter de propriétés pour le binding externe

    public SimpleListProperty<Card> cardsProperty(){
        return new SimpleListProperty<>(cardList);
    }

    public SimpleBooleanProperty columnLeftDisabledProperty(){
        return disableColumnLeft;
    }
    public SimpleBooleanProperty selectionProperty(){
        return selection;
    }
    //vue
    public SimpleBooleanProperty columnRightDisabledProperty(){
        return disableColumnRight;
    }

    public SimpleBooleanProperty columnLeftDisabledProperty(Column c){
        return new SimpleBooleanProperty(btnLeftHasToBeDisabled());
    }
    //model
    public SimpleBooleanProperty columnRightDisabledProperty(Column c){
        return new SimpleBooleanProperty(btnRightHasToBeDisabled());
    }


    //biding

    public void selectedCardBinding(ReadOnlyIntegerProperty integerProperty){
        cardSelected.bind(integerProperty);
    }

    public BooleanProperty selectedColumnToDeleteProperty(){
        return selectedToDeleteProp;
    }
    private void configDisableButtons(){
        disableColumnLeft.bind(columnLeftDisabledProperty(column));
        disableColumnRight.bind(columnRightDisabledProperty(column));
        selection.bindBidirectional(selectedColumnToDeleteProperty());
    }
}
