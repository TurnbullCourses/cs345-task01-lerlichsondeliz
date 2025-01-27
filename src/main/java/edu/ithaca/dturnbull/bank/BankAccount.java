package edu.ithaca.dturnbull.bank;

import java.net.InetAddress;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email == null || email.isEmpty()) {
            return false;
        }
    
        // Regex, used this in python a while ago
        String emailRegex = "^[a-zA-Z0-9]+([._-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$";
        if (!email.matches(emailRegex)) {
            return false;
        }

        // Extract domain from the email address
        String domain = email.substring(email.indexOf('@') + 1);

        // Perform DNS lookup to check if the domain exists
        try {
            InetAddress.getByName(domain); // Will throw an exception if the domain doesn't exist
            return true;
        } catch (Exception e) {
            return false; // Domain does not exist
        }
    }
}