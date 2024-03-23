package com.model2.mvc.service.domain;

public class RestApiCommonVO {
	private ProductVO product;
	private UserVO user;
	private PurchaseVO purchase;
	
	public RestApiCommonVO() {
		// Blank;
	}

	public ProductVO getProduct() {
		return product;
	}

	public UserVO getUser() {
		return user;
	}

	public PurchaseVO getPurchase() {
		return purchase;
	}

	public void setProduct(ProductVO product) {
		this.product = product;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public void setPurchase(PurchaseVO purchase) {
		this.purchase = purchase;
	}
}
