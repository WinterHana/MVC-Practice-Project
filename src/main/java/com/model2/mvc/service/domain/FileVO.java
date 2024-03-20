package com.model2.mvc.service.domain;

import org.springframework.web.multipart.MultipartFile;

public class FileVO {
	
	private int prodNo;
	private int imageNo;
	private String fileName;
	private MultipartFile multipartFile;
	
	public FileVO() {
		// Blank
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public int getImageNo() {
		return imageNo;
	}

	public void setImageNo(int imageNo) {
		this.imageNo = imageNo;
	}

	public String getFileName() {
		return fileName;
	}
	
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	@Override
	public String toString() {
		return "FileVO [prodNo=" + prodNo + ", imageNo=" + imageNo + ", fileName=" + fileName + ", multipartFile="
				+ multipartFile + "]";
	}
}
