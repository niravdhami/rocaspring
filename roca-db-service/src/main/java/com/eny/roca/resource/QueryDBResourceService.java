package com.eny.roca.resource;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eny.roca.dao.QueryDao;
import com.eny.roca.db.bean.QueryAssignment;
import com.eny.roca.db.bean.QueryBean;
import com.eny.roca.db.bean.StatusBean;
import com.eny.roca.db.bean.SubscriptionBean;
import com.eny.roca.db.bean.UserBean;


@RestController
@RequestMapping("/rs/db")
public class QueryDBResourceService {
	
	@Autowired
	private QueryDao queryDao;

	@PostMapping("/saveQuery")
	public Boolean query(@RequestBody QueryBean queryBean) {
		return queryDao.saveQueryUser(queryBean) > 0 ? true : false;
	}

	@PostMapping("/getQuery")
	public List<QueryBean> getQuery(@RequestBody Integer userId) {
		return queryDao.getQuery(userId);
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/queryAssignment")
	public Boolean queryAssignment(@RequestBody List<QueryAssignment> queryAssignment) {
		return queryDao.saveQueryAssignment(queryAssignment) > 0 ? true : false;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/postQueryAssignment")
	public Boolean postQueryAssignment(@RequestBody List<QueryAssignment> queryAssignment) {
		return queryDao.postQueryAssignment(queryAssignment) > 0 ? true : false;
	}
	
	@PostMapping("/fetchQueryAssignmentById")
	public List<QueryAssignment> fetchQueryAssignmentById(@RequestBody Integer queryId) {
		return queryDao.getQueryAssignment(queryId);
	}
	
	@PostMapping("/fetchQueryById")
	public QueryBean fetchQueryById(@RequestBody Integer queryId) {
		return queryDao.fetchQueryById(queryId);
	}
	
	@PostMapping("/updateQueryStatus")
	public Boolean updateStatus(@RequestBody List<StatusBean> StatusBean) {
		return queryDao.updateStatus(StatusBean) > 0 ? true : false;
	}
	
	@PostMapping("/fetchQueryStatus")
	public List<QueryBean> fetchUserSubscriptionStatus(@RequestBody UserBean userBean) {
		List<QueryBean> queryBean = queryDao.fetchQueryStatus(userBean.getStatus());
		return queryBean;
	}
}
