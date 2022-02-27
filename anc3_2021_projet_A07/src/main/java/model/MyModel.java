package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class MyModel<E> extends Nameable{

    public enum ToMove {
        UP,DOWN,RIGHT,LEFT
    }

    private List<E> list;

    MyModel(List<E> list,String name){
        super(name);
        this.list=new ArrayList<E>();
    }


    public boolean NoMoveLeftOrUp(E elem){
        return (getIndex(elem) == 0);
    }

    public boolean NoMoveRightOrDown(E elem){
        return (getIndex(elem) == getSize()-1);
    }

    List<E> getList(){
        return Collections.unmodifiableList(list);
    }

    int getSize(){
        if(list==null){
            return 0;
        }
        return list.size();
    }

    boolean addElem(E elem){
        return list.add(elem);
    }

    void addElemPos(E elem, int index){
        list.add(index,elem);
    }

    boolean removeElem(E elem){
        if(list.contains(elem)){
            list.remove(elem);
            return true;
        }
        return false;
    }

    protected E getElem(int index){
        if( index >=this.list.size() || index<0 ){
            return null;
        }
        return list.get(index);
    }

    public int getIndex(E elem){
        return list.indexOf(elem);
    }

    void switchPosition( int index1, int index2){
        E elemIndex1=list.get(index1);
        E elemIndex2=list.get(index2);
        list.set(index1,elemIndex2);
        list.set(index2,elemIndex1);
    }


}
