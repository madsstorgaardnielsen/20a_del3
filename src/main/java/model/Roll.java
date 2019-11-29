package model;

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
