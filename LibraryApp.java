import java.util.*;
import java.util.regex.Pattern;

public class LibraryApp {

    private static final int MAX_USERS = 20;
    private static final int MAX_BOOKS = 15;
    private static final int MAX_STRING_LENGTH = 50;

    private static int userCount = 0;
    private static int bookCount = 0;

    private static double[] accountBalances = new double[MAX_USERS];
    private static String[] userIds = new String[MAX_USERS];
    private static String[] userNames = new String[MAX_USERS];

    private static String[] bookTitles = new String[MAX_BOOKS];
    private static String[] bookAuthors = new String[MAX_BOOKS];
    private static String[] bookIsbns = new String[MAX_BOOKS];
    private static boolean[] bookAvailable = new boolean[MAX_BOOKS];
    private static int[] bookBorrowedBy = new int[MAX_BOOKS];

    private static final Scanner sc = new Scanner(System.in);

    private static final Pattern USER_ID_PATTERN = Pattern.compile("USR\\d{4}");
    private static final Pattern BOOK_ISBN_PATTERN = Pattern.compile("B\\d{4}");

    public static void main(String[] args) {
        for (int i = 0; i < MAX_BOOKS; i++) {
            bookTitles[i] = "Default Title";
            bookAuthors[i] = "Default Author";
            bookIsbns[i] = String.format("B%04d", i + 1);
            bookAvailable[i] = true;
            bookBorrowedBy[i] = -1;
        }
        bookCount = MAX_BOOKS;

        while (true) {
            System.out.println("\n1. Create Account\n2. Login as User\n3. Login as Admin\n0. Exit");
            int choice = readInt("Enter choice: ");

            if (choice == 0) break;

            switch (choice) {
                case 1 -> createUserAccount();
                case 2 -> loginUser();
                case 3 -> adminLogin();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void createUserAccount() {
        if (userCount >= MAX_USERS) return;

        String userId = readString("Enter User ID (USRXXXX): ");
        if (!USER_ID_PATTERN.matcher(userId).matches()) return;
        if (findUserIndex(userId) != -1) return;

        String name = readLine("Enter Name: ");
        String password = readString("Enter Password: ");
        if (!validatePassword(password)) return;

        double initialDeposit = readDouble("Enter Initial Deposit (>=50): ");
        if (initialDeposit < 50) return;

        userIds[userCount] = userId;
        userNames[userCount] = name;
        accountBalances[userCount] = initialDeposit - 50;
        userCount++;
    }

    private static void loginUser() {
        String userId = readString("Enter User ID: ");
        int idx = findUserIndex(userId);
        if (idx == -1) return;

        while (true) {
            System.out.println("\n1. View Balance\n2. Deposit Funds\n3. Borrow Book\n4. Return Book\n5. List Books\n0. Logout");
            int choice = readInt("Enter choice: ");

            if (choice == 0) break;

            switch (choice) {
                case 1 -> viewUserDetails(userId);
                case 2 -> {
                    double amount = readDouble("Deposit Amount: ");
                    accountBalances[idx] += amount;
                }
                case 3 -> borrowBook(idx);
                case 4 -> returnBook(idx);
                case 5 -> listAllBooks();
            }
        }
    }

    private static void adminLogin() {
        String username = readString("Admin Username: ");
        String password = readString("Admin Password: ");

        if (!(username.equals("admin") && password.equals("Admin@123"))) return;

        while (true) {
            System.out.println("\n1. Add Book\n2. Remove Book\n3. Modify Book\n4. List Books\n5. View Users\n0. Logout");
            int choice = readInt("Enter choice: ");

            if (choice == 0) break;

            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> modifyBookDetails();
                case 4 -> listAllBooks();
                case 5 -> viewAllUsers();
            }
        }
    }

    private static void borrowBook(int userIdx) {
        String isbn = readString("Enter ISBN: ");
        int bIdx = findBookIndex(isbn);
        if (bIdx == -1 || !bookAvailable[bIdx]) return;

        bookAvailable[bIdx] = false;
        bookBorrowedBy[bIdx] = userIdx;
    }

    private static void returnBook(int userIdx) {
        String isbn = readString("Enter ISBN to Return: ");
        int bIdx = findBookIndex(isbn);
        if (bIdx == -1 || bookBorrowedBy[bIdx] != userIdx) return;

        bookAvailable[bIdx] = true;
        bookBorrowedBy[bIdx] = -1;
    }

    private static void addBook() {
        if (bookCount >= MAX_BOOKS) return;

        String title = readLine("Title: ");
        String author = readLine("Author: ");
        String isbn = readString("ISBN (BXXXX): ");
        if (!BOOK_ISBN_PATTERN.matcher(isbn).matches()) return;

        bookTitles[bookCount] = title;
        bookAuthors[bookCount] = author;
        bookIsbns[bookCount] = isbn;
        bookAvailable[bookCount] = true;
        bookBorrowedBy[bookCount] = -1;
        bookCount++;
    }

    private static void removeBook() {
        String isbn = readString("Enter ISBN: ");
        int idx = findBookIndex(isbn);
        if (idx == -1 || !bookAvailable[idx]) return;

        for (int i = idx; i < bookCount - 1; i++) {
            bookTitles[i] = bookTitles[i + 1];
            bookAuthors[i] = bookAuthors[i + 1];
            bookIsbns[i] = bookIsbns[i + 1];
            bookAvailable[i] = bookAvailable[i + 1];
            bookBorrowedBy[i] = bookBorrowedBy[i + 1];
        }
        bookCount--;
    }

    private static void modifyBookDetails() {
        String isbn = readString("Enter ISBN: ");
        int idx = findBookIndex(isbn);
        if (idx == -1) return;

        String newTitle = readLine("New Title: ");
        String newAuthor = readLine("New Author: ");
        if (!newTitle.isBlank()) bookTitles[idx] = newTitle;
        if (!newAuthor.isBlank()) bookAuthors[idx] = newAuthor;
    }

    private static void listAllBooks() {
        for (int i = 0; i < bookCount; i++) {
            System.out.println(bookIsbns[i] + " | " + bookTitles[i] + " | " + bookAuthors[i] + " | " + (bookAvailable[i] ? "Available" : "Borrowed"));
        }
    }

    private static void viewAllUsers() {
        for (int i = 0; i < userCount; i++) {
            System.out.printf("User ID: %s | Name: %s | Balance: %.2f%n", userIds[i], userNames[i], accountBalances[i]);
        }
    }

    private static void viewUserDetails(String userId) {
        int idx = findUserIndex(userId);
        if (idx == -1) return;
        System.out.printf("Balance: %.2f%n", accountBalances[idx]);
    }

    private static int findUserIndex(String userId) {
        for (int i = 0; i < userCount; i++) if (userIds[i].equals(userId)) return i;
        return -1;
    }

    private static int findBookIndex(String isbn) {
        for (int i = 0; i < bookCount; i++) if (bookIsbns[i].equals(isbn)) return i;
        return -1;
    }

    private static boolean validatePassword(String pwd) {
        boolean u = false, l = false, d = false, s = false;
        for (char c : pwd.toCharArray()) {
            if (Character.isUpperCase(c)) u = true;
            else if (Character.isLowerCase(c)) l = true;
            else if (Character.isDigit(c)) d = true;
            else s = true;
        }
        return pwd.length() >= 8 && u && l && d && s;
    }

    private static int readInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(sc.nextLine().trim());
    }

    private static double readDouble(String msg) {
        System.out.print(msg);
        return Double.parseDouble(sc.nextLine().trim());
    }

    private static String readString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static String readLine(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}
