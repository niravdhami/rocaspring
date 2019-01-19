package com.eny.roca.bl.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eny.roca.bean.MasterData;
import com.google.gson.Gson;

@RestController
@RequestMapping("/rs/bl")
public class ServiceTeamResourceService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Gson gson;
	
	@GetMapping("/rocajrmembers")
	public List<MasterData> getRocaJuniorTeamMembers() {
		 ResponseEntity<List> registrationRoles = restTemplate.getForEntity("http://roca-db-service/rs/db/rocajrmembers", List.class);
		 return (List<MasterData>)registrationRoles.getBody();
	}
	
	@GetMapping("/rocasrmembers")
	public List<MasterData> getRocaSeniorTeamMembers() {
		 ResponseEntity<List> registrationRoles = restTemplate.getForEntity("http://roca-db-service/rs/db/rocasrmembers", List.class);
		 return (List<MasterData>)registrationRoles.getBody();
	}
	
	@GetMapping("/jrmemberQueryAlloacted")
	public Integer jrmemberQueryAlloacted(@RequestParam String jrMember) {
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(jrMember,httpHeaders);
		ResponseEntity<Integer> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/jrmemberQueryAlloacted", httpEntity, Integer.class);
		return postForEntity.getBody();
	}
}
