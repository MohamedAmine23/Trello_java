package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class Board extends MyModel {

    private int index;
    private static final DAO<Column> dataColumn= new ColumnDAO();
    private static final DAO<Card> dataCard= new CardDAO();
    private final int idBoard;
    private static int instBoard=0;//nombre d'instance de Column
    private final Connection connect= ConnectionSqlite.getConnect();
    private List<Column> deleteCol;

    public Board(String name){
        super(new ArrayList<Column>(),name);
        instBoard++;
        this.idBoard=instBoard;
        this.deleteCol=new ArrayList<>();
        LoadData(connect);
    }

    public List<Column> getDeleteCol() {
        return deleteCol;
    }

    int getIdBoard() {
        return idBoard;
    }

    public Column getColumn(int index){

        return (Column) super.getElem(index);
    }
    public List<Column> getColumns(){

        return  super.getList();
    }
    public boolean addColumnToBoard(Column c){
        return this.addElem(c)&&dataColumn.create(c)&& dataColumn.update(c);

    }
    public boolean addColumnAfterRemove(Column c){
        this.addElemPos(c,index);dataColumn.create(c);refreshColumn();
        for(Card card : c.getCards()){ dataCard.create(card);dataCard.update(card);

        }
        return true;
    }
    public boolean addColumnAfterRemoveSelected(Column c,int index){
        this.addElemPos(c,index);dataColumn.create(c);refreshColumn();
        for(Card card : c.getCards()){ dataCard.create(card);dataCard.update(card);

        }

        return true;
    }
    public boolean removeColumnToBoard(Column c){
        index=this.getIndex(c);
        boolean res= this.removeElem(c)&& dataColumn.delete(c) && refreshColumn();

        return res;

    }
    private boolean refreshColumn(){
        Boolean res= false;
        for(Column col : this.getColumns()){
           res= dataColumn.update(col);
        }
        return res;
    }

    public boolean moveColumn(Column c, ToMove m){
        int index=0;
        boolean result=false;

        switch(m){

            case RIGHT:
                index = getIndex(c);
                if(index==0 && index+1>= getSize()){
                    break;
                }else{
                    switchPosition(index,index+1);
                    result=dataColumn.update(c);
                }
                break;

            case LEFT:

                index = getIndex(c);
                if(index==0 && index-1<0){
                    break;
                }else{
                    switchPosition(index,index-1);
                    result= dataColumn.update(c);
                }
                break;
        }
        for(Column col : getColumns()){
            dataColumn.update(col);
        }
        return result;
    }
    private void LoadData(Connection connect) {
        Statement statement2=null;
        Statement statement3=null;
        ResultSet resultCard=null;
        ResultSet resultColumn=null;
        try{

            String sqlColumn = "SELECT * FROM Column ORDER BY position Asc;";
            statement2 = connect.createStatement();
            resultColumn = statement2.executeQuery(sqlColumn);
            while (resultColumn.next()) {
                String nameColumn = resultColumn.getString("name");
                int  idCol=resultColumn.getInt("idColumn");
                Column column=new Column(nameColumn,this);
                column.setIdColumn(idCol);
                this.addElem(column);

                String sqlCard = "SELECT * FROM Card ORDER BY position Asc;";
                statement3 = connect.createStatement();
                resultCard = statement3.executeQuery(sqlCard);
                while (resultCard.next()) {
                    String nameCard = resultCard.getString("name");
                    int idColumn= resultCard.getInt("idColumn");
                    if( column.getIdColumn()==idColumn){
                        Card card=new Card(nameCard,column);
                        column.addElem(card);
                    }
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if ( resultCard != null )
                try {
                    resultCard.close();
                } catch ( SQLException ignore ) { }
            if ( statement3 != null )
                try {
                    statement3.close();
                } catch ( SQLException ignore ) { }
            if (resultColumn != null )
                try {
                    resultColumn.close();
                } catch ( SQLException ignore ) { }
            if ( statement2 != null )
                try {
                    statement2.close();
                } catch ( SQLException ignore ) { }
            if ( connect != null )
                try {
                    connect.close();
                } catch ( SQLException ignore ) { }
        }
    }


}
