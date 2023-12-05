package com.clover.entity;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQuery(
    name = "insert_employee",
    procedureName = "insert_employee",
    parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_empName"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_empDept"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_empCast"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "p_empSalary"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "p_empContact")
    }
)
public class Employee{
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer empId;
	
	
    private String empName;
	
    private String empDept;
	
    private String empCast;
    
    private Long empSalary;
    
    private Long empContact;
    
    
    public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
    
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpDept() {
		return empDept;
	}
	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}
	public String getEmpCast() {
		return empCast;
	}
	public void setEmpCast(String empCast) {
		this.empCast = empCast;
	}
	public Long getEmpSalary() {
		return empSalary;
	}
	public void setEmpSalary(Long empSalary) {
		this.empSalary = empSalary;
	}
	public Long getEmpContact() {
		return empContact;
	}
	public void setEmpContact(Long empContact) {
		this.empContact = empContact;
	}
	
	public Employee(Integer empId, String empName, String empDept, String empCast, Long empSalary, Long empContact) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empDept = empDept;
		this.empCast = empCast;
		this.empSalary = empSalary;
		this.empContact = empContact;
	}
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empDept=" + empDept + ", empCast=" + empCast
				+ ", empSalary=" + empSalary + ", empContact=" + empContact + "]";
	}
	  
}