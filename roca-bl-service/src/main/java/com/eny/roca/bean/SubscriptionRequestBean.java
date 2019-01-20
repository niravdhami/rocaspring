package com.eny.roca.bean;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;


public class SubscriptionRequestBean {
	
	
	

	public SubscriptionRequestBean() {
		super();
	}

	private int subscriptionId;
	
	private String email;
	
	/* Subscription Doc */
	
	private int subscriptionDocId;
	
	//private SubscriptionDocDetails subscriptionDocDetails;
	
	
	private String docName;
	
	private DocType docType;
	
	private String docExtention;
	
	private int is_valid_doc;
	
	//private MultipartFile docData;
	
	private FileSystemResource file;
	

	public int getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSubscriptionDocId() {
		return subscriptionDocId;
	}

	public void setSubscriptionDocId(int subscriptionDocId) {
		this.subscriptionDocId = subscriptionDocId;
	}

	
	@Override
	public String toString() {
		return "SubscriptionBean [subscriptionId=" + subscriptionId + ", subscriptionDocId=" + subscriptionDocId 
				+ ", email=" + email + "]";
	}

	/*public SubscriptionDocDetails getSubscriptionDocDetails() {
		return subscriptionDocDetails;
	}

	public void setSubscriptionDocDetails(SubscriptionDocDetails subscriptionDocDetails) {
		this.subscriptionDocDetails = subscriptionDocDetails;
	}*/

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

	/*public MultipartFile getDocData() {
		return docData;
	}

	public void setDocData(MultipartFile docData) {
		this.docData = docData;
	}*/

	public FileSystemResource getFile() {
		return file;
	}

	public void setFile(FileSystemResource file) {
		this.file = file;
	}

	
	
}
