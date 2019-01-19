package com.eny.roca.dao;

import java.security.SecureRandom;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.eny.roca.db.bean.UserRegistration;
import com.eny.roca.db.services.SmtpMailSender;
import com.eny.roca.db.services.RandomString;
import com.eny.roca.db.services.ValidateEmail;

@Repository
public class RegistrationDaoImpl implements RegistrationDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	private SmtpMailSender smtpMailSender;
	
	
	@Override
	public List<UserRegistration> getRegistrationData() {
		
		return jdbcTemplate.query("select * from rocausers.RocaUserRegistration", new UserRegistrationDataMapper());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer setMasterData(UserRegistration userRegistration) {
		
		String setData = "INSERT INTO rocausers.RocaUserRegistration "
				+ "	(LegalEntityName,contactperson,roleId,emailid,mobileNumber,industryId,CountryId,password) "
				+ "VALUES "
				+ "  (:legalEntity,:name,:role,:email,:mobile,:industries,:countryCode,:password)";
		
		String ran = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx" + RandomString.specialCharacter;
		RandomString autoGenPassword = new RandomString(8, new SecureRandom(), ran);
		
		String password = autoGenPassword.nextString();
		int i = 0;
		try {
			
			Map namedParameters = new HashMap();
			namedParameters.put("legalEntity", userRegistration.getLegalEntityName());
			namedParameters.put("name", userRegistration.getContactPerson());
			namedParameters.put("role", Integer.valueOf(userRegistration.getRoleId()));
			namedParameters.put("email", userRegistration.getEmailId());
			namedParameters.put("mobile", Long.valueOf(userRegistration.getMobileNo()));
			namedParameters.put("industries", userRegistration.getIndustryId());
			namedParameters.put("countryCode", userRegistration.getCountryCode());
			namedParameters.put("password", password);	
			
			i = namedTemplate.update(setData, namedParameters);
			if(i > 0) {
				String link = "http://localhost:4200/login/email-verify?emailId="+userRegistration.getEmailId();
				StringBuilder  sb = new StringBuilder();
				sb.append("<html><head> WELCOME TO ROCA Services, <head> <body><br />   Activation Link :  <a href=\""+link+"\">Verify EmailId Here</a><br /><br /> You can Login to ROCA site from below Credentials <br />");
				sb.append("UserName : " + userRegistration.getEmailId() + "<br />");
				sb.append("Password : " + password);
				smtpMailSender.send(userRegistration.getEmailId(), "ROCA Account Activation", sb.toString());
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return i;
	}

	@Override
	public Boolean validateEmailId(String emailId) {
			  
			  return ValidateEmail.isAddressValid(emailId);  
	}

	@Override
	public Integer verifyEmailId(String email) {
		
		String setData = "UPDATE rocausers.RocaUserRegistration "
				+ " Set isemailVerified = 1"
				+ " where emailid = :emailid";
		
		int i = 0;
		try {

			Map<String, String> namedParameters = new HashMap<String, String>();
			namedParameters.put("emailid", email);
			i = namedTemplate.update(setData, namedParameters);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	@Override
	public Integer validateMobileNo(Long mobileNo) {
		Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from rocausers.RocaUserRegistration where mobileNumber=?", new Object[] {mobileNo},  Integer.class);
		return queryForObject;
	}

	@Override
	public Integer sendOtp(String mobileNo) {
		String ran = RandomString.digits;
		RandomString autoGenPassword = new RandomString(4, new SecureRandom(), ran);
		String otp = autoGenPassword.nextString();
		//gatewayAPITest.sendMessage(mobileNo, otp);
		
		String setData = "INSERT INTO rocausers.OtpVerification "
				+ "	(mobileNumber,otp) "
				+ "VALUES "
				+ "  (:mobilenumber,:otp)";
		
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("mobilenumber", mobileNo);
		namedParameters.put("otp", otp);
		namedTemplate.update(setData, namedParameters);
		
		return 1;
	}

	@Override
	public Integer verifyOtp(String mobileNo, Integer otp) {
		
		Integer verify = jdbcTemplate.queryForObject("select count(*) from rocausers.OtpVerification where mobileNumber=? and otp=?", new Object[] {mobileNo, otp},  Integer.class);
		return verify;
	}
}
