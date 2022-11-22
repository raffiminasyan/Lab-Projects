import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class Account implements Serializable {
    private String name;
    private String lastName;
    private String accountNumber;
    Random number = new Random();
    private double balance;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        int num = (int) ((Math.random() * (1999999999 - 900000)) + 900000);
        accountNumber = Integer.toString(num);
        this.balance = 0;
        System.out.println("An account has been set up for " + name + " " + lastName + " with a account number of " + accountNumber);
    }

    public double checkBalance() {
        return balance;
    }

    public void transfer(double amount, String accNumber, HashMap<String, Account> accounts) {
        Account target = accounts.get(accNumber);
        if (balance >= amount) {
            balance -= amount;
            target.setBalance(target.checkBalance() + amount);
            System.out.println("Funds successfully transferred.");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void addAmount(double amount) {
        balance = balance + amount;
    }

    public void withdraw(double amount) {
        if (amount < 0 || amount == 0) {
            System.out.println("Insert a valid number");
        } else if (amount < balance) {
            balance = balance - amount;
        } else {
            System.out.println("You do not have enough money");
        }
    }


    public String getFullName() {
        return name + " " + lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
