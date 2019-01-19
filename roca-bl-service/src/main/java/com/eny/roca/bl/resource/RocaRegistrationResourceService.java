package com.eny.roca.bl.resource;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eny.roca.bean.EmailValidationBean;
import com.eny.roca.bean.RegisteredUserResponse;
import com.eny.roca.bean.UserBean;
import com.eny.roca.bean.UserRegistration;
import com.eny.roca.bl.services.SmtpMailSender;
import com.eny.roca.bl.services.ValidateEmail;
import com.eny.roca.helper.AzureAuthenticationHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/rs/bl")
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
	
	@Autowired
	private AzureAuthenticationHelper azureAuthenticationHelper;
	
	
	@Autowired
	private Gson gson;
	
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
	
	@GetMapping("/sendOtp")
	public Integer updatePaceId(@RequestParam String mobileNo) {
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(mobileNo,httpHeaders);
		ResponseEntity<Integer> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/sendOtp", httpEntity, Integer.class);
		return postForEntity.getBody();
		
	}
	
	@GetMapping("/verifyOtp")
	public Integer verifyOtp(@RequestParam String mobileNo, @RequestParam Integer otp) {
		String json = gson.toJson(otp);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("mobileNo" ,mobileNo); 
		map.add("otp", json);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<Integer> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/verifyOtp", request, Integer.class);
		return postForEntity.getBody();
		
	}

}
