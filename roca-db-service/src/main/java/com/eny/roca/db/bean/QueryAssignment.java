package com.eny.roca.db.bean;

public class QueryAssignment extends StatusBean {

	
	private String  fromAssignment;
	private String  toAssignment;
	private String  comments;
	
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
}
