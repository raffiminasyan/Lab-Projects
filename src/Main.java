import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//411872484
//784568867
//358072056
//892181403

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("For the creation of a new account: press 0" + "\n" +
                "In order to access account: press 1");

        Scanner scan = new Scanner(System.in);
        boolean flag = false;

        Account account = null;
        HashMap<String, Account> accounts = null;


        int toDo;
        toDo = scan.nextInt();
        if (toDo == 1) {
            try {
                if (isTextFileEmpty("Data.txt")) {
                    System.out.println("Account with entered account number does not exist");
                    System.exit(1);
                } else {
                    System.out.println("Enter your account number");
                    String enteredAccount = scan.next();
                    ObjectInputStream read = new ObjectInputStream(new FileInputStream("Data.txt"));
                    accounts = (HashMap<String, Account>) read.readObject();
                    read.close();
                    if (!accounts.containsKey(enteredAccount)) {
                        System.out.println("Account with entered account number does not exist");
                        System.exit(1);
                    } else {
                        account = accounts.get(enteredAccount);
                        flag = true;
                    }
                }
            } catch (Exception err) {
                System.out.println("error " + err);
            }
        } else if (toDo == 0) {
            System.out.print("Enter your name: ");
            String name = scan.next();
            System.out.print("Enter your surname: ");
            String surname = scan.next();

            Account newAcc = new Account(name, surname);
            try {
                accounts = new HashMap<>();
                if (!isTextFileEmpty("Data.txt")) {
                    ObjectInputStream read = new ObjectInputStream(new FileInputStream("Data.txt"));
                    accounts = (HashMap<String, Account>) read.readObject();
                    read.close();
                }
                accounts.put(newAcc.getAccountNumber(), newAcc);
                account = newAcc;
                ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream("Data.txt"));
                write.writeObject(accounts);
                write.flush();
                write.close();
                flag = true;
                System.out.println("Your account is successfully created");
            } catch (Exception err) {
                System.out.println("dfsdfsdfsdf");
                System.out.println("error " + err);
            }
        }

        if (flag) {
            System.out.println("What would you like to do next?" + "\n" +
                    "See account full name: press 2" + "\n" +
                    "Check balance: press 3" + "\n" +
                    "Add money to balance: press 4" + "\n" +
                    "Withdraw money from balance: press 5" + "\n" +
                    "To transfer funds between accounts: press 6" + "\n" +
                    "To remove the current account: press 7" + "\n" +
                    "To complete a transaction and save changes: press 8" + "\n");

            while (scan.hasNext()) {
                toDo = scan.nextInt();
                if (toDo == 2) {
                    System.out.println("Account full name: " + account.getFullName());
                } else if (toDo == 3) {
                    System.out.println("Current balance: $" + account.checkBalance());
                } else if (toDo == 4) {
                    System.out.println("Desired amount to add: $");
                    account.addAmount(scan.nextDouble());
                    System.out.println("Money successfully added to balance.");
                } else if (toDo == 5) {
                    System.out.println("Desired amount to withdraw: $");
                    account.withdraw(scan.nextDouble());
                    System.out.println("Money successfully withdrawn from balance.");
                } else if (toDo == 6) {
                    System.out.println("Enter the account number of bank account to transfer");
                    String acc = scan.next();
                    System.out.println("Enter the amount to transfer");
                    double amount = scan.nextDouble();
                    try {
                        ObjectInputStream read = new ObjectInputStream(new FileInputStream("Data.txt"));
                        accounts = (HashMap<String, Account>) read.readObject();
                        if (!accounts.containsKey(acc)) {
                            System.out.println("Such account does not exist :( ");
                        }
                        account.transfer(amount, acc, accounts);
                        System.out.println("Money successfully transferred");
                    } catch (Exception err) {
                        System.out.println("error " + err);
                    }
                } else if (toDo == 7) {
                    ObjectInputStream read = new ObjectInputStream(new FileInputStream("Data.txt"));
                    accounts = (HashMap<String, Account>) read.readObject();
                    accounts.remove(account.getAccountNumber());
                    updateDB(accounts);
                    System.out.println("Account successfully deleted");
                    System.exit(0);
                } else if (toDo == 8) {
                    accounts.put(account.getAccountNumber(), account);
                    updateDB(accounts);
                    System.out.println("Transaction is completed");
                    System.exit(0);
                }
                else {
                    System.out.println("Input the number listed above");
                }
            }
        }


    }


    public static boolean isTextFileEmpty(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            boolean res = (bufferedReader.readLine() == null);
            bufferedReader.close();
            return res;
        } catch (Exception err) {
            System.out.println("error " + err);
            return false;
        }

    }

    public static boolean updateDB(HashMap<String, Account> updatedAccounts) {
        try {
            ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream("Data.txt"));
            write.writeObject(updatedAccounts);
            write.flush();
            write.close();
            return true;
        } catch (Exception err) {
            System.out.println("error " + err);
            return false;
        }
    }
}