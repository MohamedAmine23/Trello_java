package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;


public final class Column extends MyModel{

    private int index;//index de la carte
    private Board myBoard;
    private static DAO<Card> dataCard= new CardDAO();
    private static DAO<Column>dataColumn= new ColumnDAO();
    private int idColumn;
    private static int instCol=0;//nombre d'instance de Column

     void setIdColumn(int idColumn) {
        this.idColumn = idColumn;
    }

    public Column(String name) {
        super(new ArrayList<Card>(),name);instCol++;
        this.idColumn=instCol;
    }

    public Column(String name,Board board){
        this(name);
        this.myBoard=board;
    }
    public Board getMyBoard() {
        return myBoard;
    }
    public static int getInstCol() {
        return instCol;
    }

    int getIdColumn() {
        return idColumn;
    }

    public Card getCard(int index){
        return (Card) super.getElem(index);
    }

    public List<Card> getCards(){
        return super.getList();
    }

    public boolean removeCardFromColumn(Card c){
        index=this.getIndex(c);
        Boolean res= this.removeElem(c)&&dataCard.delete(c);
        for(Card card: this.getCards()){
            dataCard.update(card);
        }
        return res;
    }

    public boolean addCardToColumn(Card c){
        return this.addElem(c)&&dataCard.create(c)&&dataCard.update(c);
    }

    public boolean addCardToColumnAfterRemove(Card c){
        this.addElemPos(c,index);dataCard.create(c);dataCard.update(c);
        return true;
    }

    public Boolean moveCard(Card c, ToMove m){

        int indexColumn=myBoard.getIndex(this);
        int indexCard=getIndex(c);
        boolean result=false;
        switch (m){
            case LEFT:

                if(indexColumn==0 ){
                    break;
                }else{

                    removeElem(c);//suppression en DB
                    Column leftColumn=myBoard.getColumn(indexColumn-1);

                    if(leftColumn.getSize()>=1 && indexCard<= leftColumn.getSize()-1){
                       leftColumn.addElemPos(c,indexCard);
                   }else {
                        leftColumn.addElem(c);
                    }
                    c.setMyColumn(leftColumn);
                    result=true;//modification en DB
               }
                break;
            case RIGHT:

                if(indexColumn==this.myBoard.getSize()-1){
                    break;
                }else{
                    removeElem(c);//suppression en DB
                    Column rightColumn=this.myBoard.getColumn(indexColumn+1);

                    if(rightColumn.getSize()>=1 && indexCard<= rightColumn.getSize()-1){
                        rightColumn.addElemPos(c,indexCard);
                    }
                    else{
                        rightColumn.addElem(c);
                    }
                    c.setMyColumn(rightColumn);
                    result=true;//modification en DB
               }
                break;

                case DOWN:

                    if (indexCard == 0 && indexCard + 1 >= getSize()) {
                        break;
                    } else {
                        switchPosition(indexCard, indexCard + 1);
                        result = true;
                    }
                    break;

                case UP:

                    if (indexCard == 0 && indexCard - 1 < 0) {
                        break;
                    } else {
                        switchPosition(indexCard, indexCard - 1);
                        result = true;
                    }
                    break;
        }
       for(Column col : this.getMyBoard().getColumns()){
           for(Card card : col.getCards()){
                dataCard.update(card);dataColumn.update(col);
           }
       }
        return result;
    }


}


