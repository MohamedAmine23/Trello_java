package mvvm.Command;

import model.Card;
import model.Column;
import model.MyModel;
import static model.MyModel.ToMove.*;

public final class MoveCardCommand extends Command {
    private final MyModel.ToMove move;
    private final Column col;
    private final Card card;
    private Column newCol;
    private int newPosition;
    private  final int indexCol;
    private final int positionCard;
    private final Column rightColumn;
    private final Column leftColumn;

    public MoveCardCommand(Column col, Card card, MyModel.ToMove move){
        this.col=col;
        this.card=card;
        this.move=move;
        this.indexCol=col.getMyBoard().getIndex(col);
        this.positionCard=col.getIndex(card);
        this.rightColumn=col.getMyBoard().getColumn(indexCol+1);
        this.leftColumn=col.getMyBoard().getColumn(indexCol-1);
    }

    @Override
    boolean execute() {
        boolean result=false;
        switch (move){

            case UP:
                result=col.moveCard(card, UP);
                newPosition=positionCard+1;
                break;
            case DOWN:
                result=col.moveCard(card, DOWN);
                newPosition=positionCard-1;
                break;
            case LEFT:
                result=col.moveCard(card, LEFT);
                newCol= col.getMyBoard().getColumn(indexCol-1);
                break;
            case RIGHT:
                result=col.moveCard(card, RIGHT);
                newCol= col.getMyBoard().getColumn(indexCol+1);
                break;
        }
        return result;

    }

    @Override
    void redo() {
        execute();
    }

    @Override
    void undo() {
        switch (move){

            case UP:
                col.moveCard(card, DOWN);
                break;

            case DOWN:
                col.moveCard(card, UP);
                break;

            case LEFT:
                leftColumn.moveCard(card, RIGHT);
                break;

            case RIGHT:
                rightColumn.moveCard(card, LEFT);
                break;
        }
    }

    @Override
    String getName() {
        String message="Move card: "+card ;
        if(move==UP||move==DOWN) message+="from position: "+positionCard+" to position: "+newPosition;
        else if (move==RIGHT||move==LEFT)message+=" from column: "+col +" to column: "+newCol;

        return message;
    }


}
