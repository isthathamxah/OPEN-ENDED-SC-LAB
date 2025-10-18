/**
 * Savings Account with minimum balance requirement
 */
public class SavingsAccount extends BankAccount {
    private static final double MINIMUM_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, String customerId, double initialBalance) {
        super(accountNumber, customerId, initialBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }

        if (balance - amount < MINIMUM_BALANCE) {
            addTransaction(TransactionType.WITHDRAWAL, amount, TransactionStatus.FAILED_INSUFFICIENT_FUNDS);
            throw new InsufficientFundsException("Withdrawal would violate minimum balance of " + MINIMUM_BALANCE);
        }

        balance -= amount;
        addTransaction(TransactionType.WITHDRAWAL, amount, TransactionStatus.SUCCESS);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}