package com.icia.web.model;

import java.io.Serializable;

public class HiBoard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5535622299741327720L;
	
	private long hibbsSeq;
	private String userId;
	private long hibbsGroup;
	private int hibbsOrder;
	private int hibbsIndent;
	private String hibbsTitle;
	private String hibbsContent;
	private int hibbsReadCnt;
	private String regDate;
	private long hibbsParent;
		
	private String searchType;
	private String searchValue;
	
	private long startRow;
	private long endRow;
	
	private String userName;
	private String userEmail;
	
	private HiBoardFile hiBoardFile;
	
	public HiBoard() {
		hibbsSeq = 0;
		userId = "";
		hibbsGroup = 0;
		hibbsOrder = 0;
		hibbsIndent = 0;
		hibbsTitle = "";
		hibbsContent = "";
		hibbsReadCnt = 0;
		regDate = "";
		hibbsParent = 0;
		
		searchType = "";
		searchValue = "";
		
		startRow = 0;
		endRow = 0;
		
		userName = "";
		userEmail = "";
		
		hiBoardFile = null;
	}
	
	public long getHibbsSeq() {
		return hibbsSeq;
	}
	
	public void setHibbsSeq(long hibbsSeq) {
		this.hibbsSeq = hibbsSeq;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public long getHibbsGroup() {
		return hibbsGroup;
	}
	
	public void setHibbsGroup(long hibbsGroup) {
		this.hibbsGroup = hibbsGroup;
	}
	
	public int getHibbsOrder() {
		return hibbsOrder;
	}
	
	public void setHibbsOrder(int hibbsOrder) {
		this.hibbsOrder = hibbsOrder;
	}
	
	public int getHibbsIndent() {
		return hibbsIndent;
	}
	
	public void setHibbsIndent(int hibbsIndent) {
		this.hibbsIndent = hibbsIndent;
	}
	
	public String getHibbsTitle() {
		return hibbsTitle;
	}
	
	public void setHibbsTitle(String hibbsTitle) {
		this.hibbsTitle = hibbsTitle;
	}
	
	public String getHibbsContent() {
		return hibbsContent;
	}
	
	
	public void setHibbsContent(String hibbsContent) {
		this.hibbsContent = hibbsContent;
	}
	
	public int getHibbsReadCnt() {
		return hibbsReadCnt;
	}
	
	public void setHibbsReadCnt(int hibbsReadCnt) {
		this.hibbsReadCnt = hibbsReadCnt;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public long getHibbsParent() {
		return hibbsParent;
	}
	
	public void setHibbsParent(long hibbsParent) {
		this.hibbsParent = hibbsParent;
	}
	
	public String getSearchType() {
		return searchType;
	}
	
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public String getSearchValue() {
		return searchValue;
	}
	
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public long getStartRow() {
		return startRow;
	}
	
	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}
	
	public long getEndRow() {
		return endRow;
	}
	
	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public HiBoardFile getHiBoardFile() {
		return hiBoardFile;
	}

	public void setHiBoardFile(HiBoardFile hiBoardFile) {
		this.hiBoardFile = hiBoardFile;
	}
	
	
	
}
