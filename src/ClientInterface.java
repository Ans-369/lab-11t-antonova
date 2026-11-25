

import model.BankAccount;
import exceptions.AccountException;
import model.CheckingAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() throws IOException {

        String option;
        while (true) {
            printMainMenu();
            option = scanner.next();

            switch (Integer.parseInt(option)) {
                case 1:
                    try {
                        addNewAccount();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                        break;
                    }
                    break;
                case 2:
                    try {
                        deleteAccount();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 3:
                    try {
                        deposit();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 4:
                    try {
                        withdraw();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 5:
                    try {
                         printInfo();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 6:
                    return;
                default:
                    // Please enter a valid choice (1 thru 6)
            }
        }

    }

    private void printMainMenu() throws IOException {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }
    private BankAccount findAndAuthenticate(String userId, String password) {
        for (BankAccount account : accounts) {
            if (account.getUserId().equals(userId)) {
                if (!account.checkPassword(password)) {
                    throw new AccountException("Invalid password");
                }
                return account;
            }
        }
        throw new AccountException("Account with user ID '" + userId + "' not found");
    }

    private void addNewAccount() {

        /*
         * В даному методі ви повинні передбачити перевірки для
         * коректності полів нового акаунту та створити новий акаунт, якщо всі дані
         * є коректними
         */

        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        var name = scanner.next();
        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        String password = scanner.next();
        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.next();

        boolean exists = accounts.stream().anyMatch(a -> a.getUserId().equals(userId));
        if (exists) {
            throw new AccountException("Account with user ID '" + userId + "' already exists");
        }
        if (!password.equals(confirmPassword)) {
            throw new AccountException("Passwords do not match");
        }

        CheckingAccount account = new CheckingAccount(userId, name, password, 0.0);
        accounts.add(account);
        System.out.println("Account created successfully.\n");
    }

    private void deleteAccount() {

        /*
         * В даному методі ви повинні передбачити перевірки для
         * коректності полів для видалення акаунту та видалити акаунт
         */

        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = findAndAuthenticate(userId, password);
        if (account.getBalance() != 0.0) {
            throw new AccountException("Cannot delete account with non-zero balance");
        }

        accounts.remove(account);
        System.out.println("Account deleted successfully.\n");
    }

    private void deposit() {

        /*
         * В даному методі ви повинні передбачити перевірки для
         * коректності полів та зробити внесок
         */

        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amountInput = scanner.next();

        double depositAmount= 0.0;
        try {
            depositAmount = Double.parseDouble(amountInput);
        } catch (NumberFormatException e) {
            throw new AccountException("Invalid amount format");
        }

        BankAccount account = findAndAuthenticate(userId, password);
        account.deposit(depositAmount);
        System.out.println("Deposit completed successfully.\n");
    }

    private void withdraw() {

        /*
         * В даному методі ви повинні передбачити перевірки для
         * коректності полів та зробити зняття коштів
         */

        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amountInput = scanner.next();

        double withdrawAmount= 0.0;
        try {
            withdrawAmount = Double.parseDouble(amountInput);
        } catch (NumberFormatException e) {
            throw new AccountException("Invalid amount format");
        }

        BankAccount account = findAndAuthenticate(userId, password);
        account.withdraw(withdrawAmount);
        System.out.println("Withdrawal completed successfully.\n");
    }

    private void printInfo() {

        /*
         * Даний метод виводить інформацію щодо рахунку. Ви
         * повинні перевірити наявність рахунку та вивести інформацію
         * стосовно нього
         */

        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = findAndAuthenticate(userId, password);
        double limit = (account instanceof CheckingAccount checking)
                ? checking.getDailyWithdrawalLimit()
                : 0.0;

        System.out.println("\nAccount info: ");

        System.out.println(" - customer name: " + account.getCustomerName());
        System.out.println(" - account balance: " + account.getBalance());
        System.out.println(" - account type: Checking");
        System.out.println(" - account daily withdrawal limit: " + limit);

        System.out.print("\nPress Enter to return to main menu...");

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}