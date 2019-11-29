package model;

import java.util.Random;

import view.*;

public class ChanceCard extends Field {
    private String message;
    private int reward;
    private int position;
    static Board getNumberOfPlayers = new Board();

    private static LanguageManager lang = new LanguageManager();

    private static ChanceCard[] cards = new ChanceCard[]{

            //chancekort nr 7
            //Du har spist for meget slik, betal M2 til banken
            new ChanceCard(lang.translate("chance1"), -2, 0, lang.translate("chancekort"), false),

            //chancekort nr 14
            //det er din fødselsdag, alle spillere giver dig M1

            new ChanceCard(lang.translate("tillykkefodselsdag"), (getNumberOfPlayers.getNumberOfPlayers()-1), 0, "chancekort", false),

            //chancekort nr 16
            //du har lavet alle dine lektier, modtag M2 fra banken
            new ChanceCard(lang.translate("lavetlektier"),2,0,"Chancekort",false),
    };

    public ChanceCard(String message, int reward, int position, String name, boolean goToJail) {
        super(name, goToJail);
        this.message = message;
        this.reward = reward;
        this.position = position;
    }

    public ChanceCard() {
        super();
    }

    public static ChanceCard getRandomCard() {
        int i = new Random().nextInt(cards.length);
        return cards[i];
    }

    public int getReward() {
        return reward;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Field landOnField(Player player) {
        ChanceCard card = ChanceCard.getRandomCard();
        player.account.getPaid(card.getReward());
        return this;
    }
}























