package mvvm.Command;

import model.Card;
import model.Column;

public final class AddCardCommand extends Command{
    private Card card;
    private Column col;

    public AddCardCommand( Column col, Card card) {
        this.col=col;
        this.card=card;
    }

    @Override
    public boolean execute() {
        return col.addCardToColumn(card);
    }

    @Override
    void redo() {
        execute();
    }

    @Override
    void undo() {
        col.removeCardFromColumn(card);
    }

    @Override
    String getName() {
        return " adding a card";
    }
}
