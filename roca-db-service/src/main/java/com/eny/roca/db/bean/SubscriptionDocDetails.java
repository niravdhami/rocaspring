package com.eny.roca.db.bean;

import org.springframework.web.multipart.MultipartFile;

public class SubscriptionDocDetails {
	
	private String docName;
	
	private DocType docType;
	
	private String docExtention;
	
	private int is_valid_doc;
	
	private MultipartFile docData;
	
	private long docDataSize;
	 private byte[] fileData;
	public SubscriptionDocDetails() {
		
	}
	
		public SubscriptionDocDetails(String docName, DocType docType, String docExtention, int is_valid_doc,
				MultipartFile docData, long docDataSize) {
		//super();
		this.docName = docName;
		this.docType = docType;
		this.docExtention = docExtention;
		this.is_valid_doc = is_valid_doc;
		this.docData = docData;
		this.docDataSize = docDataSize;
	}

	public SubscriptionDocDetails(String fileName, String contentType, long docDataSize) {
		
		this.docName = fileName;
		this.docExtention = contentType;
		this.docDataSize = docDataSize;
			// TODO Auto-generated constructor stub
		}

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

	public MultipartFile getDocData() {
		return docData;
	}

	public void setDocData(MultipartFile docData) {
		this.docData = docData;
	}

	public long getDocDataSize() {
		return docDataSize;
	}

	public void setDocDataSize(long docDataSize) {
		this.docDataSize = docDataSize;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

}
