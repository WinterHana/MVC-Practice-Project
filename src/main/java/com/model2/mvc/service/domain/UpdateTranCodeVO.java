package com.model2.mvc.service.domain;

public class UpdateTranCodeVO {
	private int tranNo;
	private String tranCode;
	private int page;
	
	public UpdateTranCodeVO() {
		// blank
	}
	
	public UpdateTranCodeVO(int tranNo, String tranCode, int page) {
		this.tranNo = tranNo;
		this.tranCode = tranCode;
		this.page = page;
	}
	
	public int getTranNo() {
		return tranNo;
	}
	
	public String getTranCode() {
		return tranCode;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "UpdateTranCodeVO [tranNo=" + tranNo + ", tranCode=" + tranCode + ", page=" + page + "]";
	}
}
