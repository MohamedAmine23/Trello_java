package model;


public final class Card extends Nameable  {
    private final int idCard;
    private Column myColumn;
    private static int instCard=0;//nombre d'instance de carte

    public Card(String name){
        super(name);
        ++instCard;
        idCard=instCard;
    }

    public Card(String name,Column column){
        this(name);
        this.myColumn=column;
    }

    void setMyColumn(Column myColumn) {
        this.myColumn = myColumn;
    }

    public static int getInstCard(){
        return instCard;
    }

    public Column getMyColumn() {
        return myColumn;
    }

    int getIdCard() {
        return idCard;
    }


}
