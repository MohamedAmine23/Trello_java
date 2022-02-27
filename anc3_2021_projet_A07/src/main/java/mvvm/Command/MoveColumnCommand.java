package mvvm.Command;

import model.Board;
import model.Column;
import model.MyModel;

public final class MoveColumnCommand extends Command{
    private final Board board;
    private final Column column;
    private final MyModel.ToMove move;
    private Column newCol;
    private  final int indexCol;

    public MoveColumnCommand(Board board, Column column, MyModel.ToMove move) {
        this.board = board;
        this.column = column;
        this.move = move;
        indexCol=column.getMyBoard().getIndex(column);
    }

    @Override
    boolean execute() {
        boolean result=false;
        switch (move){
            case LEFT:
                result=board.moveColumn(column, MyModel.ToMove.LEFT);
                newCol=column.getMyBoard().getColumn(indexCol-1);
                break;
            case RIGHT:
                result=board.moveColumn(column, MyModel.ToMove.RIGHT);
                newCol=column.getMyBoard().getColumn(indexCol+1);
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
            case LEFT:
                board.moveColumn(column, MyModel.ToMove.RIGHT);
                break;
            case RIGHT:
                board.moveColumn(column, MyModel.ToMove.LEFT);
                break;
        }
    }

    @Override
    String getName() {
        return "Move column: "+column+" to column: "+newCol ;
    }

}