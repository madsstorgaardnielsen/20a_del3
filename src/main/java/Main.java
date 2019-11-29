import controller.GameLogic;
import model.LanguageManager;

public class Main {
    public static void main(String[] args) {
        LanguageManager language = new LanguageManager();
        language.selectLanguage();

        GameLogic game = new GameLogic();
        game.runGame();
    }
}
