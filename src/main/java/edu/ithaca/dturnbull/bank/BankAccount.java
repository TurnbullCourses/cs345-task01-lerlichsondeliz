package edu.ithaca.dturnbull.bank;

import java.net.InetAddress;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
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
     * @throws IllegalArgumentException if amount is <= 0 
     * @throws InsufficientFundsException if amount > balance 
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid amount. Must be positive and have at most two decimal places.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough money");
        }
        balance -= amount;
        // if (amount <= balance){
        //     balance -= amount;
        // }
        // else {
        //     throw new InsufficientFundsException("Not enough money");
        // }
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
    /**
     * @return true if the amount is positive and has two decimal points or less, and false otherwise
     */

     public static boolean isAmountValid(double amount){
        if (amount <= 0) {
            return false;
        }
        return Math.round(amount * 100) / 100.0 == amount;
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param amount the amount to deposit
     * @post increases the balance by the amount
     * @throws IllegalArgumentException if the amount is invalid
     */
    public void deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount. Must be positive and have at most two decimal places.");
        }
        balance += amount;
    }

    /**
     * Transfers a specified amount from this account to another account.
     *
     * @param amount the amount to transfer
     * @param recipient the account to receive the transfer
     * @post decreases this account's balance by the amount and increases the recipient's balance by the same amount
     * @throws IllegalArgumentException if the amount is invalid or the recipient is null
     * @throws InsufficientFundsException if this account does not have enough balance
     */
    public void transfer(double amount, BankAccount recipient) throws InsufficientFundsException{
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid transfer amount. Must be positive and have at most two decimal places.");
        }
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient account cannot be null.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough funds to complete the transfer.");
        }
    
        this.withdraw(amount);
        recipient.deposit(amount);
    }
}