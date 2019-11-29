import model.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    Account account = new Account();

    @Test
    public void setBalance() {
        account.setBalance(5);

        assertEquals(account.balance, 5);
    }

    @Test
    public void getBalance() {
        setBalance();
        assertEquals(account.getBalance(), 5);
    }
}