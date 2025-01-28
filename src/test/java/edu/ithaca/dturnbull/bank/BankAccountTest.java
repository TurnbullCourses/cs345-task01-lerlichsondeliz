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
        // I think goobie might be real... I tried other fake domains and the test passed.
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
    }

}