package com.icia.web.model;

import java.io.Serializable;

public class KakaoPayOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String partnerOrderId;
	private String partnerUserId;
	private String itemName;
	private String itemCode;
	private int quantity;
	private int totalAmount;
	private int taxFreeAmount;
	private int vatAmount;
	
	private String tId;
	private String pgToken;
	
	public KakaoPayOrder() {
		partnerOrderId = "";
		partnerUserId = "";
		itemName = "";
		itemCode = "";
		quantity = 0;
		totalAmount = 0;
		taxFreeAmount = 0;
		vatAmount = 0;
		
		tId = "";
		pgToken = "";
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTaxFreeAmount() {
		return taxFreeAmount;
	}

	public void setTaxFreeAmount(int taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}

	public int getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(int vatAmount) {
		this.vatAmount = vatAmount;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getPgToken() {
		return pgToken;
	}

	public void setPgToken(String pgToken) {
		this.pgToken = pgToken;
	}
	
}
