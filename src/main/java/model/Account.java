package model;
import model.*;
import view.*;
import controller.*;
public class Account {
    public int balance;

    public Account() {
        balance = 0;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void getPaid(int value) {
        setBalance(getBalance() + value);
    }
}
