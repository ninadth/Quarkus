package com.clover.payload;


public class ApiResponce {


	private boolean success;
	
	private String message;



	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiResponce(boolean success, String message) {
		super();
	
		this.success = success;
		this.message = message;
	}

	public ApiResponce() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
