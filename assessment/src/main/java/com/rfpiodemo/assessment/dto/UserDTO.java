package com.rfpiodemo.assessment.dto;

import java.util.HashMap;
import java.util.Map;

public class UserDTO {
	private String userId;
	private String name;
	private PhoneNumberDTO phoneNumber;
	private Map<String, String> userSettings = new HashMap<>();
	private AddressDTO address;
	private PreferenceDTO preference;
	private Double income;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public PhoneNumberDTO getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(PhoneNumberDTO phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Map<String, String> getUserSettings() {
		return userSettings;
	}
	public void setUserSettings(Map<String, String> userSettings) {
		this.userSettings = userSettings;
	}
	
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public PreferenceDTO getPreference() {
		return preference;
	}
	public void setPreference(PreferenceDTO preference) {
		this.preference = preference;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	
}
