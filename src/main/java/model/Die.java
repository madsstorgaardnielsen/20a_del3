package model;
import model.*;
import view.*;
import controller.*;
public class Die {
    private int faceValue;

    public int rollDie() {
        faceValue = (int) (Math.random() * 6 + 1);
        return faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }
}
