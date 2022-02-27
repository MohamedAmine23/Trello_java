package mvvm;

import javafx.beans.property.*;
import model.Card;
import model.MyModel;
import mvvm.Command.MoveCardCommand;


public final class CardViewModel extends NameableViewModel {

    private final Card card;
    private final ColumnViewModel columnViewModel;
    private final SimpleBooleanProperty disabledLeft = new SimpleBooleanProperty();
    private final SimpleBooleanProperty disabledRight = new SimpleBooleanProperty();
    private final SimpleBooleanProperty disabledUp = new SimpleBooleanProperty();
    private final SimpleBooleanProperty disabledDown = new SimpleBooleanProperty();

    public CardViewModel(Card card,ColumnViewModel columnViewModel){
        super(card);
        this.card = card;
        this.columnViewModel=columnViewModel;
        configAppLogic();
    }

    private void configAppLogic() {
        configDisableButtons();
    }


    public void remove() {
        columnViewModel.removeCardFromColumn(card);
    }

    // Move card
    public boolean moveCardToUp(){
        Boolean move= managerCommand.executeCommand(new MoveCardCommand(card.getMyColumn(),card, MyModel.ToMove.UP));
        columnViewModel.setCardList();
        return move;
    }

    public boolean moveCardToDown(){
        Boolean moving = managerCommand.executeCommand(new MoveCardCommand(card.getMyColumn(),card,MyModel.ToMove.DOWN));
        columnViewModel.setCardList();
        return moving;
    }

    public boolean moveCardToRight() {
        return columnViewModel.moveCardToRight(card);
    }

    public boolean moveCardToLeft(){
        return columnViewModel.moveCardToLeft(card);
    }

    //Button disabled

    private Boolean btnUpHasToBeDisabled(){
        return (card == null ) || card.getMyColumn().NoMoveLeftOrUp(card);
    }

    private Boolean btnDownHasToBeDisabled(){
        return (card == null || card.getMyColumn().NoMoveRightOrDown(card));
    }

    // Getter de propriétés pour le binding externe

    public SimpleBooleanProperty disabledUpProperty(){
        return disabledUp;
    }
    public SimpleBooleanProperty disabledDownProperty(){
        return disabledDown;
    }
    public SimpleBooleanProperty disabledLeftProperty(){
        return disabledLeft;
    }
    public SimpleBooleanProperty disabledRightProperty(){
        return disabledRight;
    }
    public SimpleBooleanProperty cardUpDisabledProperty(Card c){
        return new SimpleBooleanProperty(btnUpHasToBeDisabled());
    }
    public SimpleBooleanProperty cardDownDisabledProperty(Card c){
        return new SimpleBooleanProperty(btnDownHasToBeDisabled());
    }

    //Biding
    private void configDisableButtons(){
        disabledRight.bind(columnViewModel.columnRightDisabledProperty());
        disabledLeft.bind(columnViewModel.columnLeftDisabledProperty());
        disabledUp.bind(cardUpDisabledProperty(card));
        disabledDown.bind(cardDownDisabledProperty(card));
    }

}
