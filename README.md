# Library Management System (Java Console Application)

## 📌 Project Description
This **Library Management System** is a **Java-based console application** designed to simplify book and user management operations in a library environment. It supports both **Admin** and **User** functionalities, ensuring a clear separation of responsibilities.

This project showcases:
- Input validation using Regex
- Menu-driven navigation
- Data storage using arrays
- Basic object and data handling logic

---

## ✅ Features

### 👤 User Features
| Feature | Description |
|--------|-------------|
| **Create Account** | Register using unique ID format: `USR0001` |
| **Secure Password** | Password must meet security rules (uppercase, lowercase, number, special char) |
| **Balance Management** | Deposit funds and view balance |
| **Borrow Books** | Borrow available books (tracked to specific user) |
| **Return Books** | Users can return previously borrowed books |

---

### 🛠 Admin Features
| Feature | Description |
|--------|-------------|
| **Login as Admin** | Default credentials: **Username:** `admin` | **Password:** `Admin@123` |
| **Add Books** | Add new books using ISBN format: `B0001` |
| **Modify Books** | Update book title or author |
| **Remove Books** | Remove books if not currently borrowed |
| **View Users** | Display all registered users and balances |
| **View Books** | List all books with availability status |

---

## 🔄 Workflow

### User Flow
1. Register a new user account  
2. Login using user ID  
3. View balance, deposit funds, borrow or return books  

### Admin Flow
1. Login using admin credentials  
2. Add, edit, or remove books  
3. View all users and book list  

---

## 🔒 Validation Rules

| Data | Rule |
|------|------|
| **User ID** | Must follow `USRxxxx` format (e.g., `USR0001`) |
| **Password** | Min 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special character |
| **ISBN** | Must follow `Bxxxx` format (e.g., `B0001`) |

---

## 🖥 Sample Menus

### Main Menu
  1.Create Account
  2.Login as User
  3.Login as Admin
  4.Exit


### Admin Menu
  1.Add Book
  2.Remove Book
  3.Modify Book
  4.List Books
  5.View Users
  6.Logout


### User Menu
  1.View Balance
  2.Deposit Funds
  3.Borrow Book
  4.Return Book
  5.List Books
  6.Logout


---

## 🔧 System Requirements
| Requirement | Details |
|------------|---------|
| Language | Java (JDK 8+) |
| Console | Command Prompt / Terminal |
| IDE (optional) | IntelliJ, Eclipse, NetBeans |

---

## ▶ How to Compile & Run

### Using Terminal / CMD
```sh
javac LibraryApp.java
java LibraryApp

Using an IDE

Create a new Java project

Add the LibraryApp.java file

Run the program
