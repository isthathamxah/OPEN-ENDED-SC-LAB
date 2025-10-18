## Banking System

## System Overview
This banking system implements a complete ATM and bank management solution using Object-Oriented Programming principles in Java. The system supports multiple customers, diverse account types, transaction logging, and administrative functions.                

## Files Included: Go to src/
BankAccount.java - Abstract base class for accounts

SavingsAccount.java - Savings account implementation

CheckingAccount.java - Checking account implementation

Customer.java - Customer entity

Transaction.java - Transaction record

Bank.java - Central bank repository

ATM.java - Customer interface

BankAdministrator.java - Admin interface

BankingSystemApp.java - Main application

## Enums:
AccountStatus.java

TransactionType.java

TransactionStatus.java


## Exceptions:
AccountBlockedException.java

InsufficentFundsException.java

InvalidAccountException.java

InvalidAmountException.java

## Features 
Login as Admin (username: admin, password: password)

Register New Customer: Create a customer with name and PIN

Create Account: Add a Savings or Checking account for the customer

Logout from admin

Login to ATM using the Customer ID and PIN you just created

## ATM Features

Login: Try correct and incorrect PINs (3 failed attempts = blocked)

Check Balance: View current balance

Deposit: Add money to account

Withdraw:

Savings: Cannot go below $500 minimum balance

Checking: Can overdraft up to $1000 (balance can be -$1000)

Transfer:

Own Accounts: Transfer between your savings and checking

Transfer Other Customer: Transfer to another customer's account


Transaction History: View all past transactions

Receipt: Printed after each successful transaction


## Administrator Features

Register New Customer: Create new customers at runtime

View All Customers: See all registered customers

View All Accounts: See all accounts with balances

Create Account: Add new savings/checking account for existing customer

Unblock Customer: Unblock customers blocked due to failed login attempts


## Testing Features


## Test 1: Failed Login & Blocking

Login to ATM with your first customer ID

Enter wrong PIN 3 times

Account should be blocked

Login as admin and unblock the customer


## Test 2: Minimum Balance (Savings)

Create a customer with a savings account having $8000

Login to ATM

Try to withdraw $7600 (would leave $400, below $500 minimum)

Should fail with error message

Withdraw $7400 (leaves $600) - should succeed


## Test 3: Overdraft (Checking)

Create a customer with checking account having $3500

Login to ATM

Withdraw $4000 (balance becomes -$500)

Should succeed (within $1000 overdraft limit)

Try to withdraw another $600 (would be -$1100)

Should fail (exceeds overdraft limit)


## Test 4: Intra-Customer Transfer

Create a customer with both savings and checking accounts

Login to ATM and select savings account

Transfer $1000 to checking account

Check both balances to verify


## Test 5: Cross-Customer Transfer
Have at least 2 customers with accounts

Login as first customer

Select any account

Transfer $500 to second customer's account number

Login as second customer to verify received funds

## Test 6: Admin Account Creation

Login as admin

Register a new customer

Create new savings account for that customer

Set initial balance: $2000

Note the new account number

Login as that customer and verify the account appears


## Project Structure src/
├── BankAccount.java          (Abstract class)

├── SavingsAccount.java       (Extends BankAccount)

├── CheckingAccount.java      (Extends BankAccount)

├── Customer.java             (Entity)

├── Transaction.java          (Entity)

├── Bank.java                 (Data management)

├── ATM.java                  (Customer UI)

├── BankAdministrator.java    (Admin UI)

├── AccountStatus.java        (Type definitions)

├── TransactionType.java      (Type definitions)

├── TransactionStatus.java    (Type definitions)             

├── AccountBlockedException.java   (Custom exceptions)

├── InsufficentFundsException.java (Custom exceptions)

├── InvalidAccountException.java   (Custom exceptions)

├── InvalidAmountException.java    (Custom exceptions)      

└── BankingSystemApp.java     (Main entry point)
