package com.eny.roca.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.eny.roca.db.bean.QuestionBean;

public class QuestionDataMapper implements RowMapper<QuestionBean> {
	@Override
	public QuestionBean mapRow(ResultSet rs, int arg1) throws SQLException {
		
		QuestionBean questionBean = new QuestionBean();
		
		questionBean.setQuestionDescription(rs.getString("QuestionDescription"));
		questionBean.setQueryId(Integer.parseInt(rs.getString("QueryId")));
		questionBean.setQueStatus(rs.getString("Status"));
		questionBean.setModifiedQuestionDescription(rs.getString("ModifiedQuestionDescription"));
		questionBean.setIsQuestionModified(Integer.parseInt(rs.getString("IsQuestionModified")));
		questionBean.setQueComment(rs.getString("Comments"));
		questionBean.setAnswer(rs.getString("Answer"));
		
		return questionBean;
	}

}
