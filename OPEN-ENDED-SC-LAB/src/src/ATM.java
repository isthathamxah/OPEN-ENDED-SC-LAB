import java.util.Scanner;


public class ATM {
    private Bank bank;
    private Scanner scanner;
    private Customer currentCustomer;
    private BankAccount currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }


    public void start() {
        System.out.println("====================================");
        System.out.println("   Welcome to BLDM Banking ATM");
        System.out.println("====================================\n");

        if (login()) {
            showMainMenu();
        }
    }


    private boolean login() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        Customer customer = bank.getCustomer(customerId);
        if (customer == null) {
            System.out.println("Invalid Customer ID!");
            return false;
        }

        if (customer.isBlocked()) {
            System.out.println("Your account is blocked. Please contact the bank.");
            return false;
        }

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine().trim();

        if (customer.verifyPin(pin)) {
            currentCustomer = customer;
            System.out.println("\nLogin successful! Welcome, " + customer.getName());
            return true;
        } else {
            if (customer.isBlocked()) {
                System.out.println("Too many failed attempts. Your account has been blocked.");
            } else {
                System.out.println("Incorrect PIN! Attempts remaining: " + (3 - customer.getFailedLoginAttempts()));
            }
            return false;
        }
    }


    private void showMainMenu() {
        while (true) {
            System.out.println("\n====== MAIN MENU ======");
            System.out.println("1. Select Account");
            System.out.println("2. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                if (currentCustomer.getAccounts().isEmpty()) {
                    System.out.println("\nYou don't have any accounts yet.");
                    System.out.println("Please visit the bank or contact administrator to create an account.");
                } else {
                    selectAccount();
                }
            } else if (choice.equals("2")) {
                System.out.println("Thank you for using ABC Bank ATM!");
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }


    private void selectAccount() {
        if (currentCustomer.getAccounts().isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        System.out.println("\n====== YOUR ACCOUNTS ======");
        for (int i = 0; i < currentCustomer.getAccounts().size(); i++) {
            BankAccount acc = currentCustomer.getAccounts().get(i);
            System.out.printf("%d. %s - %s (Balance: %.2f)\n",
                    i + 1, acc.getAccountNumber(), acc.getAccountType(), acc.getBalance());
        }

        System.out.print("Select account (enter number): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= currentCustomer.getAccounts().size()) {
                currentAccount = currentCustomer.getAccounts().get(choice - 1);
                showAccountMenu();
            } else {
                System.out.println("Invalid selection!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }


    private void showAccountMenu() {
        while (true) {
            System.out.println("\n====== ACCOUNT OPERATIONS ======");
            System.out.println("Account: " + currentAccount.getAccountNumber() + " (" + currentAccount.getAccountType() + ")");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Transfer Funds");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    deposit();
                    break;
                case "3":
                    withdraw();
                    break;
                case "4":
                    viewTransactionHistory();
                    break;
                case "5":
                    transferFunds();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }


    private void checkBalance() {
        System.out.printf("\nCurrent Balance: %.2f\n", currentAccount.getBalance());
    }


    private void deposit() {
        System.out.print("Enter deposit amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            currentAccount.deposit(amount);
            System.out.printf("Successfully deposited %.2f\n", amount);
            printReceipt("DEPOSIT", amount, currentAccount.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            currentAccount.withdraw(amount);
            System.out.printf("Successfully withdrew %.2f\n", amount);
            printReceipt("WITHDRAWAL", amount, currentAccount.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void viewTransactionHistory() {
        System.out.println("\n====== TRANSACTION HISTORY ======");
        if (currentAccount.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : currentAccount.getTransactionHistory()) {
                System.out.println(t);
            }
        }
    }


    private void transferFunds() {
        System.out.println("\n====== FUND TRANSFER ======");
        System.out.println("1. Transfer to my other account");
        System.out.println("2. Transfer to another customer");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            transferToOwnAccount();
        } else if (choice.equals("2")) {
            transferToOtherCustomer();
        } else {
            System.out.println("Invalid option!");
        }
    }


    private void transferToOwnAccount() {
        if (currentCustomer.getAccounts().size() < 2) {
            System.out.println("You need at least 2 accounts for this operation.");
            return;
        }

        System.out.println("\nYour other accounts:");
        for (int i = 0; i < currentCustomer.getAccounts().size(); i++) {
            BankAccount acc = currentCustomer.getAccounts().get(i);
            if (!acc.getAccountNumber().equals(currentAccount.getAccountNumber())) {
                System.out.printf("%d. %s - %s (Balance: %.2f)\n",
                        i + 1, acc.getAccountNumber(), acc.getAccountType(), acc.getBalance());
            }
        }

        System.out.print("Select destination account (enter number): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= currentCustomer.getAccounts().size()) {
                BankAccount destAccount = currentCustomer.getAccounts().get(choice - 1);
                if (destAccount.getAccountNumber().equals(currentAccount.getAccountNumber())) {
                    System.out.println("Cannot transfer to the same account!");
                    return;
                }

                System.out.print("Enter amount to transfer: ");
                double amount = Double.parseDouble(scanner.nextLine().trim());

                bank.transfer(currentAccount.getAccountNumber(), destAccount.getAccountNumber(), amount);
                System.out.printf("Successfully transferred %.2f to %s\n", amount, destAccount.getAccountNumber());
                printReceipt("TRANSFER", amount, currentAccount.getBalance());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        } catch (InsufficientFundsException | InvalidAccountException | InvalidAmountException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }


    private void transferToOtherCustomer() {
        System.out.print("Enter destination account number: ");
        String destAccountNum = scanner.nextLine().trim();

        BankAccount destAccount = bank.getAccount(destAccountNum);
        if (destAccount == null) {
            System.out.println("Destination account not found!");
            return;
        }

        if (destAccount.getCustomerId().equals(currentCustomer.getCustomerId())) {
            System.out.println("Use 'Transfer to my other account' for internal transfers!");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            bank.transfer(currentAccount.getAccountNumber(), destAccountNum, amount);
            System.out.printf("Successfully transferred %.2f to account %s\n", amount, destAccountNum);
            printReceipt("TRANSFER", amount, currentAccount.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
        } catch (InsufficientFundsException | InvalidAccountException | InvalidAmountException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }


    private void printReceipt(String type, double amount, double newBalance) {
        System.out.println("\n========== RECEIPT ==========");
        System.out.println("Transaction Type: " + type);
        System.out.printf("Amount: %.2f\n", amount);
        System.out.println("Account: " + currentAccount.getAccountNumber());
        System.out.printf("New Balance: %.2f\n", newBalance);
        System.out.println("=============================\n");
    }
}