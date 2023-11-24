package com.clover.entity;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User extends PanacheEntity {

	//@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private String userName;

    private String userAddress;

    private int userNo;
    
    private LocalDate date;

    public long getid() {
        return id;
    }

    public void setid(long id) {
        this.id = id;
    }

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

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
    
    public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	
    
    public User(long id, String userName, String userAddress, int userNo, LocalDate date) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAddress = userAddress;
		this.userNo = userNo;
		this.date = date;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userAddress=" + userAddress + ", userNo="
				+ userNo + ", date=" + date + "]";
	}

}
