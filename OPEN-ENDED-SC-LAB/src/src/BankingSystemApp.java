import java.util.Scanner;

public class BankingSystemApp {
    public static void main(String[] args) {
        // Initialize the bank
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("             BLDM Banking System");
        System.out.println("========================================\n");

        while (true) {
            System.out.println("\n====== MAIN PORTAL ======");
            System.out.println("1. ATM Service");
            System.out.println("2. Administrator Portal");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    //  ATM interface
                    ATM atm = new ATM(bank);
                    atm.start();
                    break;
                case "2":
                    // Administrator interface
                    BankAdministrator admin = new BankAdministrator(bank);
                    admin.start();
                    break;
                case "3":
                    System.out.println("\nThank you for using BLDM Banking System!");
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}