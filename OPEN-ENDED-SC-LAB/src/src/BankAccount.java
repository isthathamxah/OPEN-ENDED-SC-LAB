import java.util.ArrayList;

public abstract class BankAccount
{
    protected String accountNumber;
    protected double balance;
    protected String customerId;
    protected ArrayList<Transaction> transactionHistory;
    protected AccountStatus status;

    public BankAccount(String accountNumber, String customerId, double initialBalance)
    {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        this.status = AccountStatus.ACTIVE;
    }


    public void deposit(double amount) throws InvalidAmountException
    {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        balance += amount;
        addTransaction(TransactionType.DEPOSIT, amount, TransactionStatus.SUCCESS);
    }


    public abstract void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException;


    public double getBalance() {
        return balance;
    }


    protected void addTransaction(TransactionType type, double amount, TransactionStatus status) {
        Transaction t = new Transaction(type, amount, accountNumber, status);
        transactionHistory.add(t);
    }


    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public abstract String getAccountType();
}