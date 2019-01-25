package com.eny.roca.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.eny.roca.db.bean.QueryAdditionalDocDetails;
import com.eny.roca.db.bean.QueryAssignment;
import com.eny.roca.db.bean.QueryAssignmentMapper;
import com.eny.roca.db.bean.QueryBean;
import com.eny.roca.db.bean.QuestionBean;
import com.eny.roca.db.bean.StatusBean;
import com.eny.roca.db.bean.SubscriptionAssignment;
import com.eny.roca.db.bean.SubscriptionBean;

@Repository
public class QueryDaoImpl implements QueryDao {

	private static final int IS_ACTIVE = 1;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Integer saveQueryUser(QueryBean queryBean) {
		int update2 = 0;
		
			KeyHolder keyHolder = new GeneratedKeyHolder();
			String query = "INSERT INTO  rocausers.Query "
					+ "				 (QueryCaption, QueryFact, Category, FinancialYear, Status, isAssigned, inScope, comments, UserId) "
					+ "				 VALUES (:queryCaption, :queryFact, :Category, :financialYear, :status, :isAssigned, :inScope, :comment, :userId)";

			if (queryBean.getIsSubmit()) {
				queryBean.setStatus("New");
			} else {
				queryBean.setStatus("Saved");
			}

			SqlParameterSource fileParameters = new BeanPropertySqlParameterSource(queryBean);
			int update = namedParameterJdbcTemplate.update(query, fileParameters, keyHolder);
			int id = keyHolder.getKey().intValue();
			if (update == 1) {
				for(QuestionBean questionbean : queryBean.getQueationbeans()) {
				String query2 = "INSERT INTO  rocausers.Question "
						+ "				 (QuestionDescription, QueryId, Status, ModifiedQuestionDescription, isQuestionModified, comments, Answer) "
						+ "				 VALUES (:questionDescription, :queryId, :status, :modifiedQuestionDescription, :isQuestionModified, :comments, :answer)";

				
				Map<String, Object> map = new HashMap<>(1);
				map.put("questionDescription", questionbean.getQuestionDescription());
				map.put("queryId", id);
				map.put("status", questionbean.getQueStatus());
				map.put("modifiedQuestionDescription", questionbean.getModifiedQuestionDescription());
				map.put("isQuestionModified", questionbean.getIsQuestionModified());
				map.put("comments", questionbean.getQueComment());
				map.put("answer", questionbean.getAnswer());
				update2 = namedParameterJdbcTemplate.update(query2, map);
			}
			}
		
		return update2;
	}

	@Override
	public List<QueryBean> getQuery(Integer userId) {

		List<QueryBean> query = jdbcTemplate.query("select * from rocausers.Query qr where qr.UserId=?",
				new Object[] { userId }, new QueryDataMapper());
		for (QueryBean qr : query) {
			List<QuestionBean> listofQuestion = jdbcTemplate.query(
					"select que.* from rocaUSERS.question que where que.queryid=?", new Object[] { qr.getQueryId() },
					new QuestionDataMapper());
			qr.setQueationbeans(listofQuestion);
		}
		return query;
	}

