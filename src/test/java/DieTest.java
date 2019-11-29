import model.Die;
import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {

    @Test
    public void rollDie() {
        Die die = new Die();
        boolean state = true;

        for (int i = 0; i < 1000; i++) {
            if (!(die.rollDie() >= 1 && die.rollDie() <=6)){
                state = false;
            }
        }

        assertTrue(state);
    }
}