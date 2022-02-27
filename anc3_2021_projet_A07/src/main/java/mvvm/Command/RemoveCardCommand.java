package mvvm.Command;


import model.Card;
import model.Column;

public final class RemoveCardCommand extends Command{
    private final Card card;
    private final Column col;

    public RemoveCardCommand( Card card, Column col) {
        this.card=card;
        this.col=col;
    }

    @Override
    public boolean execute() {
        return col.removeCardFromColumn(card);
    }

    @Override
    void redo() {
        execute();
    }

    @Override
    void undo() {
        col.addCardToColumnAfterRemove(card);
    }

    @Override
    String getName() {
        return " remove card: " + card ;
    }

}
