package mvvm.Command;

import model.Board;
import model.Column;

public final class AddColumnCommand extends Command {

    private final Board board;
    private final Column col;

    public AddColumnCommand( Board board, Column col) {
        this.board=board;
        this.col=col;
    }

    @Override
    public boolean execute() {
        return board.addColumnToBoard(col);
    }

    @Override
    void redo() {
        execute();
    }

    @Override
    void undo() {
        board.removeColumnToBoard(col);
    }

    @Override
    String getName() {
        return " adding a column";
    }


}
