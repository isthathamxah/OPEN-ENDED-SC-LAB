import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
    private static int transactionCounter = 1000;
    private String transactionId;
    private TransactionType type;
    private double amount;
    private LocalDateTime dateTime;
    private String accountNumber;
    private TransactionStatus status;

    public Transaction(TransactionType type, double amount, String accountNumber, TransactionStatus status) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.status = status;
        this.dateTime = LocalDateTime.now();
    }


    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("ID: %s | Type: %s | Amount: %.2f | Date: %s | Status: %s",
                transactionId, type, amount, dateTime.format(formatter), status);
    }
}