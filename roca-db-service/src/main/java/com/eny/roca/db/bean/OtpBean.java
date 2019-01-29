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
@Table(name="roca_Users_Otp_Verification")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OtpBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id")
	private int id;
	
	@Column(name="mobile_Number")
	private String mobileNumber;
	
	@Column(name="otp")
	private int otp;
	
	@Column(name="is_Expired")
	private int isExpired;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	
	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(int isExpired) {
		this.isExpired = isExpired;
	}
}
