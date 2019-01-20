package com.eny.roca.bl.resource;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

import com.eny.roca.bean.QueryAssignment;
import com.eny.roca.bean.QueryBean;
import com.eny.roca.bean.StatusBean;
import com.eny.roca.bean.SubscriptionBean;
import com.eny.roca.bean.UserBean;
import com.google.gson.Gson;

@RestController
@RequestMapping("/rs/bl")
public class QueryResourceService {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Gson gson;
	
	
	@PostMapping("/saveQuery")
	public Boolean setQuery(@RequestBody QueryBean queryBean) {
		String json = gson.toJson(queryBean);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<Boolean> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/saveQuery", httpEntity, Boolean.class);
		return postForEntity.getBody();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getQuery")
	public List<QueryBean> getQuery(@RequestParam Integer userId) {
		String json = gson.toJson(userId);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<List> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/getQuery", httpEntity, List.class);
		return postForEntity.getBody();
	}
	
	@PostMapping("/setQueryAssignment")
	public Boolean queryAssignment(@RequestBody List<QueryAssignment> queryAssignments) {
		String json = gson.toJson(queryAssignments);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<Boolean> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/queryAssignment", httpEntity, Boolean.class);
		return postForEntity.getBody();
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/fetchQueryAssignmentById")
	public List<QueryBean> fetchQueryAssignmentById(@RequestParam Integer queryId) {
		String json = gson.toJson(queryId);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<List> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/fetchQueryAssignmentById", httpEntity, List.class);
		return postForEntity.getBody();
	}
	
	@GetMapping("/fetchQueryById")
	public QueryBean fetchQueryById(@RequestParam Integer queryId) {
		String json = gson.toJson(queryId);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<QueryBean> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/fetchQueryById", httpEntity, QueryBean.class);
		return postForEntity.getBody();
	}
	
	@PostMapping("/updateQueryStatus")
	public Boolean updateStatus(@RequestBody List<StatusBean> statusBean) {
		String json = gson.toJson(statusBean);
		HttpHeaders httpHeaders = new  HttpHeaders();
		httpHeaders.set("content-type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
		ResponseEntity<Boolean> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/updateQueryStatus", httpEntity, Boolean.class);
		return postForEntity.getBody();
	}
	
	@PostMapping("/fetchQueryStatus")
	public List<StatusBean> fetchQueryStatus(@RequestBody UserBean userBean) {
		List<StatusBean> queryBeans = new ArrayList<>();
		if(userBean.getMultipleStatus() != null && userBean.getMultipleStatus().size() > 0) {
			for(String stuatus : userBean.getMultipleStatus()) {
				userBean.setStatus(stuatus);
				String json = gson.toJson(userBean);
				HttpHeaders httpHeaders = new  HttpHeaders();
				httpHeaders.set("content-type", "application/json");
				HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
				ResponseEntity<List> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/fetchQueryStatus",httpEntity, List.class);
				queryBeans.addAll(postForEntity.getBody());
			}
		} else {
			String json = gson.toJson(userBean);
			HttpHeaders httpHeaders = new  HttpHeaders();
			httpHeaders.set("content-type", "application/json");
			HttpEntity<String> httpEntity = new HttpEntity<>(json,httpHeaders);
			ResponseEntity<List> postForEntity = restTemplate.postForEntity("http://roca-db-service/rs/db/fetchQueryStatus",httpEntity, List.class);
			queryBeans = postForEntity.getBody();			
		}
		
		return queryBeans;
	}
}
