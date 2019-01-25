package com.eny.roca.dao;
import java.util.List;

import com.eny.roca.db.bean.UserRegistration;
public interface UserRegistrationDao {
    List<UserRegistration> getAllRegistration();
    Integer setUserRegistrationId(UserRegistration registrationBean);
    UserRegistration getUserRegistrationId(int Id);
	Integer verifyEmailId(String email);
	Integer validateMobileNo(Long mobileNo);
	Integer sendOtp(String mobileNo);
	Integer verifyOtp(String mobileNo, Integer otp);
	Boolean validateEmailId(String emailId);
}
 