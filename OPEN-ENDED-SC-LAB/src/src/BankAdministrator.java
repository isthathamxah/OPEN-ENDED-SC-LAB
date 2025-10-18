import java.util.Scanner;

/**
 * Bank Administrator interface for management operations
 */
public class BankAdministrator {
    private Bank bank;
    private Scanner scanner;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    public BankAdministrator(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Start admin interface
     */
    public void start() {
        System.out.println("====================================");
        System.out.println("   Bank Administrator Login");
        System.out.println("====================================\n");

        if (login()) {
            showAdminMenu();
        }
    }

    /**
     * Admin login
     */
    private boolean login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("\nAdmin login successful!");
            return true;
        } else {
            System.out.println("Invalid credentials!");
            return false;
        }
    }

    /**
     * Show admin menu
     */
    private void showAdminMenu() {
        while (true) {
            System.out.println("\n====== ADMIN MENU ======");
            System.out.println("1. Register New Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. View All Accounts");
            System.out.println("4. Create New Account");
            System.out.println("5. Unblock Customer");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerNewCustomer();
                    break;
                case "2":
                    viewAllCustomers();
                    break;
                case "3":
                    viewAllAccounts();
                    break;
                case "4":
                    createNewAccount();
                    break;
                case "5":
                    unblockCustomer();
                    break;
                case "6":
                    System.out.println("Admin logout successful!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Register a new customer
     */
    private void registerNewCustomer() {
        System.out.println("\n====== REGISTER NEW CUSTOMER ======");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        System.out.print("Enter PIN (4 digits recommended): ");
        String pin = scanner.nextLine().trim();

        if (pin.isEmpty()) {
            System.out.println("PIN cannot be empty!");
            return;
        }

        String customerId = bank.registerCustomer(name, pin);
        System.out.println("\n✓ Customer registered successfully!");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("\nNote: Customer needs at least one account to use ATM.");
        System.out.println("Use 'Create New Account' option to add accounts.");
    }

    /**
     * View all customers
     */
    private void viewAllCustomers() {
        System.out.println("\n====== ALL CUSTOMERS ======");
        if (bank.getAllCustomers().isEmpty()) {
            System.out.println("No customers registered yet.");
            System.out.println("Use 'Register New Customer' option to add customers.");
            return;
        }

        for (Customer customer : bank.getAllCustomers().values()) {
            System.out.println("\nCustomer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Status: " + (customer.isBlocked() ? "BLOCKED" : "ACTIVE"));
            System.out.println("Accounts:");
            if (customer.getAccounts().isEmpty()) {
                System.out.println("  - No accounts yet");
            } else {
                for (BankAccount acc : customer.getAccounts()) {
                    System.out.println("  - " + acc.getAccountNumber() + " (" + acc.getAccountType() + ")");
                }
            }
        }
    }

    /**
     * View all accounts
     */
    private void viewAllAccounts() {
        System.out.println("\n====== ALL ACCOUNTS ======");
        if (bank.getAllAccounts().isEmpty()) {
            System.out.println("No accounts created yet.");
            System.out.println("Use 'Create New Account' option to add accounts.");
            return;
        }

        System.out.printf("%-12s %-20s %-15s %-12s\n", "Account No", "Type", "Customer ID", "Balance");
        System.out.println("---------------------------------------------------------------");

        for (BankAccount account : bank.getAllAccounts().values()) {
            System.out.printf("%-12s %-20s %-15s $%-11.2f\n",
                    account.getAccountNumber(),
                    account.getAccountType(),
                    account.getCustomerId(),
                    account.getBalance());
        }
    }

    /**
     * Create new account for existing customer
     */
    private void createNewAccount() {
        System.out.println("\n====== CREATE NEW ACCOUNT ======");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        Customer customer = bank.getCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");
        System.out.print("Choose type: ");
        String typeChoice = scanner.nextLine().trim();

        String accountType;
        if (typeChoice.equals("1")) {
            accountType = "savings";
        } else if (typeChoice.equals("2")) {
            accountType = "checking";
        } else {
            System.out.println("Invalid account type!");
            return;
        }

        System.out.print("Enter initial balance: ");
        try {
            double initialBalance = Double.parseDouble(scanner.nextLine().trim());

            if (initialBalance < 0) {
                System.out.println("Initial balance cannot be negative!");
                return;
            }

            String accountNumber = bank.createAccount(customerId, accountType, initialBalance);
            System.out.println("Account created successfully!");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Customer: " + customer.getName());
            System.out.printf("Initial Balance: %.2f\n", initialBalance);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
        } catch (InvalidAccountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Unblock a customer account
     */
    private void unblockCustomer() {
        System.out.println("\n====== UNBLOCK CUSTOMER ======");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        Customer customer = bank.getCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        if (!customer.isBlocked()) {
            System.out.println("Customer is not blocked.");
            return;
        }

        customer.unblock();
        System.out.println("Customer " + customer.getName() + " has been unblocked successfully!");
    }
}