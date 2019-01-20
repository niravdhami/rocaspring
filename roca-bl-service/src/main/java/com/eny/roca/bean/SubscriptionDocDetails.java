package com.eny.roca.bean;

public class SubscriptionDocDetails {
	
	private String docName;
	
	private DocType docType;
	
	private String docExtention;
	
	private int is_valid_doc;
	
	private byte[] docData;
	
	public int getIs_valid_doc() {
		return is_valid_doc;
	}

	public void setIs_valid_doc(int is_valid_doc) {
		this.is_valid_doc = is_valid_doc;
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

	public byte[] getDocData() {
		return docData;
	}

	public void setDocData(byte[] docData) {
		this.docData = docData;
	}

}
