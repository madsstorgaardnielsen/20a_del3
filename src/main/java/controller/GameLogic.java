package controller;

import model.*;
import gui_fields.GUI_Field;
import gui_main.GUI;
import view.Board;

public class GameLogic {
    private Board gameBoard = new Board();
    private int[] tempDie = new int[4];
    private LanguageManager lang = new LanguageManager();
    private int numberOfPlayers;
    private GUI gui;
    private int turnController;
    private int turnCounter = 0;

    public GameLogic() {
        gui = gameBoard.initGUI();
        gameBoard.initPlayers();
        gameBoard.findStarter();
        gameBoard.initField(gameBoard.getStartingPlayer());
    }

    public void runGame() {
        numberOfPlayers = gameBoard.getNumberOfPlayers();
        turnController = gameBoard.getStarter();
        //Løkken der styrer spillets logik, den breaker når en spiller går bankrupt
        while (gameBoard.checkBankruptcy()) {
            for (int i = 0; i < numberOfPlayers; i++) {
                gameBoard.gui_player[i].setBalance(gameBoard.players[i].account.getBalance());
            }

            //virker ikke endnu...
            checkJailBool();

            //Placerer bilerne på start feltet.
            if (turnCounter == 0) {
                for (int i = 0; i < numberOfPlayers; i++) {
                    gui.getFields()[0].setCar(gameBoard.gui_player[i], true);
                }
            }
            turnCounter++;

            //Terninge knap i GUI
            gui.getUserButtonPressed("", lang.translate("rul") + " " + gameBoard.getPlayer(turnController).getPlayerName());
            Roll die = new Roll();

            //sætter terning i GUI, lægger facevalue til tempdie
            int facevalue = die.getRoll();
            tempDie[turnController] += facevalue;
            gui.setDie(facevalue);

            //placerer og fjerner biler
            gameBoard.carPlacement(tempDie[turnController], facevalue, turnController);

            //udskriver spillerens navn + slag
            String plname = gameBoard.players[turnController].getPlayerName();
            gui.showMessage(plname + " " + lang.translate("slog") + " " + facevalue);

            //Sørger for der ikke kommer out of bounds error når en spiller passerer start
            if (tempDie[turnController] >= 24) {
                tempDie[turnController] -= 23;
            }

            //deklarerer og instansierer gui_field med feltets nr som parameter
            GUI_Field gui_field;
            gui_field = gameBoard.getGuiField(tempDie[turnController] - 1);

            //setFieldOwnedBorder(gui_field);
            gameBoard.setFieldOwnedBorder(gui_field, turnController);

            //deklarerer og instansierer gui_field med feltets nr som parameter
            Field field;
            field = gameBoard.getField(tempDie[turnController] - 1);
            fieldOutcome(field);

            //kører du turn metoden, for spilleren der har tur.
            doTurn(gameBoard.players[turnController]);
        }
    }

    private void doTurn(Player player) {
        int numberOfPlayers = gameBoard.getNumberOfPlayers();

        //updaterer spillernes balance
        for (int i = 0; i < numberOfPlayers; i++) {
            gameBoard.gui_player[i].setBalance(gameBoard.players[i].account.getBalance());
        }
        if (player.account.getBalance() < 0) {
            gui.displayChanceCard(player.getPlayerName() + " " + lang.translate("hartabtspillet") + "!");
        }
        if (!gameBoard.checkBankruptcy()) {
            gui.displayChanceCard(findWinner());
        }

        //holder styr på variablen der afgører hvilken spiller der har turen.
        turnController = ++turnController % numberOfPlayers;
    }

    //afgører udfaldet alt efter hvilket felt en spiller lander på
    private void fieldOutcome(Field field) {
        //hvis en spiller lander på chancekort
        if (field instanceof ChanceCard) {
            ChanceCard card = ChanceCard.getRandomCard();
            String chancekortNavn = card.getFieldName(tempDie[turnController] - 1);
            String player = gameBoard.players[turnController].getPlayerName();
            gui.showMessage(player + " " + lang.translate("landedepaa") + " " + chancekortNavn);
            gameBoard.players[turnController].account.setBalance(gameBoard.players[turnController].account.getBalance() + card.getReward());
            gui.displayChanceCard(card.getMessage());
        } else {
            //Finder ud af om feltet er ejet, hvis ikke ejet = køb, hvis ejet, betal husleje samt udskriver beskeder til gui
            PropertyField propertyField = (PropertyField) field;
            if (propertyField.isOwned()) {
                String player = gameBoard.players[turnController].getPlayerName();
                String owner = propertyField.ownedBy.getPlayerName();
                int payment = propertyField.getPrice();
                if (!propertyField.owned) {
                    gui.showMessage(player + lang.translate("landtepåledigtfelt") + " " + propertyField.getFieldName(tempDie[turnController]) + " " + lang.translate("nukøbt") + " ");
                } else if (player.equals(owner)) {
                    gui.showMessage(player + " " + lang.translate("landedepaaegetfelt"));
                } else if (payment > 0) {
                    gui.showMessage(player + " " + lang.translate("landedepaafeltejetaf") + " " + owner + " " + lang.translate("ogskalbetale") + " " + payment + " " + lang.translate("til") + " " + owner);
                }
            }
            propertyField.landOnField(gameBoard.players[turnController]);
        }
    }

    //metode der finder vinderen når en person går bankeråt
    private String findWinner() {
        int winner = 0;
        int winningPlayerName = 0;
        if (numberOfPlayers == 2) {
            winner = Math.max(gameBoard.players[0].account.getBalance(), gameBoard.players[1].account.getBalance());
        } else if (numberOfPlayers == 3) {
            winner = Math.max(Math.max(gameBoard.players[0].account.getBalance(), gameBoard.players[1].account.getBalance()), gameBoard.players[2].account.getBalance());
        } else if (numberOfPlayers == 4) {
            winner = Math.max(Math.max(gameBoard.players[0].account.getBalance(), gameBoard.players[1].account.getBalance()), Math.max(gameBoard.players[2].account.getBalance(), gameBoard.players[3].account.getBalance()));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            if (winner == gameBoard.players[i].account.getBalance()) {
                winningPlayerName = i;
            }
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            if (winner == gameBoard.players[i].account.getBalance()) {
                return lang.translate("uafgjort") + " " + gameBoard.players[winningPlayerName].getPlayerName() + " " + lang.translate("og") + " " + gameBoard.players[i].getPlayerName();
            }
        }
        return gameBoard.players[winningPlayerName].getPlayerName() + " " + lang.translate("harvundetspilletmed") + " " + gameBoard.players[winningPlayerName].account.getBalance() + " " + lang.translate("pengeibanken");
    }

    //En spiller med true goToJail boolean bliver "sprunget over"
    public void checkJailBool() {
        if (turnCounter >= 2) {
            if (gameBoard.players[turnController].getPlayerGoToJail()) {
                gui.displayChanceCard(lang.translate("duerifangesel"));
                gameBoard.players[turnController].setPlayerGoToJail(false);
                doTurn(gameBoard.players[++turnController % numberOfPlayers]);
            }
        }
    }
}