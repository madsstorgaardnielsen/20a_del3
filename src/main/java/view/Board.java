package view;

import model.*;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class Board {
    private GUI_Field[] fields = new GUI_Field[24];
    private GUI gui;
    private Field[] fieldArray = new Field[24];
    private LanguageManager lang = new LanguageManager();
    private Color[] colors = new Color[]{Color.red, Color.BLACK, Color.blue, Color.GREEN};
    private int numberOfPlayers;
    private int startingPlayer;
    public GUI_Player[] gui_player = new GUI_Player[4];
    public Player[] players;

    //**OBS konstruktører findes i bunden af klassen**

    //sætter antallet af spillere på GUI vha setPlayers og setNumberOfPlayers metoderne.
    public void initPlayers() {
        setPlayers(setNumberOfPlayers());
    }

    //beder brugeren om et antal spillere, udskriver fejl meddelelse hvis der indtastes for få eller for mange spilelre
    public int setNumberOfPlayers() {
        numberOfPlayers = gui.getUserInteger(lang.translate("indtastantalspillere"));
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            gui.showMessage(lang.translate("indtast2til4spillere"));
            numberOfPlayers = gui.getUserInteger(lang.translate("indtastantalspillere"));
        }
        return numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Player getStartingPlayer() {
        return players[startingPlayer];
    }

    //initialiserer alle spillerne og dertilhørende informationer
    public void setPlayers(int numberOfPlayers) {
        int startBalance;
        startBalance = initStartBalance();

        players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player();
            players[i].setPlayerName(gui);
            players[i].setPlayerAge(gui);
            players[i].account.setBalance(startBalance);
            gui_player[i] = new GUI_Player(players[i].getPlayerNameAndType(i), players[i].account.getBalance());
            gui_player[i].getCar().setPrimaryColor(colors[i]);
            gui.addPlayer(gui_player[i]);
        }
    }

    public Player getPlayer(int playerNumber) {
        return players[playerNumber];
    }

    //beregner start balance ud fra antallet af spillere
    public int initStartBalance() {
        int startBalance;
        if (numberOfPlayers == 2) {
            startBalance = 20;
        } else if (numberOfPlayers == 3) {
            startBalance = 18;
        } else startBalance = 16;
        return startBalance;
    }

    //finder den yngste spiller, og sætter ham til starter
    public void findStarter() {
        int ageOfYoungestPlayer;
        if (numberOfPlayers == 2) {
            ageOfYoungestPlayer = Math.min(players[0].getPlayerAge(), players[1].getPlayerAge());
        } else if (numberOfPlayers == 3) {
            ageOfYoungestPlayer = Math.min(Math.min(players[0].getPlayerAge(), players[1].getPlayerAge()), players[2].getPlayerAge());
        } else
            ageOfYoungestPlayer = Math.min(Math.min(players[0].getPlayerAge(), players[1].getPlayerAge()), Math.min(players[2].getPlayerAge(), players[3].getPlayerAge()));

        for (int i = 0; i < numberOfPlayers; i++) {
            if (ageOfYoungestPlayer == players[i].getPlayerAge()) {
                startingPlayer = i;
            }
        }
    }

    public int getStarter() {
        return startingPlayer;
    }

    //checker alle spiller objekterne i spiller array listens balance og returnere false hvis en af spillerne har mindre end 0
    public boolean checkBankruptcy() {
        for (Player player : players) {
            if (player.account.getBalance() < 0) {
                return false;
            }
        }
        return true;
    }

    //placerer og fjerner biler fra boarded
    public void carPlacement(int totalDieValue, int facevalue, int turnController) {

        //sørger for at der kun er 1 bil på banen pr spiller.
        int deleteCarFromField = totalDieValue - facevalue;
        for (int i = 0; i <= deleteCarFromField; i++) {
            gui.getFields()[i].setCar(gui_player[turnController], false);
        }

        //sørger for at bilerne kan krydse start uden array out of bounds error
        if (totalDieValue == 0 && facevalue == 0) {
            gui.getFields()[(totalDieValue)].setCar(gui_player[turnController], true);
        } else if (totalDieValue >= 24) {
            totalDieValue -= 23;
            gui.getFields()[(totalDieValue - 1)].setCar(gui_player[turnController], true);

            //spiller får 2 penge når starte passeres.
            players[turnController].account.setBalance(players[turnController].account.getBalance() + 2);
        } else {
            gui.getFields()[(totalDieValue - 1)].setCar(gui_player[turnController], true);
        }
    }

    //Sætter et hotel og ændrer rammen på felt når det købes.
    public void setFieldOwnedBorder(GUI_Field gui_field, int turnController) {
        if (gui_field instanceof GUI_Street) {
            GUI_Street street = (GUI_Street) gui_field;
            street.setBorder(Color.BLACK, Color.red);
            street.setSubText(lang.translate("ejesaf") + " " + String.valueOf(players[turnController].getPlayerName()));
        }
    }

    //Kontruktør der instanisierer spilbrættet når et board objekt deklareres
    public Board() {
        fields[0] = new GUI_Start(lang.translate("start"), "", lang.translate("startfeltet"), Color.GREEN, Color.BLACK);
        fields[1] = new GUI_Street(lang.translate("gadekøkkenet"), lang.translate("pris") + " " + "1", "", "M1", Color.getHSBColor(139, 69, 19), Color.BLACK);
        fields[2] = new GUI_Street(lang.translate("pizzahuset"), lang.translate("pris") + " " + "1", "", "M1", Color.getHSBColor(139, 69, 19), Color.BLACK);
        fields[3] = new GUI_Chance(lang.translate("chancekort"), lang.translate("spændende1"), "", Color.WHITE, Color.BLACK);
        fields[4] = new GUI_Street(lang.translate("slikbutikken"), lang.translate("pris") + " " + "1", "", "M1", Color.CYAN, Color.BLACK);
        fields[5] = new GUI_Street(lang.translate("iskiosken"), lang.translate("pris") + " " + "1", "", "M1", Color.CYAN, Color.BLACK);
        fields[6] = new GUI_Jail("default", lang.translate("påbesøgfængsel"), lang.translate("påbesøgfængsel"), "", Color.BLACK, Color.WHITE);
        fields[7] = new GUI_Street(lang.translate("museet"), lang.translate("pris") + " " + "2", "", "M2", Color.magenta, Color.BLACK);
        fields[8] = new GUI_Street(lang.translate("biblioteket"), lang.translate("pris") + " " + "2", "", "2", Color.magenta, Color.BLACK);
        fields[9] = new GUI_Chance(lang.translate("chancekort"), lang.translate("spændende2"), "", Color.WHITE, Color.BLACK);
        fields[10] = new GUI_Street(lang.translate("skaterparken"), lang.translate("pris") + " " + "2", "", "2", Color.orange, Color.BLACK);
        fields[11] = new GUI_Street(lang.translate("swimmingpoolen"), lang.translate("pris") + " " + "2", "", "2", Color.orange, Color.BLACK);
        fields[12] = new GUI_Refuge("default", lang.translate("gratisparkering"), lang.translate("gratisparkering"), "", Color.WHITE, Color.BLACK);
        fields[13] = new GUI_Street(lang.translate("spillehallen"), lang.translate("pris") + " " + "3", "", "3", Color.red, Color.BLACK);
        fields[14] = new GUI_Street(lang.translate("biografen"), lang.translate("pris") + " " + "3", "", "3", Color.red, Color.BLACK);
        fields[15] = new GUI_Chance(lang.translate("chancekort"), lang.translate("spændende3"), "", Color.WHITE, Color.BLACK);
        fields[16] = new GUI_Street(lang.translate("legetøjsbutikken"), lang.translate("pris") + " " + "3", "", "3", Color.yellow, Color.BLACK);
        fields[17] = new GUI_Street(lang.translate("dyrehandlen"), lang.translate("pris") + " " + "3", "", "3", Color.yellow, Color.BLACK);
        fields[18] = new GUI_Jail("default", lang.translate("fængslet"), lang.translate("fængslet"), "", Color.BLACK, Color.WHITE);
        fields[19] = new GUI_Street(lang.translate("bowlinghallen"), lang.translate("pris") + " " + "4", "", "4", Color.green, Color.BLACK);
        fields[20] = new GUI_Street(lang.translate("zoo"), lang.translate("pris") + " " + "4", "", "4", Color.green, Color.BLACK);
        fields[21] = new GUI_Chance(lang.translate("chancekort"), lang.translate("spændende4"), "", Color.WHITE, Color.BLACK);
        fields[22] = new GUI_Street(lang.translate("vandlandet"), lang.translate("pris") + " " + "4", "", "5", Color.blue, Color.BLACK);
        fields[23] = new GUI_Street(lang.translate("strandpromenaden"), lang.translate("pris") + " " + "4", "", "5", Color.blue, Color.BLACK);
    }

    //returnrere gui objektet så spilbrættet kan initialiseres
    public GUI initGUI() {
        gui = new GUI(fields, Color.white);
        return gui;
    }

    public GUI_Field getGuiField(int fieldNumber) {
        return fields[fieldNumber];
    }

    //felt array der har parametre som udgør udfaldet for det felt en spiller lander på
    public void initField(Player player) {
        fieldArray[0] = new PropertyField("Start", 0, false, player, false);
        fieldArray[1] = new PropertyField("Gadekøkkenet", 1, false, player, false);
        fieldArray[2] = new PropertyField("Pizzahuset", 1, false, player, false);
        fieldArray[3] = new ChanceCard();
        fieldArray[4] = new PropertyField("Godtebutik", 1, false, player, false);
        fieldArray[5] = new PropertyField("Iskiask", 1, false, player, false);
        fieldArray[6] = new PropertyField("Besøg i fængsel", 0, false, player, false);
        fieldArray[7] = new PropertyField("Museet", 2, false, player, false);
        fieldArray[8] = new PropertyField("Biblio", 2, false, player, false);
        fieldArray[9] = new ChanceCard();
        fieldArray[10] = new PropertyField("Skaterpark", 2, false, player, false);
        fieldArray[11] = new PropertyField("Rulleskøjte", 2, false, player, false);
        fieldArray[12] = new PropertyField("Gratis parkering", 0, false, player, false);
        fieldArray[13] = new PropertyField("spillehallen", 2, false, player, false);
        fieldArray[14] = new PropertyField("biffen", 2, false, player, false);
        fieldArray[15] = new ChanceCard();
        fieldArray[16] = new PropertyField("legetøjsbutikken", 3, false, player, false);
        fieldArray[17] = new PropertyField("dyrehandlen", 3, false, player, false);
        fieldArray[18] = new PropertyField("gå i fængsel", 0, false, player, true);
        fieldArray[19] = new PropertyField("bowling", 4, false, player, false);
        fieldArray[20] = new PropertyField("zoo", 4, false, player, false);
        fieldArray[21] = new ChanceCard();
        fieldArray[22] = new PropertyField("vandland", 5, false, player, false);
        fieldArray[23] = new PropertyField("stranpromenaden", 5, false, player, false);
    }

    public Field getField(int fieldNumber) {
        return fieldArray[fieldNumber];
    }
}
