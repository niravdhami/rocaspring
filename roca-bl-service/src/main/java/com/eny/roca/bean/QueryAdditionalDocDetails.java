package com.eny.roca.bean;

import org.springframework.web.multipart.MultipartFile;

public class QueryAdditionalDocDetails {
	
	private Integer queryId;
	
	private String docName;
	
	private DocType docType;
	
	private String docExtention;
	
	private int is_valid_doc;
	
	private MultipartFile docData;
	
	private long docDataSize;
	
	private byte[] fileData;

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
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
