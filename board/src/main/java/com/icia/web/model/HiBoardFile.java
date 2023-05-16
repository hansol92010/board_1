package com.icia.web.model;

import java.io.Serializable;

public class HiBoardFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -925645383946417138L;

	private long hibbsSeq;
	private int fileSeq;
	private String fileOrgName;
	private String fileName;
	private String fileExe;
	private long fileSize;
	private String regDate;
	
	public HiBoardFile() {
		hibbsSeq = 0;
		fileSeq = 0;
		fileOrgName = "";
		fileName = "";
		fileExe = "";
		fileSize = 0;
		regDate = "";
	}
	
	public long getHibbsSeq() {
		return hibbsSeq;
	}
	public void setHibbsSeq(long hibbsSeq) {
		this.hibbsSeq = hibbsSeq;
	}
	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getFileOrgName() {
		return fileOrgName;
	}
	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExe() {
		return fileExe;
	}
	public void setFileExe(String fileExe) {
		this.fileExe = fileExe;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