	@Override
	public Integer saveQueryAssignment(List<QueryAssignment> queryAssignment) {
		
		List<Integer> update = new ArrayList<Integer>();
		int i = 0;
		for (QueryAssignment s : queryAssignment) {
			
			String getTeamRole = "select DISTINCT qr.name from rocaserviceteam.queryassignment qa inner join rocaserviceteam.serviceteamusers qu on qa.fromAssignment=qu.id inner join rocaserviceteam.serviceteamRole qr on qu.roleId=qr.id where qa.fromAssignment=?";
			String queryForTeamRole = jdbcTemplate.queryForObject(getTeamRole, new Object[] { s.getFromAssignment() },
					String.class);
			
			if(queryForTeamRole.equalsIgnoreCase("SENIOR")) {
				String query = "INSERT INTO  rocaserviceteam.QueryAssignment "
						+ "				 (QueryId, FromAssignment, ToAssignment, Comments) "
						+ "				 VALUES (:queryId,:fromAssignment,:toAssignment,:comments)";

				Map<String, Object> map = new HashMap<>(1);
				map.put("queryId", s.getId());
				map.put("fromAssignment", s.getFromAssignment());
				map.put("toAssignment", s.getToAssignment());
				map.put("comments", s.getComments());
				
				String getStatus = "select Status from rocausers.Query where rocausers.Query.Id=?";
				String queryForFromStatus = jdbcTemplate.queryForObject(getStatus, new Object[] { s.getId() },
						String.class);
				String queryForToStatus = null;

				if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
					String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=? and Condition=?";
					queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
							new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
				} else if (queryForFromStatus.equalsIgnoreCase("ANS_CREATED")) {
					String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=?";
					queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
							new Object[] { queryForFromStatus, s.getAction() }, String.class);
				} else {
					String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=?";
					queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus },
							String.class);
				}
				String query2 = "UPDATE  rocausers.Query SET Status = :status, Comments = :comments ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
				Map<String, Object> map2 = new HashMap<>(1);
				map2.put("status", queryForToStatus);
				map2.put("comments", s.getQueryComments());
				map2.put("id", s.getId());
				
				update.add(namedParameterJdbcTemplate.update(query, map));
				update.add(namedParameterJdbcTemplate.update(query2, map2));
			} else if(queryForTeamRole.equalsIgnoreCase("JUNIOR")) {
			String query = "INSERT INTO  rocaserviceteam.QueryAssignment "
					+ "				 (QueryId, FromAssignment, ToAssignment, Comments) "
					+ "				 VALUES (:queryId,:fromAssignment,:toAssignment,:comments)";

			Map<String, Object> map = new HashMap<>(1);
			map.put("queryId", s.getId());
			map.put("fromAssignment", s.getFromAssignment());
			map.put("toAssignment", s.getToAssignment());
			map.put("comments", s.getComments());
			
			if(s.getDocRequired() != 0) {
				for(QueryAdditionalDocDetails q : s.getQueryAdditionalDocDetails()) {
				String queryForAddDoc = "INSERT INTO  rocausers.QueryAdditionalDocs "
						+ "				 (QueryId, DocName, Type, DocData, FileExtention) "
						+ "				 VALUES (:queryId, :docName, :type, :docData, :fileExtention)";
				Map<String, Object> map4 = new HashMap<>(1);
				map4.put("queryId", s.getId());
				map4.put("docName", q.getDocName());
				map4.put("type", q.getDocType());
				map4.put("docData", q.getDocData());
				map4.put("fileExtention", q.getDocExtention());
				
				namedParameterJdbcTemplate.update(queryForAddDoc, map4);
				}	
			}	
			
			String getStatus = "select Status from rocausers.Query where rocausers.Query.Id=?";
			String queryForFromStatus = jdbcTemplate.queryForObject(getStatus, new Object[] { s.getId() },
					String.class);
			String queryForToStatus = null;

			if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
				String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=? and Condition=?";
				queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
						new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
			} else if (queryForFromStatus.equalsIgnoreCase("ANS_CREATED")) {
				String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=?";
				queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
						new Object[] { queryForFromStatus, s.getAction() }, String.class);
			} else {
				String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=?";
				queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus },
						String.class);
			}
			String query2 = "UPDATE  rocausers.Query SET IsAdditionalDocRequired = :isAdditionalDocRequired, Status = :status, InScope = :inScope ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
			Map<String, Object> map2 = new HashMap<>(1);
			map2.put("isAdditionalDocRequired", s.getDocRequired());
			map2.put("status", queryForToStatus);
			map2.put("inScope", s.getInScope());
			map2.put("id", s.getId());
			
			String getQuestionId = "select id from rocausers.question where queryId=? order by id asc";
			List<String> queryForQuestionId = jdbcTemplate.queryForList(getQuestionId, new Object[] { s.getId() },
					String.class);
			int j =0;
			for(QuestionBean que : s.getQueationbeans()) {
				if(que.getIsQuestionModified() != 0 ) {
					
					String query5 = "UPDATE rocausers.Question SET IsQuestionModified = 1, ModifiedQuestionDescription = :modifiedQuestionDescription, Status = :status  ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
					Map<String, Object> map5 = new HashMap<>(1);
					map5.put("modifiedQuestionDescription", que.getModifiedQuestionDescription());
					map5.put("status", "Accepted with modification");
					map5.put("id", queryForQuestionId.get(j));
					namedParameterJdbcTemplate.update(query5, map5);
				}
				j++;
			}
			update.add(namedParameterJdbcTemplate.update(query, map));
			update.add(namedParameterJdbcTemplate.update(query2, map2));
		}
		}
		if (!update.contains(0)) {
			i = 1;
		}
		return i;
	}

	@Override
	public List<QueryAssignment> getQueryAssignment(Integer queryId) {
		return jdbcTemplate.query(
				"select Queryid,fromassignment,toassignment,comments from rocaserviceteam.QueryAssignment where Queryid = ? AND IsActive = 1 order by createdon\r\n",
				new Object[] { queryId }, new QueryAssignmentMapper());

	}

	@Override
	public QueryBean fetchQueryById(Integer queryId) {

		QueryBean query = jdbcTemplate.queryForObject("select * from rocausers.Query qr where qr.Id=?",
				new Object[] { queryId }, new QueryDataMapper());
			List<QuestionBean> listofQuestion = jdbcTemplate.query(
					"select que.* from rocaUSERS.question que where que.queryid=?", new Object[] { queryId },
					new QuestionDataMapper());
			query.setQueationbeans(listofQuestion);
		return query;
	}

	@Override
	public Integer updateStatus(List<StatusBean> statusBean) {
		List<Integer> update = new ArrayList<Integer>();
		int i = 0;
		for(StatusBean s : statusBean) { 
		String getStatus = "select Status from rocausers.Query where rocausers.Query.Id=?";
		String queryForFromStatus = jdbcTemplate.queryForObject(getStatus , new Object[] {s.getId()},  String.class);
		String queryForToStatus = null;
		
		if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
			String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=? and Condition=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
		} else if (queryForFromStatus.equalsIgnoreCase("PACE_CREATED")) {
			String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction() }, String.class);
		} else {
			String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus , new Object[] {queryForFromStatus},  String.class);
		}
		String query =  "UPDATE  rocausers.Query SET Status = :status ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
		Map<String,Object> map = new HashMap<>(1);
		map.put("status", queryForToStatus);
		map.put("id", s.getId());
		update.add(namedParameterJdbcTemplate.update(query,map));
		}
		if(!update.contains(0)) {
			i =1;
		}
		return i;
 	}

	@Override
	public List<QueryBean> fetchQueryStatus(String status) {

		List<QueryBean> query = jdbcTemplate.query("select * from rocausers.Query qr where qr.status=?",
				new Object[] { status }, new QueryDataMapper());
		for (QueryBean qr : query) {
			List<QuestionBean> listofQuestion = jdbcTemplate.query(
					"select que.* from rocaUSERS.question que where que.queryid=?", new Object[] { qr.getQueryId() },
					new QuestionDataMapper());
			qr.setQueationbeans(listofQuestion);
		}
		return query;
	}

	@Override
	public Integer postQueryAssignment(List<QueryAssignment> queryAssignment) {
		
		List<Integer> update = new ArrayList<Integer>();
		int i = 0;
		for (QueryAssignment s : queryAssignment) {
			
			String getTeamRole = "select DISTINCT qr.name from rocaserviceteam.queryassignment qa inner join rocaserviceteam.serviceteamusers qu on qa.fromAssignment=qu.id inner join rocaserviceteam.serviceteamRole qr on qu.roleId=qr.id where qa.fromAssignment=?";
			String queryForTeamRole = jdbcTemplate.queryForObject(getTeamRole, new Object[] { s.getFromAssignment() },
					String.class);
			
			if(queryForTeamRole.equalsIgnoreCase("SENIOR")) {
				String query = "INSERT INTO  rocaserviceteam.QueryAssignment "
						+ "				 (QueryId, FromAssignment, ToAssignment, Comments) "
						+ "				 VALUES (:queryId,:fromAssignment,:toAssignment,:comments)";

				Map<String, Object> map = new HashMap<>(1);
				map.put("queryId", s.getId());
				map.put("fromAssignment", s.getFromAssignment());
				map.put("toAssignment", s.getToAssignment());
				map.put("comments", s.getComments());
				
				String getStatus = "select Status from rocausers.Query where rocausers.Query.Id=?";
				String queryForFromStatus = jdbcTemplate.queryForObject(getStatus, new Object[] { s.getId() },
						String.class);
				String queryForToStatus = null;

				if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
					String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=? and Condition=?";
					queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
							new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
				} else if (queryForFromStatus.equalsIgnoreCase("ANS_CREATED")) {
					String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=?";
					queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
							new Object[] { queryForFromStatus, s.getAction() }, String.class);
				} else {
					String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=?";
					queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus },
							String.class);
				}
				String query2 = null;
				Map<String, Object> map2 = new HashMap<>(1);
				if(queryForToStatus.equalsIgnoreCase("ANS_REJECTED")) {
				query2 = "UPDATE  rocausers.Query SET Status = :status, Comments = :comments ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
				map2.put("status", queryForToStatus);
				map2.put("comments", s.getQueryComments());
				map2.put("id", s.getId());
				} else {
					query2 = "UPDATE  rocausers.Query SET Status = :status, UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
					map2.put("status", queryForToStatus);
					map2.put("id", s.getId());
				}
				
				update.add(namedParameterJdbcTemplate.update(query, map));
				update.add(namedParameterJdbcTemplate.update(query2, map2));
			} else if(queryForTeamRole.equalsIgnoreCase("JUNIOR")) {
			String query = "INSERT INTO  rocaserviceteam.QueryAssignment "
					+ "				 (QueryId, FromAssignment, ToAssignment, Comments) "
					+ "				 VALUES (:queryId,:fromAssignment,:toAssignment,:comments)";

			Map<String, Object> map = new HashMap<>(1);
			map.put("queryId", s.getId());
			map.put("fromAssignment", s.getFromAssignment());
			map.put("toAssignment", s.getToAssignment());
			map.put("comments", s.getComments());
			
			String getStatus = "select Status from rocausers.Query where rocausers.Query.Id=?";
			String queryForFromStatus = jdbcTemplate.queryForObject(getStatus, new Object[] { s.getId() },
					String.class);
			String queryForToStatus = null;

			if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
				String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=? and Condition=?";
				queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
						new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
			} else if (queryForFromStatus.equalsIgnoreCase("ANS_CREATED")) {
				String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=? and Action=?";
				queryForToStatus = jdbcTemplate.queryForObject(ToStatus,
						new Object[] { queryForFromStatus, s.getAction() }, String.class);
			} else {
				String ToStatus = "select ToState from rocaserviceteam.QueryStatusMaster where fromstate=?";
				queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus },
						String.class);
			}
			String query2 = "UPDATE  rocausers.Query SET Status = :status, UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
			Map<String, Object> map2 = new HashMap<>(1);
			map2.put("status", queryForToStatus);
			map2.put("id", s.getId());
			
			String getQuestionId = "select id from rocausers.question where queryId=? order by id asc";
			List<String> queryForQuestionId = jdbcTemplate.queryForList(getQuestionId, new Object[] { s.getId() },
					String.class);
			int j =0;
			for(QuestionBean que : s.getQueationbeans()) {
				if(que.getIsQuestionModified() != 0 ) {
					
					String query5 = "UPDATE rocausers.Question SET Answer = :answer, UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
					Map<String, Object> map5 = new HashMap<>(1);
					map5.put("answer", que.getAnswer());
					map5.put("id", queryForQuestionId.get(j));
					namedParameterJdbcTemplate.update(query5, map5);
				}
				j++;
			}
			
			update.add(namedParameterJdbcTemplate.update(query, map));
			update.add(namedParameterJdbcTemplate.update(query2, map2));
		}
		}
		if (!update.contains(0)) {
			i = 1;
		}
		return i;
	}
	
	
}
