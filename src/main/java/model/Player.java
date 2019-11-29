package model;

import gui_main.GUI;

public class Player {
    public boolean goToJail;
    LanguageManager lang = new LanguageManager();
    private String playerName;
    public int playerAge;
    public Account account;
    String[] spillerTypeArray = new String[]{lang.translate("hunden"), lang.translate("katten"), lang.translate("katten"), lang.translate("skibet")};
    //public boolean goToJail = false;

    public Player() {
        account = new Account();
    }

    public void setPlayerName(GUI gui) {
        playerName = gui.getUserString(lang.translate("indtastnavn"));
        while (playerName.length() > 25) {
            gui.showMessage(lang.translate("indtastunder25karakterer"));
            playerName = gui.getUserString(lang.translate("indtastnavn"));
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerNameAndType(int playerNumber) {
        return playerName + spillerTypeArray[playerNumber];
    }

    public void setPlayerAge(GUI gui) {
        playerAge = gui.getUserInteger(lang.translate("indtastalder"), 5, 99);

        while (playerAge < 5 || playerAge >= 99) {
            gui.showMessage(lang.translate("mellem5og99aar"));
            playerAge = gui.getUserInteger(lang.translate("indtastalder"), 5, 99);
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
