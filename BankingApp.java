import java.util.Scanner;

class BankUser {
    private int userId;
    private String name;
    private int pin;
    private double balance;

    // Constructor to initialize user data
    public BankUser(int userId, String name, int pin, double balance) {
        this.userId = userId;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    // Getters and setters for user information
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean validatePin(int inputPin) {
        return this.pin == inputPin;
    }
}

public class BankingApp {

    // Array to store users (can be expanded to a database in a real-world app)
    private static BankUser[] users = {
        new BankUser(412435, "Chris Sandoval", 1234, 32000),
        new BankUser(264863, "Marc Yim", 5678, 1000)
    };

    private static BankUser loggedInUser = null;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login phase
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Welcome to the Banking App!");
            System.out.print("Enter User ID: ");
            int userId = scanner.nextInt();

            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();

            // Validate login credentials
            for (BankUser user : users) {
                if (user.getUserId() == userId && user.validatePin(pin)) {
                    loggedInUser = user;
                    loggedIn = true;
                    System.out.println("Login successful! Welcome, " + user.getName());
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }

        // Main banking menu after login
        boolean appRunning = true;
        while (appRunning) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Cash In");
            System.out.println("3. Money Transfer");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    cashIn(scanner);
                    break;
                case 3:
                    moneyTransfer(scanner);
                    break;
                case 4:
                    appRunning = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    // Method to check balance
    private static void checkBalance() {
        System.out.println("Your current balance is: $" + loggedInUser.getBalance());
    }

    // Method to cash in (add money to account)
    private static void cashIn(Scanner scanner) {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            double newBalance = loggedInUser.getBalance() + amount;
            loggedInUser.setBalance(newBalance);
            System.out.println("Deposit successful! Your new balance is: $" + newBalance);
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    // Method to transfer money
    private static void moneyTransfer(Scanner scanner) {
        System.out.print("Enter recipient's User ID: ");
        int recipientId = scanner.nextInt();

        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }

        // Find recipient user
        BankUser recipient = null;
        for (BankUser user : users) {
            if (user.getUserId() == recipientId) {
                recipient = user;
                break;
            }
        }

        if (recipient != null && loggedInUser.getBalance() >= amount) {
            // Transfer money
            loggedInUser.setBalance(loggedInUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            System.out.println("Transfer successful! You transferred $" + amount + " to " + recipient.getName());
        } else if (recipient == null) {
            System.out.println("Recipient with the given User ID not found.");
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }
}
