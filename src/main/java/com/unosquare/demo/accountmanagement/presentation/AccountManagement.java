package com.unosquare.demo.accountmanagement.presentation;

import java.util.Scanner;

/**
 * @author Landeros
 *
 */
public class AccountManagement {

	private boolean isLoggedIn;
	private Scanner input;
	private RestClient restClient;
	
	public AccountManagement() {
		
		input = new Scanner(System.in);
		restClient = new RestClient();
	}
	
	public static void main(String []args) {
		
		AccountManagement accountManagement = new AccountManagement();
		
		accountManagement.start();
		
	}

	public void start() {
		
		int selectedOption = 0;
		
		while ( true ) {
		
			showMenu();
			while ( true ) {
				
				System.out.print("Selected option: ");
				try {
					
					selectedOption = input.nextInt();
				} catch(Exception e) {
					input.nextLine();
					selectedOption = 0;
				}
				if ( selectedOption > 2 && !isLoggedIn) {
					System.out.println("User not logged in.");
					selectedOption = 0;
				}
				if ( selectedOption >= 1 && selectedOption <= 7 ) {
					isLoggedIn = true;
					break;
				}
			}
			
			switch(selectedOption) {
			
				case 1: 
					login();
					break;
				case 2:
					openAccount();
					break;
				case 3:
					closeAccount();
					break;
				case 4:
					deposit();
					break;
				case 5:
					withdrawal();
					break;
				case 6:
					balance();
					break;
				case 7:
					logout();
					break;
				default:
					break;
			}
			System.out.println("Pess enter key to continue...");
			String aux = input.nextLine();
		}
	}
	public void showMenu() {
		
		System.out.println("#################################################################");
		System.out.println("#				Account Management System						#");
		System.out.println("#################################################################");
		System.out.println("Choose an option:");
		System.out.println("	1. Login");
		System.out.println("	2. Open a new account");
		System.out.println("	3. Close an account");
		System.out.println("	4. Make a deposit");
		System.out.println("	5. Make a withdrawal");
		System.out.println("	6. Consult account balance");
		System.out.println("	7. Logout");
	}
	
	public void login() {
		
		input.nextLine();
		System.out.println(">>>>> Login");
		System.out.print("SSN: ");
		String ssn = input.nextLine();
		System.out.println();
		System.out.print("PIN: ");
		String pin = input.nextLine();
		System.out.println();
		
		restClient.login(ssn, pin);
		
	}
	
	public void openAccount() {
		
		input.nextLine();
		System.out.println(">>>>> Open account");
		System.out.print("SSN: ");
		String ssn = input.nextLine();
		System.out.println();
		System.out.print("First name: ");
		String firstName = input.nextLine();
		System.out.println();
		System.out.print("Last name: ");
		String lastName = input.nextLine();
		System.out.println();
		
		restClient.openAccount( ssn, firstName, lastName );
	}
	
	public void closeAccount() {
		
		input.nextLine();
		System.out.println(">>>>> Close account");
		System.out.print("Account number: ");
		String accountNumber = input.nextLine();
		System.out.println();
		
		restClient.closeAccount( accountNumber );
	}

	public void deposit() {
		
		input.nextLine();
		System.out.println(">>>>> Deposit");
		System.out.print("Account number: ");
		String accountNumber = input.nextLine();
		System.out.println();
		System.out.print("amount: ");
		String amount = input.nextLine();
		System.out.println();
		System.out.print("pin: ");
		String pin = input.nextLine();
		System.out.println();
		
		restClient.deposit(accountNumber, pin, amount);
	}

	public void withdrawal() {
		
		input.nextLine();
		System.out.println(">>>>> Withdrawal");
		System.out.print("Account number: ");
		String accountNumber = input.nextLine();
		System.out.println();
		System.out.print("amount: ");
		String amount = input.nextLine();
		System.out.println();
		System.out.print("pin: ");
		String pin = input.nextLine();
		System.out.println();
		
		restClient.withdrawal(accountNumber, pin, amount);
	}

	public void balance() {
		
		input.nextLine();
		System.out.println(">>>>> Balance");
		System.out.print("Account number: ");
		String accountNumber = input.nextLine();
		System.out.println();
		
		restClient.balance( accountNumber );
	}
	
	public void logout() {
		
		isLoggedIn = false;
		System.out.println("########### User logged out");
		input.nextLine();
		
	}
}
