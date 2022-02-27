package mvvm.Command;

import model.Board;
import model.Column;

public final class RemoveColumnCommand extends Command {
    private final Board board;
    private final Column col;

    public RemoveColumnCommand( Board board, Column col) {
        this.board=board;
        this.col=col;
    }

    @Override
    public boolean execute() {
        return board.removeColumnToBoard(col);
    }

    @Override
    void redo() {
        execute();
    }

    @Override
    void undo() {
        board.addColumnAfterRemove(col);
    }

    @Override
    String getName() {
        return " remove column: " + col;
    }

    @Override
    public String toString() {
        return "" ;
    }
}
