# accountmanagement
Demo application for account management

Requirements
  This application was tested under the following environment:
    - java 8
    - maven 3.3.9
  
Steps To Build and Run the application follow next steps:
  1. clone the application
  2. go to root folder in a terminal (command line)
  3. execute command: mvn clean package
  4. execute command: java -jar target\accountmanagement-0.0.1-SNAPSHOT.jar
  5. in other terminal go to target/classes inside the root folder and execute the command: java com.unosquare.demo.accountmanagement.presentation.AccountManagement
  6. create an account and try the other options
  7. execute debit and checks operation from external aplicacions through the rest endpoint:
    http://localhost:8080/account/process
    method: POST
    Body: {
            "accountNumber": "359100631511",
            "pin": "5637",
            "amount": "1000",
            "transactionType": "3",
            "description": "Debit"
          }
          
     Note: The allowed transaction types are 3=debit and 4=check. replace the account number and pin with your owns.  
