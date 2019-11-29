package model;

import model.*;
import view.*;
import controller.*;
import gui_main.GUI;

public class Player {
    public boolean goToJail;
    LanguageManager lang = new LanguageManager();
    private String playerName;
    public int playerAge;
    public Account account;
    String[] spillerTypeArray = new String[]{lang.textReader("hunden"), lang.textReader("katten"), lang.textReader("katten"), lang.textReader("skibet")};
    //public boolean goToJail = false;

    public Player() {
        account = new Account();
    }

    public void setPlayerName(GUI gui) {
        playerName = gui.getUserString(lang.textReader("indtastnavn"));
        while (playerName.length() > 25) {
            gui.showMessage(lang.textReader("indtastunder25karakterer"));
            playerName = gui.getUserString(lang.textReader("indtastnavn"));
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerNameAndType(int playerNumber) {
        return playerName + spillerTypeArray[playerNumber];
    }

    public void setPlayerAge(GUI gui) {
        playerAge = gui.getUserInteger(lang.textReader("indtastalder"), 5, 99);

        while (playerAge < 5 || playerAge >= 99) {
            gui.showMessage(lang.textReader("mellem5og99aar"));
            playerAge = gui.getUserInteger(lang.textReader("indtastalder"), 5, 99);
        }
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public void setPlayerGoToJail(boolean jail) {
        goToJail = jail;
    }

    public boolean getPlayerGoToJail() {
        return goToJail;
    }


}
