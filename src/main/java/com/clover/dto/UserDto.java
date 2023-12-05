package com.clover.dto;

import java.time.LocalDate;

public class UserDto {

	
    private String userName;

    private String userAddress;

    private String userNo;
    
    private LocalDate date;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UserDto(String userName, String userAddress, String userNo, LocalDate date) {
		super();
		this.userName = userName;
		this.userAddress = userAddress;
		this.userNo = userNo;
		this.date = date;
	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
