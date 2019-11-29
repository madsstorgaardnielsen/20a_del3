package model;
import model.*;
import view.*;
import controller.*;
public class Roll {
    public Die die;

    public Roll(){
        die = new Die();
        die.rollDie();
    }

    public int getRoll(){
        int roll = die.getFaceValue();
        return roll;
    }
}
