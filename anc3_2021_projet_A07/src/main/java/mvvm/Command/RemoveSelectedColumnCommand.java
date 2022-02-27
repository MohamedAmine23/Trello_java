package mvvm.Command;


import model.Board;
import model.Column;

import java.util.ArrayList;
import java.util.List;

public class RemoveSelectedColumnCommand extends Command {

    private  final Board board;
    private final List<Column>save;
    private final List<Integer>positions;
    public RemoveSelectedColumnCommand(Board board ) {
        this.save=new ArrayList<>();
        this.board=board;
        this.positions=new ArrayList<>();saveData();

    }


    private void saveData(){
        for(Column col : board.getColumns()){
            if(board.getDeleteCol().contains(col)){
                save.add(col);
                int position= board.getIndex(col);
                positions.add(position);
            }
        }
    }
    boolean execute() {
        boolean res=false;

        for(Column col : board.getDeleteCol()){
                res=board.removeColumnToBoard(col);
        }
        board.getDeleteCol().clear();

        return res;
    }

    @Override
    void redo() {

    }

    @Override
    void undo() {

        for(int i=0;i<save.size();i++){
            board.addColumnAfterRemoveSelected(save.get(i),positions.get(i));
        }

    }

    @Override
    String getName() {
        return "remove columns selected";
    }
}
