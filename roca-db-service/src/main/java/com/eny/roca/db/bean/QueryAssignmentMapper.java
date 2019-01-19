package com.eny.roca.db.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QueryAssignmentMapper implements RowMapper<QueryAssignment> {

	@Override
	public QueryAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
		//select subscriptionid,fromassignment,toassignment,comments from rocaserviceteam.SubscriptionAssignment where subscriptionid = 15 AND IsActive = 1 order by createdon
		QueryAssignment subscriptionAssignmentBean = new QueryAssignment();
		subscriptionAssignmentBean.setComments(rs.getString("comments"));
		subscriptionAssignmentBean.setFromAssignment(rs.getString("fromassignment"));
		subscriptionAssignmentBean.setToAssignment(rs.getString("toassignment"));
		subscriptionAssignmentBean.setId(rs.getInt("queryId"));
		return subscriptionAssignmentBean;
	}
}
