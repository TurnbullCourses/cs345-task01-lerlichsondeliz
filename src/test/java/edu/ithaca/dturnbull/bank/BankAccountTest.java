package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("lukas@gmail.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("example@gmail.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        BankAccount bankAccount2 = new BankAccount("lukasdeliz@gmail.com", 400);
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(0));
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(500));
    }

    @Test
    void isEmailValidTest(){
        // Base Test, meant to pass
        assertTrue(BankAccount.isEmailValid("good_example22@gmail.com"));
        // AF contains non a-z character
        assertFalse(BankAccount.isEmailValid("sneaky_exæmple111@gmail.com"));
        // AF contains an invalid character (not underscore, letter, number, or period)
        assertFalse(BankAccount.isEmailValid("this_is#invalid@gmail.com"));
        // AF an underscore, period, etc. is not followed by a letter or number (repeats)
        assertFalse(BankAccount.isEmailValid("bad..example_email@gmail.com"));

        // AT valid domain
        assertTrue(BankAccount.isEmailValid("good_example@gmail.com"));
        // AF domain has invalid symbol
        assertFalse(BankAccount.isEmailValid("invalid_domain@gmail#.com"));
        // AF domain has two periods in a row
        assertFalse(BankAccount.isEmailValid("mistyped_email_address@gmail..com"));
        // AF domain is missing TLD/TLD not long enough
        assertFalse(BankAccount.isEmailValid("missing_tld@gmail"));
        assertFalse(BankAccount.isEmailValid("tld_too_short@gmail.c"));
        // AF not a real domain
        // I think doobie might be real... I tried other fake domains and the test passed.
        assertFalse(BankAccount.isEmailValid("what_domain_is_that@nonexistentdomain12345.com"));

        // AF domain is on left side of @
        assertFalse(BankAccount.isEmailValid("gmail.com@wrong_way"));
        // AF has no @
        assertFalse(BankAccount.isEmailValid("missing_at_sign.com"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("lukasdeliz@gmail.com", 200);

        assertEquals("lukasdeliz@gmail.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("lukas@gmail.com", -8));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("led@gmail.com", 8.164));
        

    }
    @Test
    public void isAmountValidTest() {
        // valid tests
        assertTrue(BankAccount.isAmountValid(10.00));
        assertTrue(BankAccount.isAmountValid(0.01));
        
        // invalid tests 
        assertFalse(BankAccount.isAmountValid(-10.00)); // negative
        assertFalse(BankAccount.isAmountValid(10.001)); // more than 2 decimals
        //assertFalse(BankAccount.isAmountValid(0)); // 0, I don't know if I want this yet
    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("lukas@gmail.com", 100.00);

        // valid deposits
        bankAccount.deposit(50.00);
        assertEquals(150.00, bankAccount.getBalance(), 0.001);

        bankAccount.deposit(0.01);
        assertEquals(150.01, bankAccount.getBalance(), 0.001);

        // invalid deposits
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0));    // Deposit 0
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10)); // Negative deposit
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(10.001)); // More than two decimal places
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount sender = new BankAccount("lukasdeliz@gmail.com", 16.00);
        BankAccount recipient = new BankAccount("lerlichsondeliz@ithaca.edu", 4.00);

        // valid transfers
        sender.transfer(8.00, recipient);
        assertEquals(8.00, sender.getBalance(), 0.001);
        assertEquals(12.00, recipient.getBalance(), 0.001);

        sender.transfer(4.00, recipient);
        assertEquals(4.00, sender.getBalance(), 0.001);
        assertEquals(16.00, recipient.getBalance(), 0.001);

        // invalid transfers
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(0, recipient));  // Amount is 0
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(-8, recipient)); // Negative amount
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(8.164, recipient)); // More than two decimals
        assertThrows(InsufficientFundsException.class, () -> sender.transfer(20, recipient)); // Not enough balance
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(4, null)); // Null recipient
    }
}