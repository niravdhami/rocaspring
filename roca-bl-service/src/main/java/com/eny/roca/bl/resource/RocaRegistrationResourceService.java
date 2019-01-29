package com.eny.roca.bl.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eny.roca.bean.EmailValidationBean;
import com.eny.roca.bean.OTPRequest;
import com.eny.roca.bean.OTPResponse;
import com.eny.roca.bean.RegisteredUserResponse;
import com.eny.roca.bean.UserBean;
import com.eny.roca.bean.UserRegistration;
import com.eny.roca.bl.services.SmtpMailSender;
import com.eny.roca.bl.services.ValidateEmail;
import com.eny.roca.helper.AzureAuthenticationHelper;
import com.eny.roca.rocablservice.config.RedisDBConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/rs/bl")
@FeignClient(name="EmployeeSearch" )
@RibbonClient(name="EmployeeSearch")
public class RocaRegistrationResourceService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SmtpMailSender smtpMailSender;
		
	@Value("${roca.registration.ad.tenantid}")
	private String tenantId; 
	
	@Value("${roca.registration.ad.clientid}")
	private String clientid; 
	
	
	@Value("${roca.registration.ad.responseType}")
	private String responseType; 
	
	
	@Value("${roca.registration.ad.responseMode}")
	private String responseMode; 
	
	
	@Value("${roca.registration.ad.scope}")
	private String userScope; 
	
	
	@Value("${roca.registration.ad.clientSecret}")
	private String clientSecret; 

	@Value("${roca.otp.caLayerDigiUser}")
	private String caLayerDigiUser;

	@Value("${roca.otp.caLayerDigiApiKey}")
	private String caLayerDigiApiKey;

	@Value("${roca.otp.caLayerDigiApiSecret}")
	private String caLayerDigiApiSecret;

	@Value("${roca.otp.url}")
	private URI otpUrl;
	@Autowired
	private AzureAuthenticationHelper azureAuthenticationHelper;
	
	
	@Autowired
	private Gson gson;

	@Autowired
	private RedisDBConfig redisDBConfig;

	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRegistrationData")
	public List<UserRegistration> getUserRegistration() {
		ResponseEntity<List<UserRegistration>> userRegistration = restTemplate.exchange("http://roca-db-service/rs/db/getRegister",HttpMethod.GET, null,  new ParameterizedTypeReference<List<UserRegistration>>() {});
		return userRegistration.getBody();
	}
	
	@PostMapping("/register")
	public Integer registerNewUser(@RequestBody UserRegistration userRegistration) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(userRegistration);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<Integer> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/setRegister", httpEntity, Integer.class);
		
		
		return postForEntity.getBody();
	}
	
	@GetMapping("/validateEmailId")
	public String validateEmail(@RequestParam String email) {

		String val = null;
		try {
			val = Boolean.toString(ValidateEmail.isEmailValid(email));
		} catch (Exception e) {
			val = "invalid Email";
		}
		
		
		return val;
	}
	
	@GetMapping("/send-mail")
	public Boolean sendMail(@RequestParam String email) throws MessagingException {
		
		String l = "http://localhost:8302/api/roca-bl-service/rs/bl/verifiedEmailId?email="+email;
		smtpMailSender.send(email, "ROCA Account Activation", "Link : " + l);
		return true;
		
	}
	
	
	@PostMapping("/verifiedEmailId")
	public EmailValidationBean VerifiedEmail(@RequestBody UserBean userbean) {
	//public String VerifiedEmail(@RequestParam String email) {
		EmailValidationBean emailValidationBean = new  EmailValidationBean();
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		String emailId = userbean.getEmailId();
		HttpEntity<String> httpEntity = new HttpEntity<>(emailId,httpHeaders); //email
		//HttpEntity<String> httpEntity = new HttpEntity<>(email,httpHeaders); 
		ResponseEntity<Integer> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/verifyEmail", httpEntity, Integer.class);
		Integer body = postForEntity.getBody();
		// once Email is verified lets proceed for AD Registration.
		String url = null;
		if (body > 0) {
			Optional<UserRegistration> userRegistrationDetails = getUserRegistration().stream()
					.filter(u -> u.getEmailId().equalsIgnoreCase(emailId)).findFirst();

			UserRegistration userRegistration = userRegistrationDetails.isPresent() ? userRegistrationDetails.get()
					: null;
			String token = azureAuthenticationHelper.getTokenforRegistration(emailValidationBean.getCode(),
					emailValidationBean.getState());
			RegisteredUserResponse registeredUserResponse = azureAuthenticationHelper.getUserPrincipalName(token,
					userRegistration);
			emailValidationBean.setRegistered(true);
			return emailValidationBean;
		}else if(body==0) {
			//return "Email id is already Verified";
			return null;
		}
		return null;
		//return postForEntity.getBody();
	}
	
	@PostMapping("/user/regisration/ad")
	public EmailValidationBean registerUserWithAzuer(@RequestBody EmailValidationBean emailValidationBean) {
		
		/*UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmailId("m3raj10111@gmail.com");
		userRegistration.setLegalEntityName("ABC11");*/
 		
	    Optional<UserRegistration> userRegistrationDetails = getUserRegistration().stream().filter(u-> u.getRegisrationId() == (Integer.valueOf(emailValidationBean.getState()))).findFirst();
	    UserRegistration userRegistration = userRegistrationDetails.isPresent() ? userRegistrationDetails.get() : null;
	  	String token = azureAuthenticationHelper.getTokenforRegistration(emailValidationBean.getCode(),emailValidationBean.getState());
		RegisteredUserResponse registeredUserResponse = azureAuthenticationHelper.getUserPrincipalName(token,userRegistration);
		//updat userprinciple name in our regisration and subscription table.
		//send an email with username and password
		if(null!=emailValidationBean) {
			emailValidationBean.setRegistered(true);
		}
		return emailValidationBean;
	}
	
	@GetMapping("/validateMobileNo")
	public Integer validateMobileNo(@RequestParam(value = "mobileNo") Long mobileno) {
		String json = gson.toJson(mobileno);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<Integer> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/validateMobileNo", httpEntity, Integer.class);
		return postForEntity.getBody();
	}
	
	@PostMapping("/sendOtp")
	public Integer sendOtp(@RequestBody UserRegistration userRegistration) {
		String mobileNo = "+" + userRegistration.getCountryCode() + "" + userRegistration.getMobileNo();
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		httpHeaders.set("digigst_username", caLayerDigiUser);
		httpHeaders.set("api_key", caLayerDigiApiKey);
		httpHeaders.set("api_secret", caLayerDigiApiSecret);
				
		JsonObject json = new JsonObject();
		json.addProperty("mobile_number", mobileNo);
		final GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		final String finalJson = gson.toJson(json);
		HttpEntity<String> httpEntity = new HttpEntity<>(finalJson,httpHeaders);
		ResponseEntity<OTPResponse> postForEntity = restTemplate.postForEntity(otpUrl, httpEntity, OTPResponse.class);
		OTPResponse otpBean = postForEntity.getBody();
		if(otpBean.getStatus_cd().equals("1")) {
			redisDBConfig.add(mobileNo, otpBean.getOtp(), 30 * 60);
			return 1;
		} else {
			return 0;
		}
		
	}
	
	@PostMapping("/verifyOtp")
	public Integer verifyOtp(@RequestBody UserRegistration userRegistration) {
		String mobileNo = "+" + userRegistration.getCountryCode() + "" + userRegistration.getMobileNo();
		String otp = redisDBConfig.get(mobileNo);
		if(otp != null && otp.equals(userRegistration.getOtp())) {
			return 1;
		} else {
			return 0;
		}		
	}

	@PostMapping("calayerSendOtp")
	public OTPResponse sendOtpDummy(@RequestBody OTPRequest otpRequest, HttpServletRequest request) {
		int n1 = (int)(Math.random() * 9 + 1);
		int n2 = (int)(Math.random() * 9 + 1);
		int n3 = (int)(Math.random() * 9 + 1);
		int n4 = (int)(Math.random() * 9 + 1);
		String str = "" + n1 + "" + n2 + "" + n3 + "" + n4;
		OTPResponse bean = new OTPResponse();
		System.out.println("OTP generated..." + str);
		bean.setOtp(str);
		bean.setStatus_cd("1");
		return bean;
	}
}
