package com.eny.roca.dao;

public class IndustryMasterData {
	private Integer id;
	private String description;
	private Integer parentIndustryId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getParentIndustryId() {
		return parentIndustryId;
	}
	public void setParentIndustryId(Integer parentIndustryId) {
		this.parentIndustryId = parentIndustryId;
	}
}
