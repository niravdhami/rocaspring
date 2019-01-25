package com.eny.roca.db.bean;

import java.util.List;

public class QueryAssignment extends StatusBean {

	private String  fromAssignment;
	private String  toAssignment;
	private String  comments;
	private Integer inScope;	
	private List<QuestionBean> queationbeans;
	public String getFromAssignment() {
		return fromAssignment;
	}
	public void setFromAssignment(String fromAssignment) {
		this.fromAssignment = fromAssignment;
	}
	public String getToAssignment() {
		return toAssignment;
	}
	public void setToAssignment(String toAssignment) {
		this.toAssignment = toAssignment;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getInScope() {
		return inScope;
	}
	public void setInScope(Integer inScope) {
		this.inScope = inScope;
	}
	public List<QuestionBean> getQueationbeans() {
		return queationbeans;
	}
	public void setQueationbeans(List<QuestionBean> queationbeans) {
		this.queationbeans = queationbeans;
	}
}
