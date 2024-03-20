package com.model2.mvc.service.domain;

import java.sql.Date;


public class PurchaseVO {
	
	private UserVO buyer;
	private String dlvyAddr;
	private Date dlvyDate;
	private String dlvyRequest;
	private Date orderDate;
	private String paymentOption;
	private ProductVO purchaseProd;
	private String receiverName;
	private String receiverPhone;
	private String tranCode;
	private int tranNo;
	private int prodCount;
	
	public PurchaseVO(){
		// Blank
	}
	
	public UserVO getBuyer() {
		return buyer;
	}
	
	public void setBuyer(UserVO buyer) {
		this.buyer = buyer;
	}
	
	public String getDlvyAddr() {
		return dlvyAddr;
	}

	public Date getDlvyDate() {
		return dlvyDate;
	}

	public String getDlvyRequest() {
		return dlvyRequest;
	}

	public void setDlvyAddr(String dlvyAddr) {
		this.dlvyAddr = dlvyAddr;
	}

	public void setDlvyDate(Date dlvyDate) {
		this.dlvyDate = dlvyDate;
	}

	public void setDlvyRequest(String dlvyRequest) {
		this.dlvyRequest = dlvyRequest;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getPaymentOption() {
		return paymentOption;
	}
	
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	
	public ProductVO getPurchaseProd() {
		return purchaseProd;
	}
	
	public void setPurchaseProd(ProductVO purchaseProd) {
		this.purchaseProd = purchaseProd;
	}
	
	public String getReceiverName() {
		return receiverName;
	}
	
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public String getReceiverPhone() {
		return receiverPhone;
	}
	
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	
	public String getTranCode() {
		return tranCode;
	}
	
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	
	public int getTranNo() {
		return tranNo;
	}
	
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	
	public int getProdCount() {
		return prodCount;
	}

	public void setProdCount(int prodCount) {
		this.prodCount = prodCount;
	}

	@Override
	public String toString() {
		return "PurchaseVO [buyer=" + buyer + ", dlvyAddr=" + dlvyAddr + ", dlvyDate=" + dlvyDate + ", dlvyRequest="
				+ dlvyRequest + ", orderDate=" + orderDate + ", paymentOption=" + paymentOption + ", purchaseProd="
				+ purchaseProd + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone + ", tranCode="
				+ tranCode + ", tranNo=" + tranNo + ", prodCount=" + prodCount + "]";
	}
}