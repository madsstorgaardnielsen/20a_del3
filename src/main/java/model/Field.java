package model;
import model.*;
import view.*;
import controller.*;
public abstract class Field {

    private String name;
    protected boolean goToJail;

    Field(String name, boolean goToJail) {
        this.name = name;
        this.goToJail = goToJail;
    }

    Field(){
    }

    public abstract Field landOnField(Player player);

    public String getFieldName(int fieldNumber){
        return name;
    }

    public void setGoToJail(boolean jail) {
        goToJail = jail;
    }

    public boolean getGoToJail(){
        return goToJail;
    }
}



