package com.rfpiodemo.assessment.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PhoneNumberDTO {

	private Long mobileNumber;
	private Integer accessCode;
	private Integer countryCode;
	private Integer areaCode;
	private Integer localNumber;
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Integer getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(Integer accessCode) {
		this.accessCode = accessCode;
	}
	public Integer getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}
	public Integer getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getLocalNumber() {
		return localNumber;
	}
	public void setLocalNumber(Integer localNumber) {
		this.localNumber = localNumber;
	}

}
