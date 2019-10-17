package com.unosquare.demo.accountmanagement.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {

	public void login(String ssn, String pin) {
		
		String params = "{\n"+
				"\"ssn\": \""+ssn+"\",\n" +
				"\"accountPin\": \""+pin+"\"\n" +
			"}";
		
		String endpoint = "http://localhost:8080/account/authentication";
		doPost(endpoint, params);
	}
	
	public void openAccount(String ssn, String firstName, String lastName) {
		
		String params = "{\n"+
				"\"ssn\": \""+ssn+"\",\n" +
				"\"firstName\": \""+firstName+"\",\n" +
				"\"lastName\": \""+lastName+"\"\n" +
			"}";
		
		String endpoint = "http://localhost:8080/account";
		doPost(endpoint, params);
	}
	
	public void closeAccount(String accountNumber) {
		
		String endpoint = "http://localhost:8080/account/" + accountNumber;
		doDelete(endpoint);
	}
	
	public void balance(String accountNumber) {
		
		String endpoint = "http://localhost:8080/account/balance/" + accountNumber;
		doGet(endpoint);
	}
	
	public void deposit(String accountNumber, String pin, String amount) {
		
		String params = "{\n"+
				"\"accountNumber\": \""+accountNumber+"\",\n" +
				"\"pin\": \""+pin+"\",\n" +
				"\"amount\": \""+amount+"\"\n" +
			"}";
		
		String endpoint = "http://localhost:8080/account/deposit";
		doPut(endpoint, params);
	}
	
	public void withdrawal(String accountNumber, String pin, String amount) {
		
		String params = "{\n"+
				"\"accountNumber\": \""+accountNumber+"\",\n" +
				"\"pin\": \""+pin+"\",\n" +
				"\"amount\": \""+amount+"\"\n" +
			"}";
		
		String endpoint = "http://localhost:8080/account/withdrawal";
		doPut(endpoint, params);
	}
	
	private void doGet(String endpoint) {
		
		try {
			URL urlForGetRequest = new URL(endpoint);
		    String readLine = null;
		    HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
		    connection.setRequestMethod("GET");
		    
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in.readLine()) != null) {

	            response.append(readLine);
	        }
	        in .close();
	        System.out.println("Result " + response.toString());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doPost(String endpoint, String params) {
		
		try {
			
			URL url = new URL(endpoint);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);
		    
		    OutputStream os = connection.getOutputStream();
		    os.write(params.getBytes());
		    os.flush();
		    os.close();
		    
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {

	            response.append(inputLine);
	        } 
	        in.close();
	        System.out.println("####### Result: "+response.toString());
	
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doPut(String endpoint, String params) {
		
		try {
			
			URL url = new URL(endpoint);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);
		    
		    OutputStream os = connection.getOutputStream();
		    os.write(params.getBytes());
		    os.flush();
		    os.close();
		    
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {

	            response.append(inputLine);
	        } 
	        in.close();
	        System.out.println("####### Result: "+response.toString());
	
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doDelete(String endpoint) {
		
		try {
			URL urlForGetRequest = new URL(endpoint);
		    String readLine = null;
		    HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
		    connection.setRequestMethod("DELETE");
		    
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in.readLine()) != null) {

	            response.append(readLine);
	        }
	        in .close();
	        System.out.println("Result " + response.toString());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
