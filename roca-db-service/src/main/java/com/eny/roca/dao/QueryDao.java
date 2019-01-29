package com.eny.roca.dao;

import java.util.List;

import com.eny.roca.db.bean.QueryAssignment;
import com.eny.roca.db.bean.QueryBean;
import com.eny.roca.db.bean.StatusBean;
import com.eny.roca.db.bean.SubscriptionBean;

public interface QueryDao {

	Integer saveQueryUser(QueryBean queryBean);

	List<QueryBean> getQuery(Integer userId);

	Integer saveQueryAssignment(List<QueryAssignment> queryAssignment);

	List<QueryAssignment> getQueryAssignment(Integer queryId);

	QueryBean fetchQueryById(Integer queryId);

	Integer updateStatus(List<StatusBean> statusBean);

	List<QueryBean> fetchQueryStatus(String status);

	Integer postQueryAssignment(List<QueryAssignment> queryAssignment);

}
