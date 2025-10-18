import java.util.HashMap;


public class Bank {
    private HashMap<String, Customer> customers; // Key: customerId
    private HashMap<String, BankAccount> accounts; // Key: accountNumber
    private int accountCounter = 1000;

    private int customerCounter = 1000;

    public Bank() {
        customers = new HashMap<>();
        accounts = new HashMap<>();
    }


    public String registerCustomer(String name, String pin) {
        String customerId = "C" + (++customerCounter);
        Customer newCustomer = new Customer(customerId, name, pin);
        customers.put(customerId, newCustomer);
        return customerId;
    }


    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }


    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }


    public String createAccount(String customerId, String accountType, double initialBalance)
            throws InvalidAccountException {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new InvalidAccountException("Customer not found");
        }

        String accountNumber = "ACC" + (++accountCounter);
        BankAccount newAccount;

        if (accountType.equalsIgnoreCase("savings")) {
            newAccount = new SavingsAccount(accountNumber, customerId, initialBalance);
        } else if (accountType.equalsIgnoreCase("checking")) {
            newAccount = new CheckingAccount(accountNumber, customerId, initialBalance);
        } else {
            throw new InvalidAccountException("Invalid account type");
        }

        customer.addAccount(newAccount);
        accounts.put(accountNumber, newAccount);
        return accountNumber;
    }


    public void transfer(String fromAccountNum, String toAccountNum, double amount)
            throws InsufficientFundsException, InvalidAccountException, InvalidAmountException {

        BankAccount fromAccount = accounts.get(fromAccountNum);
        BankAccount toAccount = accounts.get(toAccountNum);

        if (fromAccount == null || toAccount == null) {
            throw new InvalidAccountException("Invalid account number");
        }

        if (toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Destination account is not active");
        }

        // Withdraw from source
        fromAccount.withdraw(amount);

        // Deposit to destination
        toAccount.deposit(amount);

        // Add transfer transaction records
        fromAccount.addTransaction(TransactionType.TRANSFER, -amount, TransactionStatus.SUCCESS);
        toAccount.addTransaction(TransactionType.TRANSFER, amount, TransactionStatus.SUCCESS);
    }


    public HashMap<String, Customer> getAllCustomers() {
        return customers;
    }


    public HashMap<String, BankAccount> getAllAccounts() {
        return accounts;
    }
}