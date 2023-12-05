package com.clover.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "user")
public class User extends PanacheEntity {

	//@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonbProperty("employeeName")
	@NotBlank(message = "NAME CAN'NT BLANK")
    private String userName;


	@NotBlank(message = "ADDRESS CAN'NT BLANK")
    private String userAddress;

	@Length(min = 10,max = 10, message = "length is min 10 and max 10")	
    private String userNo;
    
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

	
    
    public User(long id, String userName, String userAddress, String userNo, LocalDate date) {
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
