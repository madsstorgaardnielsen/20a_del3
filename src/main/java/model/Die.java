package model;
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
