package model;

public abstract class Nameable {
    private String name;
    private static DAO<Nameable> dataNameable= new NameableDAO();

    public Nameable(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        dataNameable.update(this);
    }

    @Override
    public String toString() {
        return  name ;
    }


}

