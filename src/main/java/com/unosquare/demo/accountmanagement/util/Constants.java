package com.unosquare.demo.accountmanagement.util;

public final class Constants {


	public static final String EMPTY_STRING = "";
	
	public static final long MAX_PIN_VALUE = 9999L;
	public static final long MIN_PIN_VALUE = 1000L;
	public static final long MAX_ACCOUNT_VALUE = 999999999999L;
	public static final long MIN_ACCOUNT_VALUE = 100000000000L;
	
	public static final Long TRASACTION_TYPE_DEPOSIT = 1L;
	public static final Long TRASACTION_TYPE_WITHDRAWAL = 2L;
	public static final Long TRASACTION_TYPE_DEBIT = 3L;
	public static final Long TRASACTION_TYPE_CHECK = 4L;
	
	public static final int ERROR_CODE = -1;
	public static final int SUCCESS_CODE = 1;
	public static final String GENERAL_SUCCESS_MESSAGE = "Successful operation";
	
	
	private Constants(){
		
	}
	
}
