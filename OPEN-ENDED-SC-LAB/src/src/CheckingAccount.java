/**
 * Checking Account with overdraft limit
 */
public class CheckingAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = 1000.0;

    public CheckingAccount(String accountNumber, String customerId, double initialBalance) {
        super(accountNumber, customerId, initialBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }

        if (balance - amount < -OVERDRAFT_LIMIT) {
            addTransaction(TransactionType.WITHDRAWAL, amount, TransactionStatus.FAILED_INSUFFICIENT_FUNDS);
            throw new InsufficientFundsException("Withdrawal exceeds overdraft limit of " + OVERDRAFT_LIMIT);
        }

        balance -= amount;
        addTransaction(TransactionType.WITHDRAWAL, amount, TransactionStatus.SUCCESS);
    }

    @Override
    public String getAccountType() {
        return "Checking Account";
    }

    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}