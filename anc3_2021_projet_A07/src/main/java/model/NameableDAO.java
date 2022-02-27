package model;


public final class NameableDAO extends DAO<Nameable>{

    private DAO<Card> dataCard= new CardDAO();
    private DAO<Column> dataColumn= new ColumnDAO();
    private DAO<Board> dataBoard= new BoardDAO();


    @Override
    public boolean update(Nameable elem) {
        boolean result=false;
        if(elem instanceof Card){
           result= dataCard.update((Card) elem);
        }
        else if(elem instanceof Column){
            result=dataColumn.update((Column) elem);
        }
        else if(elem instanceof Board){
            result=dataBoard.update((Board) elem);
        }
        return result;
    }

    @Override
    public void deleteAll(Nameable elem) { }
    @Override
    public boolean create(Nameable elem) {
        return false;
    }
    @Override
    public boolean delete(Nameable elem) {
        return false;
    }
    @Override
    public Nameable find(int i) {
        return null;
    }


}
