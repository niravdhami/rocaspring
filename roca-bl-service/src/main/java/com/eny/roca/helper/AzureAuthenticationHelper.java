package com.eny.roca.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.eny.roca.bean.PasswordProfile;
import com.eny.roca.bean.RegisteredUserResponse;
import com.eny.roca.bean.RegistrationTokenBean;
import com.eny.roca.bean.RegistrationTokenResponseBean;
import com.eny.roca.bean.UserCreationRequestBean;
import com.eny.roca.bean.UserRegistration;
import com.google.gson.Gson;

@Component
public class AzureAuthenticationHelper {
 
	
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
	private Gson gson;
	
	public String getTokenforRegistration(String code, String state) {
		try {
			
		
		String url = "https://login.microsoftonline.com/"+tenantId+"/oauth2/token";
	
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.add("content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		 MultiValueMap<String, String> map= new LinkedMultiValueMap();
	 
		map.add("client_id", clientid);
		map.add("grant_type", "client_credentials");
		map.add("client_secret", clientSecret);
		map.add("resource", "https://graph.microsoft.com");
		//map.add("code", "OAQABAAIAAACEfexXxjamQb3OeGQ4Gugvzz9YECZtNNNu29XXRluBnLgsiafHjqhYizFkkyHta4LsEHxwWYOobRXxJb2Ai8PZK4m9IHMJU2WE66XB-J6PRD3o4vzhUMINE9l910rGuNKyzEpfT1XNZlvIHNEG-Nr0J6YjpbQijL_Mc3bPaCWHHr79z5vvxRpTj_s0bw9Pd8JnZDckDO0vAyoghszPaABC7SDYCp0oPgVs-jQTuEqrd_VmnwM_7Ifa4cVPNt37ciaazz58stR4wBhZhxfFquIifialB0-MFc3XsBzf9sIb_GxIA6IBHQPBC-UgLFxduz_X1F1ZY9It8VSREiUbrNaG5CtUvU5cwmr2W50lyLvOnNZiXH7UmVqikcwhhpfGbPlXCXt5_XLwAwEOXsblP1ON4huD_9FZ42hYrL_pzD7tVERooUsfuS1OiSWRARqU7zSi82Ai2bMMA-46D2vHtrU7y4m38n1f2P8Bda418CKw5A3uIntdl_BSaNF2Bkp8uAsyvbIqqkcJTamvj1E0OIgMyllYzY6eIjuR5wsTsOVa7cPyihWJ3XC_5O2JhvhxV9ERPnQ3eRKfAhYkgHJY4lBxQx1c152eSEhtpSbo2xkwLY1j247B2u6-rpT3Xog9rySUgMhmrlvCy9s0rEHqNKLYAeE3eYpGuBeHWYz3WMev1HxyYZ0vL38VdFL7ppa1xrjmc9wh0sZ9C4UAJ5eoHdEb4FkP_Aat74_R6bY1u6IUfjNDe1gGA02kJ7mt452VWW7ImzVE4GvYU_WaVmf4OOLi8NigPXsQtZBbb9Wv6_i3Ubp_aulV518x9IdnID1CfpYbD2bsHHgnnKGiCkRyMn0AY5M-TCOT2L1KVPMOHlEhgdTzmGInuJ4plI6hJC8QNpnN3-KS6-4W-tKU2zPs5SAsNTQkLa1QvoV7dMXBWOQyTFhyCE3nxpjH9fIx0zP_hdQxXWEpDlLisf2VUxYhnYASpmFGCdq0Ha6yvLq4oKq1kWml8aj4cXFfTFY1pdvVIjscIUNH-qmOKFtShThuUIunS9HFdQDk3TnM_cLlna-_EVMGLibCG0c2TZcYuMqPI1cEmETHcQMQxSsGKP494hmkbVm3oZb2ko2OrAVqsLO--o3weTqk0EMBtJ9L9j0-m2d4gZpcoJaWJxLh6XIbvQIR-ZFn54F0AKcwAXlVLHP7fjEIwibN3gpRAzkQVXY_lRuBdin8a0VH1ou1GLSpbZlrrlH9FtrLH7BOpm0751P00LyNpc75PJQpGoKZDAieN5YgAA"); 
		//map.add("code",code);
		//String raw="grant_type=authorization_code&code="+code+"&client_id="+clientid+"&client_secret="+clientSecret+"&scope=https://graph.microsoft.com/Directory.AccessAsUser.All";
		HttpEntity< MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

		//HttpEntity<String> httpEntity = new HttpEntity<>( "",httpHeaders);
		 RestTemplate restTemp = new RestTemplate();
		 ResponseEntity<String> postForEntity = restTemp.postForEntity(url, request, String.class);
		 if(postForEntity.getStatusCode().is2xxSuccessful()) {
			 RegistrationTokenResponseBean fromJson = gson.fromJson(postForEntity.getBody(), RegistrationTokenResponseBean.class);
			 return fromJson.getAccess_token();// postForEntity.getBody().getAccess_token();
		 }else {
			 return null;
		 }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpHeaders extracted(String code) {
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
		httpHeaders.add("client_id", clientid);
		httpHeaders.add("client_Secret", clientSecret);
		httpHeaders.add("code", code);
		httpHeaders.add("grant_type", "authorization_code");
		httpHeaders.add("scope", "https://ganeshbhure1gmail.onmicrosoft.com/8057cfc8-15b1-4f95-968f-65100b837e17/user_impersonation");
		//MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
		 RegistrationTokenBean registrationTokenBean = new RegistrationTokenBean();
		 registrationTokenBean.setClient_id(clientid);
		 registrationTokenBean.setClient_Secret(clientSecret);
		 registrationTokenBean.setCode(code);
		 registrationTokenBean.setGrant_type("authorization_code");
		 registrationTokenBean.setScope("https://ganeshbhure1gmail.onmicrosoft.com/8057cfc8-15b1-4f95-968f-65100b837e17/user_impersonation");
		 //https://ganeshbhure1gmail.onmicrosoft.com/8057cfc8-15b1-4f95-968f-65100b837e17/user_impersonation
		 String json = gson.toJson(registrationTokenBean);
		return httpHeaders;
	}

	public RegisteredUserResponse getUserPrincipalName(String token, UserRegistration userRegistration) {
		String url = "https://graph.microsoft.com/v1.0/users";
		//restTemplate.postForEntity("", request, responseType)
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		httpHeaders.set("Authorization", "Bearer "+token);
		UserCreationRequestBean userCreationRequestBean = new UserCreationRequestBean();
		userCreationRequestBean.setAccountEnabled(true);
		userCreationRequestBean.setDisplayName(userRegistration.getLegalEntityName());
		userCreationRequestBean.setMailNickname(userRegistration.getEmailId().substring(0, userRegistration.getEmailId().indexOf("@")));
		userCreationRequestBean.setUserPrincipalName(userRegistration.getEmailId().substring(0, userRegistration.getEmailId().indexOf("@")) + "@ganeshbhure1gmail.onmicrosoft.com");
		PasswordProfile passwordProfile = new PasswordProfile();
		passwordProfile.setForceChangePasswordNextSignIn(true);
		passwordProfile.setPassword("Kuma2222"); //Need to call random string..
		userCreationRequestBean.setPasswordProfile(passwordProfile);
		String json = gson.toJson(userCreationRequestBean);
		HttpEntity<String> httpEntity = new HttpEntity<>( json,httpHeaders);

		 RestTemplate restTemp = new RestTemplate();
		 ResponseEntity<RegisteredUserResponse> postForEntity = restTemp.postForEntity(url, httpEntity, RegisteredUserResponse.class);

		return postForEntity.getBody();
	}

}
