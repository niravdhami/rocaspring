package com.eny.roca.db.bean;

import org.springframework.web.multipart.MultipartFile;

public class SubscriptionBean extends UserRegistration {
	private String pseudonym;
	private Integer  countryIncorporation;
	private Integer  taxResidentialStatus; // 0 -- dont know ,, 1 -- resident , 2 - non resident
	private String bodyCorporates;
	private Boolean  isCharitable;
	private Integer  registrationId;
	private String companyHqLocation; /// forign key from COuntery_master...
	private String pan;
	private Boolean  isPanAttached=false;
	private String panComments;
	private String gst;
	private String gstComments;
	private String url;
	private String address;
	private Boolean  isEYDiscloureSigned=false;
	private String status;
	private String paceId;
	private Boolean  isAdditionalDocRequired=false;
	//private DateTime createdDate =DateTime.now(); // Need to check its Data Type Can we directly put it as DateTime...??
//	private DateTime updatedDate = DateTime.now();
	private Boolean  isOnlineEngagementSigned=false;
	private Integer subscriptionId;
	private int workedWithEY=0;
	private String eyContactPerson1="";
	private String eyContactPerson2="";
	private Integer rocaServiceAvailed=0;
	private String relatedPartyName1;
	private String relatedPartyName2;
	private String roleName;
	private String countryName;
	private String industryName;
	private String createdDate;
	private String updatedDate;
	 
	/*Subscritpion docs */
	private String email;
	private String docName;
	private DocType docType;
	private String docExtention;
	private int is_valid_doc;
	private MultipartFile docData;
	
	private int isRocaServiceAvailed;
	private int isPaceProcessCompleted;
	
	
	public String getPseudonym() {
		return pseudonym;
	}
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}
	public Integer  getCountryIncorporation() {
		return countryIncorporation;
	}
	public void setCountryIncorporation(Integer  countryIncorporation) {
		this.countryIncorporation = countryIncorporation;
	}
	public Integer  getTaxResidentialStatus() {
		return taxResidentialStatus;
	}
	public void setTaxResidentialStatus(Integer  taxResidentialStatus) {
		this.taxResidentialStatus = taxResidentialStatus;
	}
	public String getBodyCorporates() {
		return bodyCorporates;
	}
	public void setBodyCorporates(String bodyCorporates) {
		this.bodyCorporates = bodyCorporates;
	}
	public Boolean  getIsCharitable() {
		return isCharitable;
	}
	public void setIsCharitable(Boolean  isCharitable) {
		this.isCharitable = isCharitable;
	}
	public Integer  getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(Integer  registrationId) {
		this.registrationId = registrationId;
	}
	public String getCompanyHqLocation() {
		return companyHqLocation;
	}
	public void setCompanyHqLocation(String companyHqLocation) {
		this.companyHqLocation = companyHqLocation;
	}
	
	public Boolean  getIsPanAttached() {
		return isPanAttached;
	}
	public void setIsPanAttached(Boolean  isPanAttached) {
		this.isPanAttached = isPanAttached;
	}
	public String getPanComments() {
		return panComments;
	}
	public void setPanComments(String panComments) {
		this.panComments = panComments;
	}
	
	public String getGstComments() {
		return gstComments;
	}
	public void setGstComments(String gstComments) {
		this.gstComments = gstComments;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean  getIsEYDiscloureSigned() {
		return isEYDiscloureSigned;
	}
	public void setIsEYDiscloureSigned(Boolean  isEYDiscloureSigned) {
		this.isEYDiscloureSigned = isEYDiscloureSigned;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaceId() {
		return paceId;
	}
	public void setPaceId(String paceId) {
		this.paceId = paceId;
	}
	public Boolean  getIsAdditionalDocRequired() {
		return isAdditionalDocRequired;
	}
	public void setIsAdditionalDocRequired(Boolean  isAdditionalDocRequired) {
		this.isAdditionalDocRequired = isAdditionalDocRequired;
	}
	/*public DateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}
	public DateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}*/
	public Boolean  getIsOnlineEngagementSigned() {
		return isOnlineEngagementSigned;
	}
	public void setIsOnlineEngagementSigned(Boolean  isOnlineEngagementSigned) {
		this.isOnlineEngagementSigned = isOnlineEngagementSigned;
	}
	@Override
	public String toString() {
		return "SubscriptionBean [pseudonym=" + pseudonym + ", countryIncorporation=" + countryIncorporation
				+ ", taxResidentialStatus=" + taxResidentialStatus + ", bodyCorporates=" + bodyCorporates
				+ ", isCharitable=" + isCharitable + ", registrationId=" + registrationId + ", companyHqLocation="
				+ companyHqLocation + ", pan=" + pan + ", isPanAttached=" + isPanAttached + ", panComments="
				+ panComments + ", gst=" + gst + ", gstComments=" + gstComments + ", url=" + url + ", address="
				+ address + ", isEYDiscloureSigned=" + isEYDiscloureSigned + ", status=" + status + ", paceId=" + paceId
				+ ", isAdditionalDocRequired=" + isAdditionalDocRequired + ", createdDate=" +  
				  ", updatedDate=" +     ", isOnlineEngagementSigned=" + isOnlineEngagementSigned + "]";
	}
	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public int getWorkedWithEY() {
		return workedWithEY;
	}
	public void setWorkedWithEY(int workedWithEY) {
		this.workedWithEY = workedWithEY;
	}
	public String getEyContactPerson1() {
		return eyContactPerson1;
	}
	public void setEyContactPerson1(String eyContactPerson1) {
		this.eyContactPerson1 = eyContactPerson1;
	}
	public String getEyContactPerson2() {
		return eyContactPerson2;
	}
	public void setEyContactPerson2(String eyContactPerson2) {
		this.eyContactPerson2 = eyContactPerson2;
	}
	public Integer getRocaServiceAvailed() {
		return rocaServiceAvailed;
	}
	public void setRocaServiceAvailed(Integer rocaServiceAvailed) {
		this.rocaServiceAvailed = rocaServiceAvailed;
	}
	public String getRelatedPartyName1() {
		return relatedPartyName1;
	}
	public void setRelatedPartyName1(String relatedPartyName1) {
		this.relatedPartyName1 = relatedPartyName1;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getRelatedPartyName2() {
		return relatedPartyName2;
	}
	public void setRelatedPartyName2(String relatedPartyName2) {
		this.relatedPartyName2 = relatedPartyName2;
	}
	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public DocType getDocType() {
		return docType;
	}
	public void setDocType(DocType docType) {
		this.docType = docType;
	}
	public String getDocExtention() {
		return docExtention;
	}
	public void setDocExtention(String docExtention) {
		this.docExtention = docExtention;
	}
	public int getIs_valid_doc() {
		return is_valid_doc;
	}
	public void setIs_valid_doc(int is_valid_doc) {
		this.is_valid_doc = is_valid_doc;
	}
	public MultipartFile getDocData() {
		return docData;
	}
	public void setDocData(MultipartFile docData) {
		this.docData = docData;
	}
	public int getIsRocaServiceAvailed() {
		return isRocaServiceAvailed;
	}
	public void setIsRocaServiceAvailed(int isRocaServiceAvailed) {
		this.isRocaServiceAvailed = isRocaServiceAvailed;
	}
	public int getIsPaceProcessCompleted() {
		return isPaceProcessCompleted;
	}
	public void setIsPaceProcessCompleted(int isPaceProcessCompleted) {
		this.isPaceProcessCompleted = isPaceProcessCompleted;
	}

	
	
	
	

}
