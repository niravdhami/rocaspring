package com.eny.roca.db.bean;

public class StatusBean {
	private int id;
	private String action;
	private String condition;
	private String  paceId;
	private Integer docRequired;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getPaceId() {
		return paceId;
	}
	public void setPaceId(String paceId) {
		this.paceId = paceId;
	}
	public Integer getDocRequired() {
		return docRequired;
	}
	public void setDocRequired(Integer docRequired) {
		this.docRequired = docRequired;
	}

}
