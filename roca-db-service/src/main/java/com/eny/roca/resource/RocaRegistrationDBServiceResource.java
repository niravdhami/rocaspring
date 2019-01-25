package com.eny.roca.resource;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eny.roca.dao.RegistrationDao;
import com.eny.roca.dao.UserRegistrationDao;
import com.eny.roca.db.bean.UserRegistration;

@RestController
@RequestMapping("/rs/db")
public class RocaRegistrationDBServiceResource {
	
	@Autowired
	private UserRegistrationDao userRegistrationDa;
	
	@GetMapping("/getRegister")
	public List<UserRegistration> registerNewUser() {
		return (List<UserRegistration>) userRegistrationDa.getAllRegistration();
	}
	
	@GetMapping("/getUserRegister")
	public UserRegistration registerNewsingleUser(@RequestParam Integer Id) {
		return  userRegistrationDa.getUserRegistrationId(Id);
	}

	@PostMapping("/setRegister")
	public Integer registerNewUser(@RequestBody UserRegistration userRegistration) {
		return userRegistrationDa.setUserRegistrationId(userRegistration);
	}
	
	@GetMapping("/validateEmail")
	public Boolean validateEmailId(@RequestParam String email) {
		return userRegistrationDa.validateEmailId(email);
	}
	
	@PostMapping("/verifyEmail")
	public Integer verifyEmailId(@RequestBody String email) {
		return userRegistrationDa.verifyEmailId(email);
	}
	
	@PostMapping("/validateMobileNo")
	public Integer validateMobileNo(@RequestBody Long mobileNo) {
		return userRegistrationDa.validateMobileNo(mobileNo);
	}
	
	@PostMapping("/sendOtp")
	public Integer sendOtp(@RequestBody String mobileNo) {
		return userRegistrationDa.sendOtp(mobileNo);
	}
	
	@PostMapping("/verifyOtp")
	public Integer verifyOtp(@RequestParam String mobileNo, @RequestParam Integer otp) {
		return userRegistrationDa.verifyOtp(mobileNo, otp);
	}
}
