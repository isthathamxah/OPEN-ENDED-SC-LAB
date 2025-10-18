import java.util.ArrayList;

/**
 * Customer class representing a bank customer
 */
public class Customer {
    private String customerId;
    private String name;
    private String pin;
    private ArrayList<BankAccount> accounts;
    private int failedLoginAttempts;
    private boolean isBlocked;

    public Customer(String customerId, String name, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.pin = pin;
        this.accounts = new ArrayList<>();
        this.failedLoginAttempts = 0;
        this.isBlocked = false;
    }

    /**
     * Add an account to this customer
     */
    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    /**
     * Verify PIN for login
     */
    public boolean verifyPin(String enteredPin) {
        if (isBlocked) {
            return false;
        }

        if (pin.equals(enteredPin)) {
            failedLoginAttempts = 0;
            return true;
        } else {
            failedLoginAttempts++;
            if (failedLoginAttempts >= 3) {
                isBlocked = true;
            }
            return false;
        }
    }

    // Getters and setters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void unblock() {
        isBlocked = false;
        failedLoginAttempts = 0;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }
}