package com.eny.roca.db.bean;

import java.io.Serializable;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="roca_users_roca_user_registration")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRegistration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id")
	private int id;
	
	@Column(name="Legal_Entity_Name")
	private String legalEntityName;
	
	@Column(name="Contact_Person")
	private String contactPerson;
	
	@Column(name="Role_Id")
	private int roleId;
	
	@Column(name="Email_Id")
    private String emailId;
	
	@Column(name="Mobile_Number")
	private Long mobileNo;
	
	@Column(name="Industry_Id")
	private String industryId;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Country_Id")
	private int countryCode;
	
	@Column(name="Is_Email_Verified")
	private int isEmailVerified;
	
	@Column(name="Is_Mobile_Verified")
	private int isMobileVerified;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getLegalEntityName() {
		return legalEntityName;
	}

	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	public int getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(int isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public int getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(int isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

}
