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

            //chancekort nr 1
            //giv dette kort til bilen og træk endnu et chancekort
            //på næste tur placer bilen på et ønsket felt og køb det, hvis ingen er ledige kan der købes en anden spillers ejendom
            //new model.ChanceCard("giv dette kort til bilen og træk endnu et chancekort, på næste tur placer bilen på et ønsket felt og køb det, hvis ingen er ledige kan der købes en anden spillers ejendom", 0, false,0),

            //chancekort nr 2
            //ryk til start og modtag M2
            //new model.ChanceCard("Ryk til start og modtag M2", 2, false,1),

            //chancekort nr 3
            //ryk op til 5 felter frem
            //new model.ChanceCard(),

            //chancekort nr 4
            //Ryk frem til et orange felt, hvis det er ledigt får du det, hvis ikke skal der betales husleje
            //new model.ChanceCard(),

            //chancekort nr 5
            //ryk et felt frem eller tag et chancekort mere
            //new model.ChanceCard(),

            //chancekort nr 6
            //giv dette kort til skibet og træk et chancekort mere. På skibets næste tur skal der rykkes til et hvilket som helst
            //ledigt felt og købe dette, hvis der ingen ledige felter er skal det købes af en anden spiller
            //new model.ChanceCard(),

            //chancekort nr 7
            //Du har spist for meget slik, betal M2 til banken
            new ChanceCard(lang.translate("chance1"), -2, 0, lang.translate("chancekort"), false),

            //chancekort nr 8
            //Ryk til et grønt eller orange felt, hvis ledigt får du det, ellers betal husleje
            //new model.ChanceCard(),

            //chancekort nr 9
            //ryk frem til et lyseblåt felt, hvis det er ledigt får det det, hvis det ejes betal husleje
            //new model.ChanceCard(),

            //chancekort nr 10
            //du løslades uden omkostninger, gem dette kort til der er brug for det
            //new model.ChanceCard(),

            //chancekort nr 11
            //Ryk frem til strandpromenaden
            //new model.ChanceCard(),

            //chancekort nr 12
            //giv dette kort til katten, på kattens tur går katten til et hvilket som helst felt og køber dette,
            //hvis ingen ledige, betal husleje der hvor du lander
            //new model.ChanceCard(),

            //chancekort nr 13
            //giv dette kort til hund, på hundens tur går hunden til et hvilket som helst felt og køber dette,
            //hvis ingen ledige, betal husleje der hvor du lander
            //new model.ChanceCard(),

            //chancekort nr 14
            //det er din fødselsdag, alle spillere giver dig M1

            new ChanceCard(lang.translate("tillykkefodselsdag"), (getNumberOfPlayers.getNumberOfPlayers()-1), 0, "chancekort", false),

            //chancekort nr 15
            //gå til pink eller blåt felt, få det gratis hvis ledigt, ellers betal husleje
            //new model.ChanceCard(),

            //chancekort nr 16
            //du har lavet alle dine lektier, modtag M2 fra banken
            new ChanceCard(lang.translate("lavetlektier"),2,0,"Chancekort",false),

            //chancekort nr 17
            //gå til rødt felt, få det gratis hvis ledigt, ellers betal husleje
            //new model.ChanceCard(),

            //chancekort nr 18
            //gå til skaterparken, få det gratis hvis ledigt, ellers betal husleje
            //new model.ChanceCard(),

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























